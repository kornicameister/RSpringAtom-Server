/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
 * *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 * *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 * *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.data.vin.decoder.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.neovisionaries.i18n.CountryCode;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Role(BeanDefinition.ROLE_SUPPORT)
@Description("Retrieves manufacturedIn by looking up in the entries loaded from file storage")
class DefaultWMIManufacturedInResolver
  implements WMIManufacturedInResolver {
  private static final Logger                             LOGGER         = LoggerFactory.getLogger(DefaultWMIManufacturedInResolver.class);
  private static final String                             WMI_PATH       = "classpath:org/agatom/springatom/data/wmi.json";
  @Autowired
  private              ObjectMapper                       objectMapper   = null;
  private              Map<CodeRangeWrapper, CountryCode> countryCodeMap = Maps.newTreeMap();

  @PostConstruct
  private void initializeWMICodes() {
    try {
      final InputStream stream = new FileSystemResourceLoader().getResource(WMI_PATH).getInputStream();
      final WMIBeanList wmiBeans = this.objectMapper.readValue(stream, WMIBeanList.class);
      Assert.notNull(wmiBeans, "Failed to read WMI codes, returned WMIBeanList is null");
      this.postProcessWMIBeans(wmiBeans);
    } catch (Exception exp) {
      LOGGER.error("initializeWMICodes failed", exp);
    }
  }

  private void postProcessWMIBeans(final WMIBeanList wmiBeans) throws NotFoundException {
    for (final WMIBean wmiBean : wmiBeans) {
      final List<String> codes = wmiBean.codes;
      Assert.notEmpty(codes);

      final List<CountryCode> byName = CountryCode.findByName(wmiBean.country);
      if (CollectionUtils.isEmpty(byName)) {
        final String format = String.format("%s has no country code corresponding", wmiBean.country);
        LOGGER.warn(format);
        continue;
      }

      for (final String code : codes) {
        final String[] split = code.split("-");

        final CodeRangeWrapper wrapper = CodeRangeWrapper.newCodeRangeWrapper(split[0], split.length == 2 ? split[1] : "");
        final CountryCode countryCode = byName.get(0);

        LOGGER.trace(String.format("Key=>%s, CountryCode=%s", wrapper, countryCode));

        this.countryCodeMap.put(wrapper, countryCode);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public CountryCode getCountryCode(final String wmi) {
    final String cc = wmi.substring(0, 2);
    final Set<CodeRangeWrapper> codeRangeWrappers = this.countryCodeMap.keySet();
    final Optional<CodeRangeWrapper> fm = FluentIterable.from(codeRangeWrappers).firstMatch(new Predicate<CodeRangeWrapper>() {
      @Override
      public boolean apply(@Nullable final CodeRangeWrapper input) {
        LOGGER.trace(String.format("Examining CRW=%s", input));
        assert input != null;
        return input.isInRange(cc);
      }
    });
    if (fm.isPresent()) {
      return this.countryCodeMap.get(fm.get());
    }
    return null;
  }

  private static class CodeRangeWrapper
    implements Comparable<CodeRangeWrapper> {
    char[] bArray = null;
    char[] eArray = null;
    private String beginCode = null;
    private String endCode   = null;

    static CodeRangeWrapper newCodeRangeWrapper(final String beginCode, final String endCode) {
      final CodeRangeWrapper wrapper = new CodeRangeWrapper();
      wrapper.bArray = CodeRangeWrapper.toCharArray(beginCode);
      wrapper.eArray = CodeRangeWrapper.toCharArray(endCode);
      wrapper.beginCode = beginCode;
      wrapper.endCode = endCode;
      return wrapper;
    }

    static char[] toCharArray(final String str) {
      if (!StringUtils.hasText(str)) {
        return new char[]{};
      }
      final int length = str.length();
      final char[] arr = new char[length];
      for (int i = 0; i < length; i++) {
        arr[i] = str.charAt(i);
      }
      return arr;
    }

    boolean isInRange(final String code) {
      final char[] chars = CodeRangeWrapper.toCharArray(code);
      final int length = chars.length;
      boolean inRange = false;
      for (int i = 0; i < length; i++) {
        if (this.eArray.length != 0) {
          inRange = this.bArray[i] <= chars[i] && chars[i] <= this.eArray[i];
        } else {
          inRange = this.bArray[i] == chars[i];
        }
        if (!inRange) {
          return false;
        }
      }
      return inRange;
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(bArray, eArray);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      CodeRangeWrapper that = (CodeRangeWrapper) o;

      return Objects.equal(this.bArray, that.bArray) &&
        Objects.equal(this.eArray, that.eArray);
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
        .addValue(beginCode)
        .addValue(endCode)
        .toString();
    }

    @Override
    public int compareTo(@Nonnull final CodeRangeWrapper crw) {
      return ComparisonChain.start()
        .compare(this.beginCode, crw.beginCode)
        .compare(this.endCode, crw.endCode)
        .result();
    }
  }

  @SuppressWarnings("UnusedDeclaration")
  public static class WMIBeanList
    implements Serializable, Iterable<WMIBean> {
    private static final long         serialVersionUID = 3060321745730000503L;
    private              Set<WMIBean> wmi              = null;

    public WMIBeanList() {
    }

    public Set<WMIBean> getWmi() {
      return wmi;
    }

    public WMIBeanList setWmi(final Set<WMIBean> wmi) {
      this.wmi = wmi;
      return this;
    }

    @Override
    public Iterator<WMIBean> iterator() {
      return this.wmi.iterator();
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
        .addValue(wmi)
        .toString();
    }
  }

  private static class WMIBean
    implements Serializable {
    private static final long         serialVersionUID = -8996607327207872771L;
    private              String       country          = null;
    private              List<String> codes            = null;

    public WMIBean() {
    }

    public String getCountry() {
      return country;
    }

    public WMIBean setCountry(final String country) {
      this.country = country;
      return this;
    }

    public List<String> getCodes() {
      return codes;
    }

    public WMIBean setCodes(final List<String> codes) {
      this.codes = codes;
      return this;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
        .addValue(country)
        .addValue(codes)
        .toString();
    }
  }
}

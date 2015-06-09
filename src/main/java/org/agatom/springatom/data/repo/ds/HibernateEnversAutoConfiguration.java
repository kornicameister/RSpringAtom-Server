package org.agatom.springatom.data.repo.ds;

import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Configuration
public class HibernateEnversAutoConfiguration
  extends HibernateJpaAutoConfiguration {

  @Override
  protected void customizeVendorProperties(final Map<String, Object> vp) {
    super.customizeVendorProperties(vp);
    vp.put("hibernate.envers.default_schema", "springatom");
    vp.put("hibernate.envers.revision_field_name", "revision");
    vp.put("hibernate.envers.revision_type_field_name", "revisionType");
    vp.put("hibernate.envers.audit_table_suffix", "_history");
    vp.put("hibernate.envers.track_entities_changed_in_revision", "true");
    vp.put("hibernate.envers.revision_on_collection_change", "true");
    vp.put("hibernate.show_sql", "true");
    vp.put("hibernate.format_sql", "false");
    vp.put("hibernate.use_sql_comments", "false");
  }
}

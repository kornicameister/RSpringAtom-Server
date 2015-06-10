package org.agatom.springatom.security.support;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AjaxHttpFirewall
  extends DefaultHttpFirewall {
  private static final String X_REQUESTED_WITH = "X-Requested-With";

  @Override
  public FirewalledRequest getFirewalledRequest(final HttpServletRequest request) throws RequestRejectedException {
    final FirewalledRequest firewalledRequest = super.getFirewalledRequest(request);
    final String header = request.getHeader(X_REQUESTED_WITH);
    if (!StringUtils.hasText(header)) {
      throw new AjaxRequestOnlyRequestRejectedException();
    }
    return firewalledRequest;
  }

  @Override
  public HttpServletResponse getFirewalledResponse(final HttpServletResponse response) {
    final HttpServletResponse firewalledResponse = super.getFirewalledResponse(response);
    final String contentType = firewalledResponse.getHeader(HttpHeaders.CONTENT_TYPE);
    if (!StringUtils.hasText(contentType)) {
      firewalledResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }
    return firewalledResponse;
  }

  private static class AjaxRequestOnlyRequestRejectedException
    extends RequestRejectedException {
    private static final long serialVersionUID = -2595381964809100380L;

    public AjaxRequestOnlyRequestRejectedException() {
      super("Only AJAX requests are allowed");
    }
  }
}

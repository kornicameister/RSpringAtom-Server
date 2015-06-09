package org.agatom.springatom.data.service.services;

import org.springframework.data.domain.AuditorAware;


public interface NAuditService
  extends AuditorAware<String> {
}

package org.agatom.springatom.data.service.impl;

import org.agatom.springatom.data.service.services.NAuditService;
import org.springframework.stereotype.Service;


@Service
class AuditDomainService
  implements NAuditService {

  @Override
  public String getCurrentAuditor() {
    return "SYSTEM";
  }

}

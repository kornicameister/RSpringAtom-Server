package org.agatom.springatom.data.service.impl;

import org.agatom.springatom.data.model.data.NDataInstallationStatus;
import org.agatom.springatom.data.repo.repositories.data.NDataInstallationStatusRepository;
import org.agatom.springatom.data.service.services.NDataInstallationStatusService;
import org.agatom.springatom.data.types.data.DataInstallationStatus;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
class DataInstallationStatusDomainService
  implements NDataInstallationStatusService {
  @Autowired
  protected NDataInstallationStatusRepository repository = null;

  @Override
  public NDataInstallationStatus onSuccessfulInstallation(final Long hash, final String path, final Class<?> handler) {
    return this.newDIS(hash, path, handler, null);
  }

  @Override
  public NDataInstallationStatus onFailureInstallation(final Long hash, final String path, final Class<?> handler, final Throwable failureReason) {
    return this.newDIS(hash, path, handler, failureReason);
  }

  private NDataInstallationStatus newDIS(final Long hash, final String path, final Class<?> handler, final Throwable failureReason) {
    final NDataInstallationStatus status = new NDataInstallationStatus();
    status.setInstallationFailure(failureReason);
    status.setInstallationHandler(handler);
    status.setInstallationHash(hash);
    status.setInstallationPath(path);
    status.setInstallationTimestamp(DateTime.now());
    status.setInstallationStatus(failureReason == null ? DataInstallationStatus.InstallStatus.SUCCESS : DataInstallationStatus.InstallStatus.FAILED);
    return this.repository.save(status);
  }

}

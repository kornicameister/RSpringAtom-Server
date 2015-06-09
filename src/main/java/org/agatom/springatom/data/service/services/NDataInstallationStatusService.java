package org.agatom.springatom.data.service.services;


import org.agatom.springatom.data.model.data.NDataInstallationStatus;

public interface NDataInstallationStatusService {

  NDataInstallationStatus onSuccessfulInstallation(final Long hash, final String path, final Class<?> handler);

  NDataInstallationStatus onFailureInstallation(final Long hash, final String path, final Class<?> handler, final Throwable failureReason);

}

package org.agatom.springatom.data.loader;

import com.google.common.base.Stopwatch;
import org.agatom.springatom.data.loader.event.PostDataLoadEvent;
import org.agatom.springatom.data.loader.event.PreDataLoadEvent;
import org.agatom.springatom.data.loader.mgr.DataLoaderManager;
import org.agatom.springatom.data.loader.srv.DataLoaderService;
import org.agatom.springatom.data.service.services.NDataInstallationStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;


class ActiveLoaderManager
  implements DataLoaderManager, ApplicationEventPublisherAware {
  private static final Logger                         LOGGER                    = LoggerFactory.getLogger(ActiveLoaderManager.class);
  @Autowired
  private              Map<String, DataLoaderService> loaderServiceMap          = null;
  @Autowired
  private              PlatformTransactionManager     transactionManager        = null;
  @Autowired
  private              NDataInstallationStatusService installationStatusService = null;
  private              ApplicationEventPublisher      eventPublisher            = null;

  @Override
  @SuppressWarnings("ConstantConditions")
  public void doLoad() {
    if (!CollectionUtils.isEmpty(this.loaderServiceMap)) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug(String.format("Loading data out of %d DataLoaderServices", this.loaderServiceMap.size()));
      }

      this.loaderServiceMap
        .keySet()
        .forEach(key -> {

          LocalMarker marker = null;
          final DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
          definition.setReadOnly(false);
          definition.setTimeout(10000);

          TransactionStatus transaction = this.transactionManager.getTransaction(definition);

          try {

            this.publishPreEvent(key);
            marker = this.executeLoader(key);
            if (marker.getError() == null) {
              this.transactionManager.commit(transaction);
              transaction = null;
              this.installationStatusService.onSuccessfulInstallation(
                marker.getHash(),
                marker.getPath(),
                marker.handlerClass
              );
            } else {
              throw marker.getError();
            }

            this.publishPostEvent(key);

          } catch (Throwable exp) {
            LOGGER.error(String.format("Failed to load data for loader=%s", key), exp);
          } finally {
            if (transaction != null) {
              this.transactionManager.rollback(transaction);
              this.installationStatusService.onFailureInstallation(
                marker.getHash(),
                marker.getPath(),
                marker.handlerClass,
                marker.getError()
              );
            }
          }
        });
    }
  }

  private void publishPreEvent(final String key) {
    if (this.eventPublisher != null) {
      this.eventPublisher.publishEvent(new PreDataLoadEvent(key, this));
    }
  }

  private LocalMarker executeLoader(final String key) throws Exception {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(String.format("Loading data from %s", key));
    }
    final Stopwatch stopwatch = Stopwatch.createStarted();

    final DataLoaderService service = this.loaderServiceMap.get(key);
    final DataLoaderService.InstallationMarker marker = service.loadData();


    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(String.format("Loaded data from %s in %d ms", key, stopwatch.stop().elapsed(TimeUnit.MILLISECONDS)));
    }

    return new LocalMarker(marker, ClassUtils.getUserClass(service));
  }

  private void publishPostEvent(final String key) {
    if (this.eventPublisher != null) {
      this.eventPublisher.publishEvent(new PostDataLoadEvent(key, this));
    }
  }

  @Override
  public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
    this.eventPublisher = applicationEventPublisher;
  }

  private static class LocalMarker {
    private final DataLoaderService.InstallationMarker marker;
    private final Class<?>                             handlerClass;

    public LocalMarker(final DataLoaderService.InstallationMarker marker, final Class<?> handlerClass) {
      this.marker = marker;
      this.handlerClass = handlerClass;
    }

    public long getHash() {
      return marker.getHash();
    }

    public String getPath() {
      return marker.getPath();
    }

    public Throwable getError() {
      return marker.getError();
    }
  }
}

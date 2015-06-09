package org.agatom.springatom.data.repo.repositories.notification;

import org.agatom.springatom.data.model.notification.NNotification;
import org.agatom.springatom.data.model.notification.NNotificationSubject;
import org.agatom.springatom.data.model.notification.NNotificationTarget;
import org.agatom.springatom.data.repo.repositories.NRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;


@RepositoryRestResource(
  itemResourceRel = NNotificationRepository.ITEM_REL,
  itemResourceDescription = @Description("Get single notification"),
  collectionResourceRel = NNotificationRepository.COLLECTION_REL,
  collectionResourceDescription = @Description("Get collection of notifications"),
  path = NNotificationRepository.REST_REPO_PATH
)
public interface NNotificationRepository
  extends NRepository<NNotification> {
  String ITEM_REL       = "notification";
  String COLLECTION_REL = "notifications";
  String REST_REPO_PATH = ITEM_REL;

  @RestResource(exported = false)
  Collection<NNotification> findBySubject(final NNotificationSubject associate);

  @RestResource(exported = false)
  Collection<NNotification> findByTarget(final NNotificationTarget target);

  @RestResource(path = "subject")
  Page<NNotification> findBySubject(@Param("subject") final NNotificationSubject subject, final Pageable pageable);

  @RestResource(path = "target")
  Page<NNotification> findByTarget(@Param("target") final NNotificationTarget subject, final Pageable pageable);
}

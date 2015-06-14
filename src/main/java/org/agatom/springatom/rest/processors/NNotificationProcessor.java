package org.agatom.springatom.rest.processors;

import org.agatom.springatom.data.model.notification.NNotification;
import org.agatom.springatom.data.model.notification.NNotificationSubject;
import org.agatom.springatom.data.model.notification.NNotificationTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

@Component
class NNotificationProcessor
  implements ResourceProcessor<Resource<NNotification>> {
  @Autowired
  @SuppressWarnings("SpringJavaAutowiringInspection")
  private RepositoryEntityLinks repositoryEntityLinks = null;

  @Override
  public Resource<NNotification> process(final Resource<NNotification> resource) {
    final NNotification content = resource.getContent();
    final NNotificationSubject subject = content.getSubject();
    final NNotificationTarget target = content.getTarget();

    resource.add(
      this.repositoryEntityLinks
        .linkForSingleResource(subject.getAssociateClass(), subject.getAssociateId())
        .withRel("subject")
    );
    resource.add(
      this.repositoryEntityLinks
        .linkForSingleResource(target.getAssociateClass(), target.getAssociateId())
        .withRel("target")
    );

    return resource;
  }
}

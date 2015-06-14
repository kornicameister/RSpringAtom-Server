package org.agatom.springatom.data.listeners.impl;

import org.agatom.springatom.data.model.appointment.NAppointment;
import org.agatom.springatom.data.model.notification.NNotification;
import org.agatom.springatom.data.model.user.NUser;
import org.agatom.springatom.data.repo.repositories.notification.NNotificationRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(NAppointment.class)
public class NAppointmentEventHandler {
  @Autowired
  private NNotificationRepository repository = null;

  @HandleAfterSave
  public void handleAfterSave(final NAppointment appointment) {
    this.sendNotification(appointment);
  }

  protected void sendNotification(final NAppointment appointment) {
    final NUser assignee = appointment.getAssignee();
    final DateTime assigned = appointment.getAssigned();

    this.repository.save(
      new NNotification()
        .setSubject(appointment)
        .setTarget(assignee)
        .setSent(DateTime.now())
        .setMessage(
          String.format("You've been at %s assigned to conduct appointment", assigned.toString("yyyy-MM-DD"))
        )
    );
  }

  @HandleAfterCreate
  public void handleAfterCreate(final NAppointment appointment) {
    this.sendNotification(appointment);
  }


}

package org.agatom.springatom.data.model.appointment;

import org.agatom.springatom.data.model.calendar.NCalendar;
import org.agatom.springatom.data.model.link.NObjectToObjectLink;
import org.agatom.springatom.data.types.appointment.CalendarAppointment;

import javax.persistence.*;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Table
@Entity
@AssociationOverrides({
        @AssociationOverride(name = NObjectToObjectLink.ROLE_A_ASSOC, joinColumns = @JoinColumn(name = "nc_id")),
        @AssociationOverride(name = NObjectToObjectLink.ROLE_B_ASSOC, joinColumns = @JoinColumn(name = "na_id"))
})
public class NAppointmentToCalendarLink
        extends NObjectToObjectLink<NCalendar, NAppointment>
        implements CalendarAppointment<NCalendar, NAppointment> {
    private static final long serialVersionUID = -4508199147471857525L;

    @Override
    public NCalendar getCalendar() {
        return this.getRoleA();
    }

    @Override
    public NAppointment getAppointment() {
        return this.getRoleB();
    }

    public NAppointmentToCalendarLink setAppointment(final NAppointment appointment) {
        return (NAppointmentToCalendarLink) this.setRoleB(appointment);
    }

    public NAppointmentToCalendarLink setCalendar(final NCalendar calendar) {
        return (NAppointmentToCalendarLink) this.setRoleA(calendar);
    }
}

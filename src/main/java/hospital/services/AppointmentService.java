package hospital.services;

import hospital.exceptions.AppointmentConflictException;
import hospital.exceptions.EntityNotFoundException;
import hospital.exceptions.InvalidInputException;
import hospital.models.Appointment;
import hospital.persistence.AppointmentRepository;
import hospital.utils.IdGenerator;
import hospital.utils.Validator;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class AppointmentService {

    private final List<Appointment> appointments;
    private final AppointmentRepository repository;

    public AppointmentService() {
        this.repository = new AppointmentRepository();
        this.appointments = repository.loadAll();
    }

    public List<Appointment> getAllAppointments() {
        return appointments;
    }

    public Appointment findById(int id) throws EntityNotFoundException {
        for (Appointment a : appointments) {
            if (a.getAppointmentId() == id) return a;
        }
        throw new EntityNotFoundException("Appointment", String.valueOf(id));
    }

    public List<Appointment> getByPatient(int patientId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : appointments) {
            if (a.getPatientId() == patientId) result.add(a);
        }
        return result;
    }

    public List<Appointment> getByDoctor(int doctorId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : appointments) {
            if (a.getDoctorId() == doctorId) result.add(a);
        }
        return result;
    }

    public Appointment book(int patientId, int doctorId,
                            LocalDate date, LocalTime time, String reason)
            throws InvalidInputException, AppointmentConflictException {

        Validator.validateDate(date);
        Validator.validateTime(time);
        Validator.validateNotEmpty(reason, "reason");

        for (Appointment a : appointments) {
            boolean sameDoctor = a.getDoctorId() == doctorId;
            boolean sameSlot = a.getDate().equals(date) && a.getTime().equals(time);
            boolean isActive = a.getStatus() != Appointment.Status.REFUSED;

            if (sameDoctor && sameSlot && isActive) {
                throw new AppointmentConflictException(doctorId, date, time);
            }
        }

        int id = IdGenerator.generateAppointmentId(appointments.size());

        Appointment appt = new Appointment(
                id, patientId, doctorId, date, time, reason
        );

        appointments.add(appt);
        persist();
        return appt;
    }

    public void accept(int id) throws EntityNotFoundException {
        findById(id).setStatus(Appointment.Status.ACCEPTED);
        persist();
    }

    public void refuse(int id) throws EntityNotFoundException {
        findById(id).setStatus(Appointment.Status.REFUSED);
        persist();
    }

    public void complete(int id) throws EntityNotFoundException {
        findById(id).setStatus(Appointment.Status.COMPLETED);
        persist();
    }

    public void delete(int id) throws EntityNotFoundException {
        appointments.remove(findById(id));
        persist();
    }

    private void persist() {
        repository.saveAll(appointments);
    }
}
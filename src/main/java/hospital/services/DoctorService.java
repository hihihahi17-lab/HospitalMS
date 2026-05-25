package hospital.services;

import hospital.exceptions.EntityNotFoundException;
import hospital.exceptions.InvalidInputException;
import hospital.models.Doctor;
import hospital.persistence.DoctorRepository;
import hospital.utils.IdGenerator;
import hospital.utils.Validator;

import java.util.ArrayList;
import java.util.List;

public class DoctorService {

    private final List<Doctor> doctors;
    private final DoctorRepository repository;

    public DoctorService() {
        this.repository = new DoctorRepository();
        this.doctors = repository.loadAll();
    }

    public List<Doctor> getAllDoctors() {
        return doctors;
    }

    public Doctor findById(int id) throws EntityNotFoundException {
        for (Doctor d : doctors) {
            if (d.getId() == id) return d;
        }
        throw new EntityNotFoundException("Doctor", String.valueOf(id));
    }

    public List<Doctor> findBySpecialty(String specialty) {
        List<Doctor> results = new ArrayList<>();
        String lc = specialty.toLowerCase();

        for (Doctor d : doctors) {
            if (d.getSpecialty().toLowerCase().contains(lc)) {
                results.add(d);
            }
        }
        return results;
    }

    public Doctor addDoctor(String firstName, String lastName, String email,
                            String password, String phone, String specialty,
                            String license, double fee)
            throws InvalidInputException {

        Validator.validateNotEmpty(firstName, "firstName");
        Validator.validateNotEmpty(lastName, "lastName");
        Validator.validateEmail(email);
        Validator.validatePassword(password);
        Validator.validatePhone(phone);
        Validator.validateNotEmpty(specialty, "specialty");
        Validator.validateNotEmpty(license, "licenseNumber");
        Validator.validateFee(fee);

        int id = IdGenerator.generateDoctorId(doctors.size());

        Doctor doctor = new Doctor(
                id, firstName, lastName, email,
                password, phone, specialty, license, fee
        );

        doctors.add(doctor);
        persist();
        return doctor;
    }

    public void update(int id, String phone, String specialty, double fee)
            throws EntityNotFoundException, InvalidInputException {

        Doctor d = findById(id);

        Validator.validatePhone(phone);
        Validator.validateNotEmpty(specialty, "specialty");
        Validator.validateFee(fee);

        d.setPhoneNumber(phone);
        d.setSpecialty(specialty);
        d.setConsultationFee(fee);

        persist();
    }

    public void delete(int id) throws EntityNotFoundException {
        Doctor d = findById(id);
        doctors.remove(d);
        persist();
    }

    private void persist() {
        repository.saveAll(doctors);
    }
}
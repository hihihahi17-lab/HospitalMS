package hospital.services;

import hospital.exceptions.EntityNotFoundException;
import hospital.exceptions.InvalidInputException;
import hospital.models.Patient;
import hospital.persistence.PatientRepository;
import hospital.utils.IdGenerator;
import hospital.utils.Validator;

import java.util.ArrayList;
import java.util.List;

public class PatientService {

    private final List<Patient> patients;
    private final PatientRepository repository;

    public PatientService() {
        this.repository = new PatientRepository();
        this.patients = repository.loadAll();
    }

    public List<Patient> getAllPatients() {
        return patients;
    }

    public Patient findById(int id) throws EntityNotFoundException {
        for (Patient p : patients) {
            if (p.getId() == id) return p;
        }
        throw new EntityNotFoundException("Patient", String.valueOf(id));
    }

    public List<Patient> searchByName(String keyword) {
        List<Patient> results = new ArrayList<>();
        String lc = keyword.toLowerCase();

        for (Patient p : patients) {
            if (p.getFullName().toLowerCase().contains(lc)) {
                results.add(p);
            }
        }
        return results;
    }

    public Patient register(String firstName, String lastName, String email,
                            String password, String phone, int age,
                            String bloodType, String address)
            throws InvalidInputException {

        Validator.validateNotEmpty(firstName, "firstName");
        Validator.validateNotEmpty(lastName, "lastName");
        Validator.validateEmail(email);
        Validator.validatePassword(password);
        Validator.validatePhone(phone);
        Validator.validateAge(age);
        Validator.validateNotEmpty(bloodType, "bloodType");
        Validator.validateNotEmpty(address, "address");

        int id = IdGenerator.generatePatientId(patients.size());

        Patient patient = new Patient(
                id, firstName, lastName, email,
                password, phone, age, bloodType, address
        );

        patients.add(patient);
        persist();
        return patient;
    }

    public void update(int id, String phone, String address, int age)
            throws EntityNotFoundException, InvalidInputException {

        Patient p = findById(id);

        Validator.validatePhone(phone);
        Validator.validateAge(age);
        Validator.validateNotEmpty(address, "address");

        p.setPhoneNumber(phone);
        p.setAddress(address);
        p.setAge(age);

        persist();
    }

    public void delete(int id) throws EntityNotFoundException {
        Patient p = findById(id);
        patients.remove(p);
        persist();
    }

    private void persist() {
        repository.saveAll(patients);
    }
}
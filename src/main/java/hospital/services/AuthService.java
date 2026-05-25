package hospital.services;

import hospital.exceptions.AuthenticationException;
import hospital.models.Admin;
import hospital.models.Doctor;
import hospital.models.Patient;
import hospital.models.Person;

import java.util.List;

public class AuthService {

    public Patient loginPatient(List<Patient> patients, String email, String password)
            throws AuthenticationException {
        for (Patient p : patients) {
            if (p.getEmail().equalsIgnoreCase(email) && p.checkPassword(password)) {
                return p;
            }
        }
        throw new AuthenticationException();
    }

    public Doctor loginDoctor(List<Doctor> doctors, String email, String password)
            throws AuthenticationException {
        for (Doctor d : doctors) {
            if (d.getEmail().equalsIgnoreCase(email) && d.checkPassword(password)) {
                return d;
            }
        }
        throw new AuthenticationException();
    }

    public Admin loginAdmin(List<Admin> admins, String email, String password)
            throws AuthenticationException {
        for (Admin a : admins) {
            if (a.getEmail().equalsIgnoreCase(email) && a.checkPassword(password)) {
                return a;
            }
        }
        throw new AuthenticationException();
    }

    public boolean emailExistsInList(List<? extends Person> persons, String email) {
        for (Person p : persons) {
            if (p.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }
}
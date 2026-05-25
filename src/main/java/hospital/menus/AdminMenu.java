package hospital.menus;

import hospital.exceptions.EntityNotFoundException;
import hospital.exceptions.InvalidInputException;
import hospital.models.*;
import hospital.services.*;
import hospital.utils.ConsoleUtils;

import java.util.List;

public class AdminMenu {

    private final Admin loggedIn;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final MedicalRecordService recordService;
    private final AuthService authService;

    public AdminMenu(Admin loggedIn,
                     DoctorService doctorService,
                     PatientService patientService,
                     AppointmentService appointmentService,
                     MedicalRecordService recordService,
                     AuthService authService) {

        this.loggedIn = loggedIn;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.recordService = recordService;
        this.authService = authService;
    }

    public void show() {
        boolean running = true;

        while (running) {
            ConsoleUtils.printHeader("ADMIN MENU — " + loggedIn.getFullName());

            System.out.println("  1.  View My Profile");
            System.out.println("  2.  Add New Doctor");
            System.out.println("  3.  Delete a Doctor");
            System.out.println("  4.  List All Doctors");
            System.out.println("  5.  List All Patients");
            System.out.println("  6.  Delete a Patient");
            System.out.println("  7.  List All Appointments");
            System.out.println("  8.  List All Medical Records");
            System.out.println("  0.  Logout");

            ConsoleUtils.printDivider();

            int choice = ConsoleUtils.readInt("  Choice: ");

            switch (choice) {
                case 1 -> viewProfile();
                case 2 -> addDoctor();
                case 3 -> deleteDoctor();
                case 4 -> listDoctors();
                case 5 -> listPatients();
                case 6 -> deletePatient();
                case 7 -> listAppointments();
                case 8 -> listRecords();
                case 0 -> {
                    ConsoleUtils.printInfo("Admin logged out.");
                    running = false;
                }
                default -> ConsoleUtils.printError("Invalid option. Choose between 0 and 8.");
            }

            if (running) ConsoleUtils.pressEnterToContinue();
        }
    }

    private void viewProfile() {
        ConsoleUtils.printHeader("MY PROFILE");
        loggedIn.displayDetails();
    }

    private void addDoctor() {
        ConsoleUtils.printHeader("ADD NEW DOCTOR");

        String firstName = ConsoleUtils.readLine("  First Name       : ");
        String lastName = ConsoleUtils.readLine("  Last Name        : ");
        String email = ConsoleUtils.readLine("  Email            : ");

        if (authService.emailExistsInList(doctorService.getAllDoctors(), email)) {
            ConsoleUtils.printError("A doctor with this email already exists.");
            return;
        }

        String password = ConsoleUtils.readLine("  Password         : ");
        String phone = ConsoleUtils.readLine("  Phone            : ");
        String specialty = ConsoleUtils.readLine("  Specialty        : ");
        String license = ConsoleUtils.readLine("  License Number   : ");
        double fee = ConsoleUtils.readDouble("  Consultation Fee : ");

        try {
            Doctor d = doctorService.addDoctor(
                    firstName, lastName, email, password,
                    phone, specialty, license, fee
            );

            ConsoleUtils.printSuccess("Doctor added! ID: " + d.getId());

        } catch (InvalidInputException e) {
            ConsoleUtils.printError(e.getMessage());
        }
    }

    private void deleteDoctor() {
        ConsoleUtils.printHeader("DELETE DOCTOR");

        listDoctors();

        int id = ConsoleUtils.readInt("  Doctor ID to delete: ");

        try {
            doctorService.delete(id);
            ConsoleUtils.printSuccess("Doctor [" + id + "] deleted.");
        } catch (EntityNotFoundException e) {
            ConsoleUtils.printError(e.getMessage());
        }
    }

    private void listDoctors() {
        ConsoleUtils.printHeader("ALL DOCTORS");

        List<Doctor> doctors = doctorService.getAllDoctors();

        if (doctors.isEmpty()) {
            ConsoleUtils.printInfo("No doctors registered.");
        } else {
            for (Doctor d : doctors) {
                System.out.println("  " + d.getInfo());
            }
        }
    }

    private void listPatients() {
        ConsoleUtils.printHeader("ALL PATIENTS");

        List<Patient> patients = patientService.getAllPatients();

        if (patients.isEmpty()) {
            ConsoleUtils.printInfo("No patients registered.");
        } else {
            for (Patient p : patients) {
                System.out.println("  " + p.getInfo());
            }
        }
    }

    private void deletePatient() {
        ConsoleUtils.printHeader("DELETE PATIENT");

        listPatients();

        int id = ConsoleUtils.readInt("  Patient ID to delete: ");

        try {
            patientService.delete(id);
            ConsoleUtils.printSuccess("Patient [" + id + "] deleted.");
        } catch (EntityNotFoundException e) {
            ConsoleUtils.printError(e.getMessage());
        }
    }

    private void listAppointments() {
        ConsoleUtils.printHeader("ALL APPOINTMENTS");

        List<Appointment> list = appointmentService.getAllAppointments();

        if (list.isEmpty()) {
            ConsoleUtils.printInfo("No appointments found.");
        } else {
            for (Appointment a : list) {
                a.displayDetails();
            }
        }
    }

    private void listRecords() {
        ConsoleUtils.printHeader("ALL MEDICAL RECORDS");

        List<MedicalRecord> list = recordService.getAllRecords();

        if (list.isEmpty()) {
            ConsoleUtils.printInfo("No medical records found.");
        } else {
            for (MedicalRecord r : list) {
                r.displayDetails();
            }
        }
    }
}
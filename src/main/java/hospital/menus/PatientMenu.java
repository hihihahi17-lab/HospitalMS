package hospital.menus;

import hospital.exceptions.*;
import hospital.models.*;
import hospital.services.*;
import hospital.utils.ConsoleUtils;

import java.util.List;

public class PatientMenu {

    private Patient loggedIn;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final MedicalRecordService recordService;

    public PatientMenu(Patient loggedIn,
                       PatientService patientService,
                       DoctorService doctorService,
                       AppointmentService appointmentService,
                       MedicalRecordService recordService) {

        this.loggedIn = loggedIn;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.recordService = recordService;
    }

    public void show() {
        boolean running = true;

        while (running) {
            ConsoleUtils.printHeader("PATIENT MENU — " + loggedIn.getFirstName());

            System.out.println("  1.  View My Profile");
            System.out.println("  2.  Update My Profile");
            System.out.println("  3.  Search Doctor by Specialty");
            System.out.println("  4.  View All Doctors");
            System.out.println("  5.  Book an Appointment");
            System.out.println("  6.  View My Appointments");
            System.out.println("  7.  View My Medical Records");
            System.out.println("  0.  Logout");

            ConsoleUtils.printDivider();

            int choice = ConsoleUtils.readInt("  Choice: ");

            switch (choice) {
                case 1 -> viewProfile();
                case 2 -> updateProfile();
                case 3 -> searchDoctor();
                case 4 -> viewAllDoctors();
                case 5 -> bookAppointment();
                case 6 -> viewAppointments();
                case 7 -> viewMedicalRecords();
                case 0 -> {
                    ConsoleUtils.printInfo("Logged out.");
                    running = false;
                }
                default -> ConsoleUtils.printError("Invalid choice. Please enter 0–7.");
            }

            if (running) ConsoleUtils.pressEnterToContinue();
        }
    }

    private void viewProfile() {
        ConsoleUtils.printHeader("MY PROFILE");
        loggedIn.displayDetails();
    }

    private void updateProfile() {
        ConsoleUtils.printHeader("UPDATE MY PROFILE");

        String phone = ConsoleUtils.readLine("  New phone (10 digits): ");
        String address = ConsoleUtils.readLine("  New address          : ");
        int age = ConsoleUtils.readInt("  New age              : ");

        try {
            patientService.update(loggedIn.getId(), phone, address, age);
            loggedIn = patientService.findById(loggedIn.getId());
            ConsoleUtils.printSuccess("Profile updated successfully.");
        } catch (EntityNotFoundException | InvalidInputException e) {
            ConsoleUtils.printError(e.getMessage());
        }
    }

    private void searchDoctor() {
        ConsoleUtils.printHeader("SEARCH DOCTOR BY SPECIALTY");

        String kw = ConsoleUtils.readLine("  Specialty keyword: ");
        List<Doctor> results = doctorService.findBySpecialty(kw);

        if (results.isEmpty()) {
            ConsoleUtils.printInfo("No doctors found for: " + kw);
        } else {
            ConsoleUtils.printInfo("Found " + results.size() + " doctor(s):");
            for (Doctor d : results) d.displayDetails();
        }
    }

    private void viewAllDoctors() {
        ConsoleUtils.printHeader("ALL AVAILABLE DOCTORS");

        List<Doctor> doctors = doctorService.getAllDoctors();

        if (doctors.isEmpty()) {
            ConsoleUtils.printInfo("No doctors registered yet.");
        } else {
            for (Doctor d : doctors) {
                System.out.println("  " + d.getInfo());
            }
        }
    }

    private void bookAppointment() {
        ConsoleUtils.printHeader("BOOK AN APPOINTMENT");

        viewAllDoctors();

        int doctorId = ConsoleUtils.readInt("  Doctor ID         : ");
        String date = ConsoleUtils.readLine("  Date (YYYY-MM-DD) : ");
        String time = ConsoleUtils.readLine("  Time (HH:MM)      : ");
        String reason = ConsoleUtils.readLine("  Reason            : ");

        try {
            Appointment a = appointmentService.book(
                    loggedIn.getId(),
                    doctorId,
                    date,
                    time,
                    reason
            );

            ConsoleUtils.printSuccess(
                    "Appointment booked! ID: " + a.getAppointmentId()
                            + " — Status: PENDING (awaiting doctor approval)"
            );

        } catch (AppointmentConflictException | InvalidInputException e) {
            ConsoleUtils.printError(e.getMessage());
        }
    }

    private void viewAppointments() {
        ConsoleUtils.printHeader("MY APPOINTMENTS");

        List<Appointment> list = appointmentService.getByPatient(loggedIn.getId());

        if (list.isEmpty()) {
            ConsoleUtils.printInfo("You have no appointments.");
        } else {
            for (Appointment a : list) a.displayDetails();
        }
    }

    private void viewMedicalRecords() {
        ConsoleUtils.printHeader("MY MEDICAL RECORDS");

        List<MedicalRecord> records =
                recordService.getByPatient(loggedIn.getId());

        if (records.isEmpty()) {
            ConsoleUtils.printInfo("No medical records found for your account.");
        } else {
            for (MedicalRecord r : records) r.displayDetails();
        }
    }
}
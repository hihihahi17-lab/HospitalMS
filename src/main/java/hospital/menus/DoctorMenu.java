package hospital.menus;

import hospital.exceptions.EntityNotFoundException;
import hospital.models.*;
import hospital.services.*;
import hospital.utils.ConsoleUtils;

import java.time.LocalDate;
import java.util.List;

public class DoctorMenu {

    private final Doctor loggedIn;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final MedicalRecordService recordService;
    private final PatientService patientService;

    public DoctorMenu(Doctor loggedIn,
                      DoctorService doctorService,
                      AppointmentService appointmentService,
                      MedicalRecordService recordService,
                      PatientService patientService) {

        this.loggedIn = loggedIn;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.recordService = recordService;
        this.patientService = patientService;
    }

    public void show() {
        boolean running = true;

        while (running) {
            ConsoleUtils.printHeader("DOCTOR MENU — Dr. " + loggedIn.getFullName());

            System.out.println("  1.  View My Profile");
            System.out.println("  2.  View Pending Appointments");
            System.out.println("  3.  Accept an Appointment");
            System.out.println("  4.  Refuse an Appointment");
            System.out.println("  5.  Add Medical Record (post-consultation)");
            System.out.println("  6.  View All My Appointments");
            System.out.println("  7.  View Medical Records I Created");
            System.out.println("  0.  Logout");

            ConsoleUtils.printDivider();

            int choice = ConsoleUtils.readInt("  Choice: ");

            switch (choice) {
                case 1 -> loggedIn.displayDetails();
                case 2 -> viewPending();
                case 3 -> acceptAppointment();
                case 4 -> refuseAppointment();
                case 5 -> addRecord();
                case 6 -> viewAllAppointments();
                case 7 -> viewMyRecords();
                case 0 -> {
                    ConsoleUtils.printInfo("Logged out.");
                    running = false;
                }
                default -> ConsoleUtils.printError("Invalid choice. Enter 0–7.");
            }

            if (running) ConsoleUtils.pressEnterToContinue();
        }
    }

    private void viewPending() {
        ConsoleUtils.printHeader("PENDING APPOINTMENTS");

        List<Appointment> all = appointmentService.getByDoctor(loggedIn.getId());
        boolean found = false;

        for (Appointment a : all) {
            if (a.getStatus() == Appointment.Status.PENDING) {
                a.displayDetails();

                try {
                    Patient p = patientService.findById(a.getPatientId());
                    System.out.println("  Patient Name: " + p.getFullName()
                            + "  |  Phone: " + p.getPhoneNumber());
                } catch (EntityNotFoundException ignored) {}

                found = true;
            }
        }

        if (!found) {
            ConsoleUtils.printInfo("No pending appointments.");
        }
    }

    private void acceptAppointment() {
        ConsoleUtils.printHeader("ACCEPT APPOINTMENT");

        int id = ConsoleUtils.readInt("  Appointment ID: ");

        try {
            appointmentService.accept(id);
            ConsoleUtils.printSuccess("Appointment [" + id + "] accepted.");
        } catch (EntityNotFoundException e) {
            ConsoleUtils.printError(e.getMessage());
        }
    }

    private void refuseAppointment() {
        ConsoleUtils.printHeader("REFUSE APPOINTMENT");

        int id = ConsoleUtils.readInt("  Appointment ID: ");

        try {
            appointmentService.refuse(id);
            ConsoleUtils.printSuccess("Appointment [" + id + "] refused.");
        } catch (EntityNotFoundException e) {
            ConsoleUtils.printError(e.getMessage());
        }
    }

    private void addRecord() {
        ConsoleUtils.printHeader("ADD MEDICAL RECORD");

        int apptId = ConsoleUtils.readInt("  Appointment ID : ");
        int patientId = ConsoleUtils.readInt("  Patient ID     : ");
        String diagnosis = ConsoleUtils.readLine("  Diagnosis      : ");
        String notes = ConsoleUtils.readLine("  Notes          : ");
        String today = LocalDate.now().toString();

        try {
            appointmentService.complete(apptId);
        } catch (EntityNotFoundException e) {
            ConsoleUtils.printError("Appointment not found: " + e.getMessage());
        }

        MedicalRecord rec = recordService.addRecord(
                patientId,
                loggedIn.getId(),
                apptId,
                diagnosis,
                notes,
                today
        );

        ConsoleUtils.printSuccess("Medical record created. ID: " + rec.getRecordId());
    }

    private void viewAllAppointments() {
        ConsoleUtils.printHeader("ALL MY APPOINTMENTS");

        List<Appointment> list = appointmentService.getByDoctor(loggedIn.getId());

        if (list.isEmpty()) {
            ConsoleUtils.printInfo("No appointments found.");
        } else {
            for (Appointment a : list) {
                a.displayDetails();
            }
        }
    }

    private void viewMyRecords() {
        ConsoleUtils.printHeader("RECORDS I CREATED");

        List<MedicalRecord> list = recordService.getByDoctor(loggedIn.getId());

        if (list.isEmpty()) {
            ConsoleUtils.printInfo("No records created yet.");
        } else {
            for (MedicalRecord r : list) {
                r.displayDetails();
            }
        }
    }
}
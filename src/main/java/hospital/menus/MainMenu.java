package hospital.menus;

import hospital.exceptions.AuthenticationException;
import hospital.exceptions.InvalidInputException;
import hospital.models.*;
import hospital.services.*;
import hospital.utils.ConsoleUtils;

public class MainMenu {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final MedicalRecordService recordService;
    private final AuthService authService;
    private final AdminService adminService;

    public MainMenu() {
        this.patientService = new PatientService();
        this.doctorService = new DoctorService();
        this.appointmentService = new AppointmentService();
        this.recordService = new MedicalRecordService();
        this.authService = new AuthService();
        this.adminService = new AdminService();
    }

    public void start() {
        printWelcomeBanner();

        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("  Select your role:");
            System.out.println("  ─────────────────────────────────────────");
            System.out.println("  1.  Patient Login");
            System.out.println("  2.  Patient Register");
            System.out.println("  3.  Doctor Login");
            System.out.println("  4.  Admin Login");
            System.out.println("  0.  Exit");

            ConsoleUtils.printDivider();

            int choice = ConsoleUtils.readInt("  Choice: ");

            switch (choice) {
                case 1 -> patientLogin();
                case 2 -> patientRegister();
                case 3 -> doctorLogin();
                case 4 -> adminLogin();
                case 0 -> {
                    printGoodbye();
                    running = false;
                }
                default -> ConsoleUtils.printError("Please enter 0, 1, 2, 3, or 4.");
            }
        }
    }

    private void patientLogin() {
        ConsoleUtils.printHeader("PATIENT LOGIN");

        String email = ConsoleUtils.readLine("  Email    : ");
        String password = ConsoleUtils.readLine("  Password : ");

        try {
            Patient p = authService.loginPatient(
                    patientService.getAllPatients(),
                    email,
                    password
            );

            ConsoleUtils.printSuccess("Welcome, " + p.getFullName() + "!");

            new PatientMenu(
                    p,
                    patientService,
                    doctorService,
                    appointmentService,
                    recordService
            ).show();

        } catch (AuthenticationException e) {
            ConsoleUtils.printError(e.getMessage());
            ConsoleUtils.pressEnterToContinue();
        }
    }

    private void patientRegister() {
        ConsoleUtils.printHeader("PATIENT REGISTRATION");

        String firstName = ConsoleUtils.readLine("  First Name : ");
        String lastName = ConsoleUtils.readLine("  Last Name  : ");
        String email = ConsoleUtils.readLine("  Email      : ");

        if (authService.emailExistsInList(patientService.getAllPatients(), email)) {
            ConsoleUtils.printError("This email is already registered. Please login.");
            ConsoleUtils.pressEnterToContinue();
            return;
        }

        String password = ConsoleUtils.readLine("  Password   : ");
        String phone = ConsoleUtils.readLine("  Phone      : ");
        int age = ConsoleUtils.readInt("  Age        : ");
        String bloodType = ConsoleUtils.readLine("  Blood Type : ");
        String address = ConsoleUtils.readLine("  Address    : ");

        try {
            Patient p = patientService.register(
                    firstName, lastName, email, password,
                    phone, age, bloodType, address
            );

            ConsoleUtils.printSuccess("Registration successful! Your ID: " + p.getId());

        } catch (InvalidInputException e) {
            ConsoleUtils.printError(e.getMessage());
        }

        ConsoleUtils.pressEnterToContinue();
    }

    private void doctorLogin() {
        ConsoleUtils.printHeader("DOCTOR LOGIN");

        String email = ConsoleUtils.readLine("  Email    : ");
        String password = ConsoleUtils.readLine("  Password : ");

        try {
            Doctor d = authService.loginDoctor(
                    doctorService.getAllDoctors(),
                    email,
                    password
            );

            ConsoleUtils.printSuccess("Welcome, Dr. " + d.getFullName() + "!");

            new DoctorMenu(
                    d,
                    doctorService,
                    appointmentService,
                    recordService,
                    patientService
            ).show();

        } catch (AuthenticationException e) {
            ConsoleUtils.printError(e.getMessage());
            ConsoleUtils.pressEnterToContinue();
        }
    }

    private void adminLogin() {
        ConsoleUtils.printHeader("ADMIN LOGIN");

        String email = ConsoleUtils.readLine("  Email    : ");
        String password = ConsoleUtils.readLine("  Password : ");

        try {
            Admin a = authService.loginAdmin(
                    adminService.getAllAdmins(),
                    email,
                    password
            );

            ConsoleUtils.printSuccess("Welcome, Admin " + a.getFullName() + "!");

            new AdminMenu(
                    a,
                    doctorService,
                    patientService,
                    appointmentService,
                    recordService,
                    authService
            ).show();

        } catch (AuthenticationException e) {
            ConsoleUtils.printError(e.getMessage());
            ConsoleUtils.pressEnterToContinue();
        }
    }

    private void printWelcomeBanner() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║        HOSPITAL MANAGEMENT SYSTEM  v2.0         ║");
        System.out.println("  ╚══════════════════════════════════════════════════╝");
    }

    private void printGoodbye() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║     Thank you for using HospitalMS. Goodbye!    ║");
        System.out.println("  ╚══════════════════════════════════════════════════╝");
        System.out.println();
    }
}
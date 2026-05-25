package hospital.models;
public class Doctor extends Person implements Displayable, Persistable {

    private String specialty;
    private String licenseNumber;
    private double consultationFee;

    public Doctor(int id, String firstName, String lastName,
                  String email, String password, String phoneNumber,
                  String specialty, String licenseNumber, double consultationFee) {
        super(id, firstName, lastName, email, password, phoneNumber);
        this.specialty       = specialty;
        this.licenseNumber   = licenseNumber;
        this.consultationFee = consultationFee;
    }

    
    @Override
    public String getRole() { return "Doctor"; }

    @Override
    public String getInfo() {
        return String.format(
            "[Doctor] ID: %-10s | Dr. %-20s | Specialty: %-18s | Fee: %.2f MAD",
            getId(), getFullName(), specialty, consultationFee);
    }

    
    @Override
    public void displayDetails() {
        System.out.println("  ┌──────────────────────────────────────────────────┐");
        System.out.println("  │                  DOCTOR DETAILS                  │");
        System.out.println("  ├──────────────────────────────────────────────────┤");
        System.out.printf( "  │  ID           : %-33d%n", super.getId());
        System.out.printf( "  │  Full Name    : Dr. %-30s│%n", super.getFullName());
        System.out.printf( "  │  Specialty    : %-33s│%n", specialty);
        System.out.printf( "  │  License No   : %-33s│%n", licenseNumber);
        System.out.printf( "  │  Consult Fee  : %-33s│%n",
            String.format("%.2f MAD", consultationFee));
        System.out.printf( "  │  Phone        : %-33s│%n", super.getPhoneNumber());
        System.out.printf( "  │  Email        : %-33s│%n", super.getEmail());
        System.out.println("  └──────────────────────────────────────────────────┘");
    }

    
    
    @Override
    public String toCsv() {
        return super.toCsv() + "," + specialty + "," + licenseNumber + "," + consultationFee;
    }

    
    public static Doctor fromCsv(String line) {
        String[] p = line.split(",", -1);
        return new Doctor(
            Integer.parseInt(p[0].trim()), p[1].trim(), p[2].trim(),
            p[3].trim(), p[4].trim(), p[5].trim(),
            p[6].trim(), p[7].trim(),
            Double.parseDouble(p[8].trim())
        );
    }

    public String getSpecialty()       { return specialty; }
    public String getLicenseNumber()   { return licenseNumber; }
    public double getConsultationFee() { return consultationFee; }

    public void setSpecialty(String v)       { this.specialty       = v; }
    public void setLicenseNumber(String v)   { this.licenseNumber   = v; }
    public void setConsultationFee(double v) { this.consultationFee = v; }
}
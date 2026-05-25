package hospital.models;
public class Admin extends Person implements Displayable {

     private String adminLevel; 
    public Admin(int  id, String firstName, String lastName,
            String email, String password, String phoneNumber,
            String adminLevel) {
        super(id, firstName, lastName, email, password, phoneNumber);
        this.adminLevel = adminLevel;
    }

    
    @Override
    public String getRole() { return "Admin"; }

    @Override
    public String getInfo() {
        return String.format(
            "[Admin] ID: %-10s | Name: %-20s | Level: %s",
            super.getId(), super.getFullName(), adminLevel);
    }

    
    @Override
    public void displayDetails() {
        System.out.println("  ┌──────────────────────────────────────────────────┐");
        System.out.println("  │               ADMINISTRATOR DETAILS              │");
        System.out.println("  ├──────────────────────────────────────────────────┤");
        System.out.printf( "  │  ID          : %-34d%n", super.getId());
        System.out.printf( "  │  Full Name   : %-34s│%n", super.getFullName());
        System.out.printf( "  │  Email       : %-34s│%n", super.getEmail());
        System.out.printf( "  │  Phone       : %-34s│%n", super.getPhoneNumber());
        System.out.printf( "  │  Admin Level : %-34s│%n", adminLevel);
        System.out.println("  └──────────────────────────────────────────────────┘");
    }

   
    @Override
    public String toCsv() {
        return super.toCsv() + "," + adminLevel;
    }

    public static Admin fromCsv(String line) {
        String[] p = line.split(",", -1);
        return new Admin(
            Integer.parseInt(p[0].trim()), p[1].trim(), p[2].trim(),
            p[3].trim(), p[4].trim(), p[5].trim(),
            p[6].trim()
        );
    }

    public String getAdminLevel() { return adminLevel; }
    public void setAdminLevel(String v) { this.adminLevel = v; }
}
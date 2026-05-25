package hospital.models;
public class Patient extends Person implements Displayable, Persistable {

    private int  age;
    private String bloodType;
    private String address;

      public Patient(int id, String firstName, String lastName,
                   String email, String password, String phoneNumber,
                   int age, String bloodType, String address) {
        super(id, firstName, lastName, email, password, phoneNumber);
        if(age > 0) {
            this.age = age;
        } 
        this.bloodType = bloodType;
        this.address   = address;
    }

   
    @Override
    public String getRole() { return "Patient"; }

    @Override
    public String getInfo() {
       
        return String.format(
            "[Patient] ID: %-10s | Name: %-20s | Age: %3d | Blood: %-4s | Phone: %s",
            super.getId(), super.getFullName(), age, bloodType, super.getPhoneNumber());
    }

    
    @Override
    public void displayDetails() {
        System.out.println("  ┌──────────────────────────────────────────────────┐");
        System.out.println("  │                 PATIENT DETAILS                  │");
        System.out.println("  ├──────────────────────────────────────────────────┤");
        System.out.printf( "  │  ID         : %-35s│%n", super.getId());
        System.out.printf( "  │  Full Name  : %-35s│%n", super.getFullName());
        System.out.printf( "  │  Age        : %-35d│%n", age);
        System.out.printf( "  │  Blood Type : %-35s│%n", bloodType);
        System.out.printf( "  │  Phone      : %-35s│%n", super.getPhoneNumber());
        System.out.printf( "  │  Email      : %-35s│%n", super.getEmail());
        System.out.printf( "  │  Address    : %-35s│%n", address);
        System.out.println("  └──────────────────────────────────────────────────┘");
    }

   
    @Override
    public String toCsv() {
        return super.toCsv() + "," + age + "," + bloodType + "," + address;
    }

    
    public static Patient fromCsv(String line) {
        
        String[] p = line.split(",", -1);
        return new Patient(
            Integer.parseInt(p[0].trim()), p[1].trim(), p[2].trim(),
            p[3].trim(), p[4].trim(), p[5].trim(),
            Integer.parseInt(p[6].trim()),
            p[7].trim(), p[8].trim()
        );
    }

     public int    getAge()       { return age; }
    public String getBloodType() { return bloodType; }
    public String getAddress()   { return address; }

    public void setAge(int v)         { if(v > 0){this.age= v;}  }
    public void setBloodType(String v){ this.bloodType  = v; }
    public void setAddress(String v)  { this.address   = v; }
}
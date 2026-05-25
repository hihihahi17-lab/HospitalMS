package hospital.models;

public abstract class Person {

    private final int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;

    public Person(int id, String firstName, String lastName,
                  String email, String password, String phoneNumber) {
        this.id          = id;
        this.firstName   = firstName;
        this.lastName    = lastName;
        this.email       = email;
        this.password    = password;
        this.phoneNumber = phoneNumber;
    }

   
    public abstract String getRole();

     public abstract String getInfo();

    
    public boolean checkPassword(String input) {
        return this.password.equals(input);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

     public int getId()          { return id; }
    public String getFirstName()   { return firstName; }
    public String getLastName()    { return lastName; }
    public String getEmail()       { return email; }
    public String getPassword()    { return password; }
    public String getPhoneNumber() { return phoneNumber; }

    public void setFirstName(String v)   { this.firstName = v; }
    public void setLastName(String v)    { this.lastName  = v; }
    public void setEmail(String v)       { this.email     = v; }
    public void setPassword(String v)    { this.password  = v; }
    public void setPhoneNumber(String v) { this.phoneNumber = v; }

    
    public String toCsv() {
        return id + "," + firstName + "," + lastName + ","
             + email + "," + password + "," + phoneNumber;
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
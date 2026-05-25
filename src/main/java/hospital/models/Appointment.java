package hospital.models;
import java.time.LocalDate;
import java.time.LocalTime;
public class Appointment implements Displayable, Persistable {

  
    public enum Status {
        PENDING,    
        ACCEPTED,   
        REFUSED,    
        COMPLETED   
    }

    private int appointmentId;
    private int  patientId;
    private int  doctorId;
    private LocalDate date;    
    private LocalTime time;    
    private String reason;
    private Status status;

    public Appointment(int appointmentId, int patientId, int doctorId,
                       String date, String time, String reason) {
        this.appointmentId = appointmentId;
        this.patientId     = patientId;
        this.doctorId      = doctorId;
        this.date          = LocalDate.parse(date);
        this.time          = LocalTime.parse(time);
        this.reason        = reason;
        this.status        = Status.PENDING;
    }

     public Appointment(int appointmentId, int patientId, int doctorId,
                       String date, String time, String reason, Status status) {
        this(appointmentId, patientId, doctorId, date, time, reason);
        this.status = status;
    }

    
    @Override
    public void displayDetails() {
        System.out.println("  ┌──────────────────────────────────────────────────┐");
        System.out.println("  │              APPOINTMENT DETAILS                 │");
        System.out.println("  ├──────────────────────────────────────────────────┤");
        System.out.printf( "  │  Appt ID    : %-35d│%n", appointmentId);
        System.out.printf( "  │  Patient ID : %-35d│%n", patientId);
        System.out.printf( "  │  Doctor ID  : %-35d│%n", doctorId);
        System.out.printf( "  │  Date       : %-35s│%n", date);
        System.out.printf( "  │  Time       : %-35s│%n", time);
        System.out.printf( "  │  Reason     : %-35s│%n", reason);
        System.out.printf( "  │  Status     : %-35s│%n", status.name());
        System.out.println("  └──────────────────────────────────────────────────┘");
    }

    
    @Override
    public String toCsv() {
        String safeReason = reason.replace(",", ";");
        return appointmentId + "," + patientId + "," + doctorId + ","
             + date + "," + time + "," + safeReason + "," + status.name();
    }

    
    public static Appointment fromCsv(String line) {
        String[] p = line.split(",", -1);
        return new Appointment(
            Integer.parseInt(p[0].trim()), Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()),
            p[3].trim(), p[4].trim(), p[5].trim(),
            Status.valueOf(p[6].trim())
        );
    }

     public int getAppointmentId() { return appointmentId; }
    public int getPatientId()     { return patientId; }
    public int getDoctorId()      { return doctorId; }
    public LocalDate getDate()          { return date; }
    public LocalTime getTime()          { return time; }
    public String getReason()        { return reason; }
    public Status getStatus()        { return status; }

    public void setStatus(Status v) { this.status = v; }
    public void setDate(LocalDate v)   { this.date   = v; }
    public void setTime(LocalTime v)   { this.time   = v; }
    public void setReason(String v) { this.reason = v; }
}
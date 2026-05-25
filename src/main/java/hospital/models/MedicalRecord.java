package hospital.models;
import java.time.LocalDate;
public class MedicalRecord  implements Displayable, Persistable {

    private final int recordId;
    private final int patientId;
    private final int doctorId;
    private final int appointmentId;
    private String diagnosis;
    private int notes;
    private final LocalDate date; 

    public MedicalRecord(int recordId, int patientId, int doctorId,
                         int appointmentId, String diagnosis,
                         int notes, LocalDate date) {
        this.recordId      = recordId;
        this.patientId     = patientId;
        this.doctorId      = doctorId;
        this.appointmentId = appointmentId;
        this.diagnosis     = diagnosis;
        this.notes         = notes;
        this.date          = date;
    }

    @Override
    public void displayDetails() {
        System.out.println("  ┌──────────────────────────────────────────────────┐");
        System.out.println("  │               MEDICAL RECORD                     │");
        System.out.println("  ├──────────────────────────────────────────────────┤");
        System.out.printf( "  │  Record ID   : %-33d│%n", recordId);
        System.out.printf( "  │  Patient ID  : %-33d│%n", patientId);
        System.out.printf( "  │  Doctor ID   : %-33d│%n", doctorId);
        System.out.printf( "  │  Appt ID     : %-33d│%n", appointmentId);
        System.out.printf( "  │  Date        : %-33s│%n", date);
        System.out.printf( "  │  Diagnosis   : %-33s│%n", diagnosis);
        System.out.printf( "  │  Notes       : %-33s│%n", notes);
        System.out.println("  └──────────────────────────────────────────────────┘");
    }

    
    @Override
    public String toCsv() {
        String safeDiagnosis = diagnosis.replace(",", ";");
        String safeNotes     = String.valueOf(notes);
        return recordId + "," + patientId + "," + doctorId + ","
             + appointmentId + "," + safeDiagnosis + "," + safeNotes + "," + date;
    }

   
    public static MedicalRecord fromCsv(String line) {
        String[] p = line.split(",", -1);
        return new MedicalRecord(
            Integer.parseInt(p[0].trim()), Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()),
            Integer.parseInt(p[3].trim()), p[4].trim(), Integer.parseInt(p[5].trim()), LocalDate.parse(p[6].trim())
        );
    }

   public int getRecordId()      { return recordId; }
    public int getPatientId()     { return patientId; }
    public int getDoctorId()      { return doctorId; }
    public int getAppointmentId() { return appointmentId; }
    public String getDiagnosis()     { return diagnosis; }
    public int getNotes()         { return notes; }
    public LocalDate getDate()          { return date; }

    public void setDiagnosis(String v) { this.diagnosis = v; }
    public void setNotes(int v)     { this.notes     = v; }
}
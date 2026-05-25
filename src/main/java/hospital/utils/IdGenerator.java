

public class IdGenerator {

    public static String generatePatientId(int count) {
        return "PAT-" + String.format("%04d", count + 1);
    }

    public static String generateDoctorId(int count) {
        return "DOC-" + String.format("%04d", count + 1);
    }

    public static String generateAppointmentId(int count) {
        return "APT-" + String.format("%04d", count + 1);
    }

    public static String generateRecordId(int count) {
        return "REC-" + String.format("%04d", count + 1);
    }

    public static String generateAdminId(int count) {
        return "ADM-" + String.format("%04d", count + 1);
    }
}
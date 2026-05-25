package hospital.persistence;
import hospital.models.Appointment;
import java.util.ArrayList;
import java.util.List;


public class AppointmentRepository {

    private static final String FILE_PATH = "data/appointments.csv";

    public List<Appointment> loadAll() {
        List<Appointment> list = new ArrayList<>();
        for (String line : FileManager.readLines(FILE_PATH)) {
            try {
                list.add(Appointment.fromCsv(line));
            } catch (Exception e) {
                System.err.println("[AppointmentRepo] Skipping bad line: " + line);
            }
        }
        return list;
    }

    public void saveAll(List<Appointment> appointments) {
        List<String> lines = new ArrayList<>();
        for (Appointment a : appointments) lines.add(a.toCsv());
        FileManager.writeLines(FILE_PATH, lines);
    }
}
package hospital.persistence;
import hospital.models.Doctor;
import java.util.ArrayList;
import java.util.List;


public class DoctorRepository {

    private static final String FILE_PATH = "data/doctors.csv";

    public List<Doctor> loadAll() {
        List<Doctor> doctors = new ArrayList<>();
        for (String line : FileManager.readLines(FILE_PATH)) {
            try {
                doctors.add(Doctor.fromCsv(line));
            } catch (Exception e) {
                System.err.println("[DoctorRepo] Skipping bad line: " + line);
            }
        }
        return doctors;
    }

    public void saveAll(List<Doctor> doctors) {
        List<String> lines = new ArrayList<>();
        for (Doctor d : doctors) lines.add(d.toCsv());
        FileManager.writeLines(FILE_PATH, lines);
    }
}
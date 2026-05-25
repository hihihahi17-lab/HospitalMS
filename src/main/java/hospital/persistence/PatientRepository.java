package hospital.persistence;

import hospital.models.Patient;
import java.util.ArrayList;
import java.util.List;


public class PatientRepository {

    private static final String FILE_PATH = "data/patients.csv";

    
    public List<Patient> loadAll() {
        List<Patient> patients = new ArrayList<>();
        for (String line : FileManager.readLines(FILE_PATH)) {
            try {
                patients.add(Patient.fromCsv(line));
            } catch (Exception e) {
                System.err.println("[PatientRepo] Skipping bad line: " + line);
            }
        }
        return patients;
    }

   
    public void saveAll(List<Patient> patients) {
        List<String> lines = new ArrayList<>();
        for (Patient p : patients) lines.add(p.toCsv());
        FileManager.writeLines(FILE_PATH, lines);
    }
}
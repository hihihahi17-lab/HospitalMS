package hospital.persistence;
import hospital.models.MedicalRecord;
import java.util.ArrayList;
import java.util.List;


public class MedicalRecordRepository {

    private static final String FILE_PATH = "data/records.csv";

    public List<MedicalRecord> loadAll() {
        List<MedicalRecord> list = new ArrayList<>();
        for (String line : FileManager.readLines(FILE_PATH)) {
            try {
                list.add(MedicalRecord.fromCsv(line));
            } catch (Exception e) {
                System.err.println("[RecordRepo] Skipping bad line: " + line);
            }
        }
        return list;
    }

    public void saveAll(List<MedicalRecord> records) {
        List<String> lines = new ArrayList<>();
        for (MedicalRecord r : records) lines.add(r.toCsv());
        FileManager.writeLines(FILE_PATH, lines);
    }
}
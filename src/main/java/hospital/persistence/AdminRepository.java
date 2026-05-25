package hospital.persistence;

import hospital.models.Admin;
import java.util.ArrayList;
import java.util.List;


public class AdminRepository {

    private static final String FILE_PATH = "data/admins.csv";

    public List<Admin> loadAll() {
        List<Admin> admins = new ArrayList<>();
        List<String> lines = FileManager.readLines(FILE_PATH);

        if (lines.isEmpty()) {
            Admin defaultAdmin = new Admin(
                "ADM-0001", "System", "Admin",
                "admin@hospital.ma", "Admin@1234",
                "0600000000", "SUPER"
            );
            admins.add(defaultAdmin);
            saveAll(admins);
            return admins;
        }

        for (String line : lines) {
            try {
                admins.add(Admin.fromCsv(line));
            } catch (Exception e) {
                System.err.println("[AdminRepo] Skipping bad line: " + line);
            }
        }
        return admins;
    }

    public void saveAll(List<Admin> admins) {
        List<String> lines = new ArrayList<>();
        for (Admin a : admins) lines.add(a.toCsv());
        FileManager.writeLines(FILE_PATH, lines);
    }
}
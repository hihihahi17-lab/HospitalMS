package hospital.persistence;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileManager {

    
    public static List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) return lines; 

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("[FileManager] Cannot read " + filePath + " : " + e.getMessage());
        }
        return lines;
    }

   
    public static void writeLines(String filePath, List<String> lines) {
        ensureParentDirs(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("[FileManager] Cannot write " + filePath + " : " + e.getMessage());
        }
    }

    
    private static void ensureParentDirs(String filePath) {
        File file = new File(filePath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
    }
}
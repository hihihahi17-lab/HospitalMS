

import java.util.Scanner;


public class ConsoleUtils {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static String readLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("  [!] Input cannot be empty. Please try again.");
        }
    }

    
    public static String readOptionalLine(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine().trim();
    }

    
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Please enter a whole number (e.g. 42).");
            }
        }
    }

   
    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("  [!] Value cannot be negative.");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("  [!] Please enter a decimal number (e.g. 150.00).");
            }
        }
    }

    
    public static void printDivider() {
        System.out.println("  ──────────────────────────────────────────────────");
    }

    public static void printHeader(String title) {
        int width = 50;
        int padding = (width - title.length() - 2) / 2;
        String pad = " ".repeat(Math.max(0, padding));
        System.out.println();
        System.out.println("  ╔════════════════════════════════════════════════╗");
        System.out.printf( "  ║%s %s %s║%n", pad, title, pad);
        System.out.println("  ╚════════════════════════════════════════════════╝");
    }

    public static void pressEnterToContinue() {
        System.out.print("\n  [ Press ENTER to continue ] ");
        SCANNER.nextLine();
    }

    public static void printSuccess(String msg) {
        System.out.println("  [✓] " + msg);
    }

    public static void printError(String msg) {
        System.out.println("  [✗] " + msg);
    }

    public static void printInfo(String msg) {
        System.out.println("  [i] " + msg);
    }
}
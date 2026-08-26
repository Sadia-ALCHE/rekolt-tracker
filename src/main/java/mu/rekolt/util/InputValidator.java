package mu.rekolt.util;

import mu.rekolt.service.Price;
import java.util.Scanner;

// Every console prompt goes through here. Each method loops until the
// user enters something valid, so that bad input never crashes the program.
public class InputValidator {
    private final Scanner scanner;
    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readMenuOption(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Please choose a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("That is not a valid option. Please try again.");
            }
        }
    }

    public String readMemberId(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.matches("M-\\d{4}")) {
                return line;
            }
            System.out.println("Member identifier must look like M-0042. Please try again.");
        }
    }

    public String readNonBlankText(String prompt, String fieldLabel) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println(fieldLabel + " cannot be empty. Please try again.");
        }
    }

    public String readProduceCode(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (Price.isValidCode(line)) {
                return line.toUpperCase();
            }
            System.out.println("Produce code must be one of MZE, BNS, POT or TEA. Please try again.");
        }
    }

    public double readMass(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(line);
                if (value > 0 && value <= 5000) {
                    return value;
                }
                System.out.println("Mass must be above 0 and not more than 5000. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Mass must be a number. Please try again.");
            }
        }
    }

    public int readQualityScore(String prompt) {
        return readWholeNumberInRange(prompt, 0, 100, "Quality score must be a whole number from 0 to 100.");
    }

    public int readWeek(String prompt) {
        return readWholeNumberInRange(prompt, 1, 20, "Week must be a whole number from 1 to 20.");
    }

    // Shared by readQualityScore and readWeek, both just need a whole
    // number inside some range, so this one method backs both of them.
    private int readWholeNumberInRange(String prompt, int min, int max, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println(errorMessage + " Please try again.");
            } catch (NumberFormatException e) {
                System.out.println(errorMessage + " Please try again.");
            }
        }
    }
}
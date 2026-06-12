package InputHelper;

import java.util.Scanner;

public class Input {

    private static final Scanner sc = new Scanner(System.in);

    // Int

    public static int getInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a whole number: ");
            }
        }
    }

    public static int getPositiveInt() {
        while (true) {
            int value = getInt();
            if (value > 0) return value;
            System.out.print("Please enter a number greater than 0: ");
        }
    }

    public static int getIntInRange(int min, int max) {
        while (true) {
            int value = getInt();
            if (value >= min && value <= max) return value;
            System.out.print("Please choose a number between " + min + " and " + max + ": ");
        }
    }

    // Long

    public static long getLong() {
        while (true) {
            try {
                return Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    // Double

    public static double getDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid price or decimal number: ");
            }
        }
    }

    public static double getPositiveDouble() {
        while (true) {
            double value = getDouble();
            if (value > 0) return value;
            System.out.print("Please enter a number greater than 0: ");
        }
    }

    // String

    public static String getString() {
        return sc.nextLine().trim();
    }

    public static String getNonEmptyString() {
        while (true) {
            String value = getString();
            if (!value.isEmpty()) return value;
            System.out.print("This field cannot be empty. Try again: ");
        }
    }

    // Boolean (yes/no)

    public static boolean getConfirmation() {
        while (true) {
            String value = getString().toLowerCase();
            if (value.equals("y") || value.equals("yes")) return true;
            if (value.equals("n") || value.equals("no"))  return false;
            System.out.print("Please enter y or n: ");
        }
    }

    // Utility

    public static void pressEnterToContinue() {
        System.out.println();
        System.out.print("Press Enter to continue...");
        sc.nextLine();
    }

    public static void close() {
        sc.close();
    }
}

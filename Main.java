package converter;


import java.util.Scanner;

public class Main {

    private static final Scanner sc = CommonScanner.getInstance();
    private static final NumberConverterInterface nci = new NumberConverterInterface();

    public static void main(String[] args) {

        while (true) {
            System.out.print("Enter two numbers in format: " +
                    "{source base} {target base} (To quit type /exit) ");
            String input = sc.nextLine().trim().toUpperCase();
            if (input.equals("/EXIT")) {
                return;
            }
            if (!input.matches("\\d{1,2}\\s+\\d{1,2}")) {
                System.out.println("You need to enter two numbers separated by space!\n");
                continue;
            }
            String[] bases = input.split("\\s+");
            int sourceBase = Integer.parseInt(bases[0]);
            int targetBase = Integer.parseInt(bases[1]);
            if (!areBasesValid(sourceBase, targetBase)) {
                System.out.println("Invalid base number! It must be a number in range 2-36!\n");
                continue;
            }
            nci.convertNumberInterface(sourceBase, targetBase);
        }
    }

    private static boolean areBasesValid(int sourceBase, int targetBase) {
        return isBaseValid(sourceBase) && isBaseValid(targetBase);
    }

    private static boolean isBaseValid(int base) {
        return base >= 2 && base <= 36;
    }
}

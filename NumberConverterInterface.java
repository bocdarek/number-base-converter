package converter;

import java.util.Scanner;

public class NumberConverterInterface {

    private static final Scanner sc = CommonScanner.getInstance();
    private static final NumberConverter nc = new NumberConverter();

    public void convertNumberInterface(int sourceBase, int targetBase) {
        String number;
        while (true) {
            System.out.printf("Enter number in base %d to convert to base %d (To go back type /back) ",
                    sourceBase, targetBase);
            number = sc.nextLine().trim().toUpperCase();
            if (number.equals("/BACK")) {
                System.out.println();
                return;
            }
            if (isFractionNumberValid(number, sourceBase)) {
                String result = nc.convertNumber(number, sourceBase, targetBase);
                System.out.println("Conversion result: " + result + "\n");
            } else {
                invalidNumberMessage(sourceBase);
            }
        }
    }

    private boolean isFractionNumberValid(String number, int sourceBase) {
        if (!number.matches("[0-9A-Z]+(\\.[0-9A-Z]+)?")) {
            return false;
        }
        number = number.replace(".", "");
        return isNumberValid(number, sourceBase);
    }

    private boolean isNumberValid(String number, int sourceBase) {
        String availableSymbols = nc.getAvailableSymbols().substring(0, sourceBase);
        for (int i = 0; i < number.length(); i++) {
            String symbol = number.charAt(i) + "";
            if (!availableSymbols.contains(symbol)) {
                return false;
            }
        }
        return true;
    }

    private void invalidNumberMessage(int sourceBase) {
        StringBuilder sb = new StringBuilder("Number can contain only digits in range ");
        if (sourceBase < 11) {
            sb.append("0-").append(sourceBase - 1);
        } else {
            sb.append("0-9 and A-").append(nc.getAvailableSymbols().charAt(sourceBase - 1));
        }
        sb.append(", use single '.' to separate fractional part if present.\n");
        System.out.println(sb);
    }
}

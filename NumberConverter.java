package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;

public class NumberConverter {

    private final String availableSymbols = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String getAvailableSymbols() {
        return availableSymbols;
    }

    public String convertNumber(String number, int sourceBase, int targetBase) {
        if (!number.contains(".")) {
            return convertIntegerPart(number, sourceBase, targetBase);
        }
        String[] splitNumber = number.split("\\.");
        String integerPart = splitNumber[0];
        String fractionalPart = splitNumber[1];
        return convertIntegerPart(integerPart, sourceBase, targetBase).toLowerCase() + "."
                + convertFractionalPart(fractionalPart, sourceBase, targetBase).toLowerCase();
    }

    private String convertIntegerPart(String number, int sourceBase, int targetBase) {
        BigInteger decimalNumber = convertIntegerPartToDecimal(number, sourceBase);
        return convertIntegerPartFromDecimal(decimalNumber, targetBase);
    }

    private BigInteger convertIntegerPartToDecimal(String number, int base) {
        String[] numArray = number.split("");
        int numLength = numArray.length;
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < numLength; i++) {
            BigInteger nextPower = BigInteger.valueOf(base).pow(i);
            int nextDigit = availableSymbols.indexOf(numArray[numLength - 1 - i]);
            result = BigInteger.valueOf(nextDigit)
                    .multiply(nextPower)
                    .add(result);
        }
        return result;
    }

    private String convertIntegerPartFromDecimal(BigInteger number, int base) {
        if (number.equals(BigInteger.ZERO)) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        while (!number.equals(BigInteger.ZERO)) {
            int index = number.mod(BigInteger.valueOf(base)).intValue();
            sb.append(availableSymbols.charAt(index));
            number = number.divide(BigInteger.valueOf(base));
        }
        sb.reverse();
        return sb.toString();
    }


    private String convertFractionalPart(String number, int sourceBase, int targetBase) {
        BigDecimal decimalNumber = convertFractionalPartToDecimal(number, sourceBase);
        return convertFractionalPartFromDecimal(decimalNumber, targetBase);
    }

    private BigDecimal convertFractionalPartToDecimal(String number, int base) {
        String[] numArray = number.split("");
        int numLength = numArray.length;
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < numLength; i++) {
            BigDecimal nextPower = BigDecimal.ONE
                    .divide(BigDecimal.valueOf(base).pow(i + 1), 50, RoundingMode.HALF_UP);
            int nextDigit = availableSymbols.indexOf(numArray[i]);
            result = BigDecimal.valueOf(nextDigit)
                    .multiply(nextPower)
                    .add(result);
        }
        return result;
    }

    private String convertFractionalPartFromDecimal(BigDecimal fraction, int base) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 5) {
            fraction = fraction.multiply(BigDecimal.valueOf(base));
            int index = fraction.divide(BigDecimal.ONE, 0, RoundingMode.FLOOR).intValue();
            sb.append(availableSymbols.charAt(index));
            fraction = fraction.remainder(BigDecimal.ONE);
        }
        return sb.toString();
    }
}

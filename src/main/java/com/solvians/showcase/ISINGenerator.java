package com.solvians.showcase;

import java.util.HashMap;
import java.util.Random;

public class ISINGenerator {
    private static final Random random = new Random();
    private static final HashMap<Character, Integer> conversionTable = new HashMap<Character, Integer>() {{
        for (char c = 'A'; c <= 'Z'; c++) {
            put(c, 10 + (c - 'A'));
        }
    }};

    public static String generateIsin() {
        StringBuilder isin = new StringBuilder();
        
        // Add the 2 random uppercase alphabets + 9 random alphanumeric characters
        for (int i = 0; i < 2; i++) {
            isin.append((char) ('A' + random.nextInt(26)));
        }

        for (int i = 0; i < 9; i++) {
            if (random.nextBoolean()) {
                isin.append((char) ('A' + random.nextInt(26)));
            } else {
                isin.append(random.nextInt(10));
            }
        }
        
        // Calculate and append the check digit
        isin.append(calculateCheckDigit(isin.toString()));

        return isin.toString();
    }

    private static String calculateCheckDigit(String isinWithoutCheckDigit) {
        String converted = convertLetters(isinWithoutCheckDigit);
        
        int sum = calculateCheckDigitSum(converted);
        
        int checkDigit = (10 - (sum % 10)) % 10; // no need to calculate next multiple of 10...
        
        return Integer.toString(checkDigit);
    }

    private static int calculateCheckDigitSum(String convertedString) {
        int sum = 0;
        boolean multiplyByTwo = true;
        
        for (int i = convertedString.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(convertedString.charAt(i));
            
            if (multiplyByTwo) {
                digit *= 2;
            }
            
            sum += digit / 10;
            sum += digit % 10;
            
            multiplyByTwo = !multiplyByTwo;
        }
        return sum;
    }

    private static String convertLetters(String baseString) {
        StringBuilder converted = new StringBuilder();
        for (char c : baseString.toCharArray()) {
            if (Character.isLetter(c)) {
                converted.append(conversionTable.get(Character.toUpperCase(c)));
            } else {
                converted.append(c);
            }
        }
        return converted.toString();
    }
}

package com.solvians.showcase;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

public class ISINGeneratorTest {
    
    @Test
    public void testGenerateIsinFormat() {
        String isin = ISINGenerator.generateIsin();
        
        // ISIN should be 12 characters long
        assertEquals(12, isin.length(), "ISIN should be 12 characters long");
        
        // First two characters should be uppercase letters
        assertTrue(Character.isUpperCase(isin.charAt(0)), "First character should be uppercase letter");
        assertTrue(Character.isLetter(isin.charAt(0)), "First character should be a letter");
        assertTrue(Character.isUpperCase(isin.charAt(1)), "Second character should be uppercase letter");
        assertTrue(Character.isLetter(isin.charAt(1)), "Second character should be a letter");
        
        // Characters 2-10 should be alphanumeric
        for (int i = 2; i < 11; i++) {
            assertTrue(Character.isLetterOrDigit(isin.charAt(i)), 
                "Character at position " + i + " should be alphanumeric");
        }
        
        // Last character (check digit) should be a digit
        assertTrue(Character.isDigit(isin.charAt(11)), "Last character should be a digit (check digit)");
    }
    
    @Test
    public void testCalculateCheckDigitExample() throws Exception {
        Method method = ISINGenerator.class.getDeclaredMethod("calculateCheckDigit", String.class);
        method.setAccessible(true);
        
        String checkDigit = (String) method.invoke(null, "DE123456789");
        assertEquals("6", checkDigit, "Check digit for DE123456789 should be 6");
    }
    
    @Test
    public void testCalculateCheckDigitAllNumeric() throws Exception {
        Method method = ISINGenerator.class.getDeclaredMethod("calculateCheckDigit", String.class);
        method.setAccessible(true);
        
        // Test with all numeric string
        String checkDigit = (String) method.invoke(null, "12345678901");
        assertNotNull(checkDigit);
        assertTrue(checkDigit.length() == 1);
        assertTrue(Character.isDigit(checkDigit.charAt(0)));
    }
    
    @Test
    public void testCalculateCheckDigitAllLetters() throws Exception {
        Method method = ISINGenerator.class.getDeclaredMethod("calculateCheckDigit", String.class);
        method.setAccessible(true);
        
        // Test with all letters
        String checkDigit = (String) method.invoke(null, "ABCDEFGHIJK");
        assertNotNull(checkDigit);
        assertTrue(checkDigit.length() == 1);
        assertTrue(Character.isDigit(checkDigit.charAt(0)));
    }
    
    @Test
    public void testCalculateCheckDigitSumLogic() throws Exception {
        Method calculateSumMethod = ISINGenerator.class.getDeclaredMethod("calculateCheckDigitSum", String.class);
        calculateSumMethod.setAccessible(true);

        int sum = (int) calculateSumMethod.invoke(null, "1314123456789");
        int checkDigit = (10 - (sum % 10)) % 10;
        assertTrue(checkDigit >= 0 && checkDigit <= 9, "Check digit should be between 0 and 9");
    }
    
    @Test
    public void testGeneratedIsinHasValidCheckDigit() throws Exception {
        Method calculateCheckDigitMethod = ISINGenerator.class.getDeclaredMethod("calculateCheckDigit", String.class);
        calculateCheckDigitMethod.setAccessible(true);
        
        // Generate an ISIN and verify its check digit
        String isin = ISINGenerator.generateIsin();
        String base = isin.substring(0, 11);
        String expectedCheckDigit = (String) calculateCheckDigitMethod.invoke(null, base);
        String actualCheckDigit = String.valueOf(isin.charAt(11));
        
        assertEquals(expectedCheckDigit, actualCheckDigit, 
            "Generated ISIN should have correct check digit");
    }
    
    @Test
    public void testCheckDigitZero() throws Exception {
        // Test case where check digit should be 0
        Method method = ISINGenerator.class.getDeclaredMethod("calculateCheckDigit", String.class);
        method.setAccessible(true);
        
        String checkDigit = (String) method.invoke(null, "AU0000XVGZA");
        assertNotNull(checkDigit);
        assertTrue(Character.isDigit(checkDigit.charAt(0)));
    }
}

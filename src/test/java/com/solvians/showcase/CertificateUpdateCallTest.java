package com.solvians.showcase;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CertificateUpdateCallTest {

    @Test
    void callShouldReturnValidCertificateUpdate() throws Exception {
        CertificateUpdateCall updateCall = new CertificateUpdateCall();
        CertificateUpdate update = updateCall.call();

        assertNotNull(update, "CertificateUpdate should not be null");
    }

    @Test
    void certificateUpdateShouldHaveValidTimestamp() throws Exception {
        long beforeCall = System.currentTimeMillis();
        CertificateUpdateCall updateCall = new CertificateUpdateCall();
        CertificateUpdate update = updateCall.call();
        long afterCall = System.currentTimeMillis();

        String csv = update.toString();
        String[] fields = csv.split(",");
        long timestamp = Long.parseLong(fields[0]);

        assertTrue(timestamp >= beforeCall && timestamp <= afterCall,
            "Timestamp should be between the call start and end time");
    }

    @Test
    void certificateUpdateShouldHaveValidISIN() throws Exception {
        CertificateUpdateCall updateCall = new CertificateUpdateCall();
        CertificateUpdate update = updateCall.call();

        String csv = update.toString();
        String[] fields = csv.split(",");
        String isin = fields[1];

        assertNotNull(isin, "ISIN should not be null");
        assertEquals(12, isin.length(), "ISIN should be exactly 12 characters long");
        assertTrue(isin.matches("[A-Z0-9]{12}"), "ISIN should contain only uppercase letters and digits");
    }

    @Test
    void bidAndAskPricesShouldBeInValidRange() throws Exception {
        CertificateUpdateCall updateCall = new CertificateUpdateCall();
        CertificateUpdate update = updateCall.call();

        String csv = update.toString();
        String[] fields = csv.split(",");
        double bidPrice = Double.parseDouble(fields[2]);
        double askPrice = Double.parseDouble(fields[4]);

        assertTrue(bidPrice >= 100.00 && bidPrice <= 200.00,
            "Bid price should be between 100.00 and 200.00");
        assertTrue(askPrice >= 100.00 && askPrice <= 200.00,
            "Ask price should be between 100.00 and 200.00");
    }

    @Test
    void pricesShouldBeRoundedToTwoDecimalPlaces() throws Exception {
        CertificateUpdateCall updateCall = new CertificateUpdateCall();
        CertificateUpdate update = updateCall.call();

        String csv = update.toString();
        String[] fields = csv.split(",");

        assertTrue(fields[2].matches("\\d+\\.\\d{2}"),
            "Bid price should have exactly two decimal places");
        assertTrue(fields[4].matches("\\d+\\.\\d{2}"),
            "Ask price should have exactly two decimal places");
    }

    @Test
    void bidAndAskSizesShouldBeInValidRange() throws Exception {
        CertificateUpdateCall updateCall = new CertificateUpdateCall();
        CertificateUpdate update = updateCall.call();

        String csv = update.toString();
        String[] fields = csv.split(",");
        int bidSize = Integer.parseInt(fields[3]);
        int askSize = Integer.parseInt(fields[5]);

        assertTrue(bidSize >= 1000 && bidSize <= 5000,
            "Bid size should be between 1000 and 5000");
        assertTrue(askSize >= 1000 && askSize <= 5000,
            "Ask size should be between 1000 and 5000");
    }
}

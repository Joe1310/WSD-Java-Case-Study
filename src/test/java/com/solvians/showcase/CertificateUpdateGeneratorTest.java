package com.solvians.showcase;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CertificateUpdateGeneratorTest {

    @Test
    public void generateCorrectNumberOfQuotes() {
        CertificateUpdateGenerator certificateUpdateGenerator = new CertificateUpdateGenerator(10, 100);
        Stream<CertificateUpdate> quotes = certificateUpdateGenerator.generateQuotes();
        assertNotNull(quotes);
        assertEquals(10 * 100, quotes.count());
    }

    @Test
    void updatesShouldBeValidObjects() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(2, 10);
        List<CertificateUpdate> updates = generator.generateQuotes().collect(Collectors.toList());

        for (CertificateUpdate update : updates) {
            assertNotNull(update, "Certificate Updates should not be null");

            String csv = update.toString();
            assertNotNull(csv, "The CSV should not be null");

            String[] fields = csv.split(",");
            assertEquals(6, fields.length, "There should be exactly 6 fields");
        }
    }

    @Test
    void ensureSingleThreadSingleQuoteGeneration() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(1, 1);
        List<CertificateUpdate> updates = generator.generateQuotes().collect(Collectors.toList());

        assertEquals(1, updates.size(), "There should be exactly one update generated");

        CertificateUpdate update = updates.get(0);
        assertNotNull(update, "The generated update should not be null");

        String csv = update.toString();
        assertNotNull(csv, "The CSV representation should not be null");

        String[] fields = csv.split(",");
        assertEquals(6, fields.length, "The CSV should split into exactly 6 fields");

        assertTrue(fields[0].matches("\\d+"), "Timestamp should be a valid long integer");
        assertTrue(fields[1].matches("[A-Z0-9]{12}"), "ISIN should be a valid 12-character alphanumeric string");
        assertTrue(fields[2].matches("\\d+\\.\\d{2}"), "Bid Price should be a valid decimal with two decimal places");
        assertTrue(fields[3].matches("\\d+"), "Bid Size should be a valid integer");
        assertTrue(fields[4].matches("\\d+\\.\\d{2}"), "Ask Price should be a valid decimal with two decimal places");
        assertTrue(fields[5].matches("\\d+"), "Ask Size should be a valid integer");
    }

    @Test
    void zeroQuotesGeneration() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(5, 0);
        List<CertificateUpdate> updates = generator.generateQuotes().collect(Collectors.toList());

        assertNotNull(updates, "Updates list should not be null even if quotes = 0");
        assertEquals(0, updates.size(), "There should be no updates generated when quotes per thread is zero");
    }

    @Test
    void zeroThreadsGeneration() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(0, 10);
        List<CertificateUpdate> updates = generator.generateQuotes().collect(Collectors.toList());

        assertNotNull(updates, "Updates list should not be null even if threads = 0");
        assertEquals(0, updates.size(), "There should be no updates generated when number of threads is zero");
    }
}
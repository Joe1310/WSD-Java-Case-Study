package com.solvians.showcase;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Callable;

public class CertificateUpdateCall implements Callable<CertificateUpdate> {
    
    @Override
    public CertificateUpdate call() {
        long timestamp = System.currentTimeMillis();
        String isin = ISINGenerator.generateIsin();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        double bidPrice = roundDecimalValue(random.nextDouble(100.00, 200.01));
        double askPrice = roundDecimalValue(random.nextDouble(100.00, 200.01));

        int bidSize = random.nextInt(1000, 5001);
        int askSize = random.nextInt(1000, 5001);

        return new CertificateUpdate(timestamp, isin, bidPrice, bidSize, askPrice, askSize);
    }

    private double roundDecimalValue(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

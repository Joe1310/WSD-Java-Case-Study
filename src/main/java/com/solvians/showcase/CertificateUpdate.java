package com.solvians.showcase;

public class CertificateUpdate {
    private long timestamp;
    private String isin;
    private double bidPrice;
    private int bidSize;
    private double askPrice;
    private int askSize;

    public CertificateUpdate(long timestamp, String isin, double bidPrice, int bidSize, double askPrice, int askSize) {
        this.timestamp = timestamp;
        this.isin = isin;
        this.bidPrice = bidPrice;
        this.bidSize = bidSize;
        this.askPrice = askPrice;
        this.askSize = askSize;
    }

    public String toString() {
        return String.format("%d,%s,%.2f,%d,%.2f,%d",
            timestamp, isin, bidPrice, bidSize, askPrice, askSize);
    }
}

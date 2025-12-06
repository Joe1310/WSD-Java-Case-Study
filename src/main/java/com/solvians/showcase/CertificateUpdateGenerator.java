package com.solvians.showcase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class CertificateUpdateGenerator {
    private final int threads;
    private final int quotes;

    public CertificateUpdateGenerator(int threads, int quotes) {
        this.threads = threads;
        this.quotes = quotes;
    }

    public Stream<CertificateUpdate> generateQuotes() {
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        List<Future<CertificateUpdate>> updateList = new ArrayList<Future<CertificateUpdate>>();
        for (int i = 0; i < threads * quotes; i++) {
            updateList.add(executor.submit(new CertificateUpdateCall()));
        }
        
        executor.shutdown();

        return updateList.stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException("Error generating certificate update", e);
            }
        });
    }
}

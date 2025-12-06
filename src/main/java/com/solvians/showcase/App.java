package com.solvians.showcase;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        if (args.length >= 2) {
            int threads = Integer.parseInt(args[0]);
            int quotes = Integer.parseInt(args[1]);

            if (threads <= 0 || quotes <= 0) {
                throw new IllegalArgumentException("Number of threads and quotes must be positive integers.");
            }

            CertificateUpdateGenerator certificateUpdateGenerator = new CertificateUpdateGenerator(threads, quotes);
            certificateUpdateGenerator.generateQuotes().forEachOrdered(
                certificateUpdate -> System.out.println(certificateUpdate.toString())
            );
            System.out.println("Generated " + quotes + " quotes using " + threads + " threads.");
        }
        throw new RuntimeException("Expected number of threads and number of quotes. But got: " + java.util.Arrays.toString(args));
    }
}

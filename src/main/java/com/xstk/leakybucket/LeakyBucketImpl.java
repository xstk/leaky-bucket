package com.xstk.leakybucket;

public class LeakyBucketImpl implements LeakyBucket {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private final TimeProvider timeProvider;
    private final double bucketCapacity;
    /**
     * Amount of requests leaked per 1000ms
     */
    private final double leakRate;
    private long lastLeakCheck;
    private double currentCapacity;


    public LeakyBucketImpl(int capacity, double leakRate, TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
        this.bucketCapacity = capacity;
        this.leakRate = leakRate;
        this.lastLeakCheck = timeProvider.getCurrentTime();
    }

    public synchronized boolean shouldAcceptRequest() {
        checkLeakage();
        if (currentCapacity + 1 > bucketCapacity) {
            System.out.println("Bucket request was submitted with result: " + ANSI_RED + false + ANSI_RESET);
            return false;
        } else {
            currentCapacity++;
            System.out.println("Bucket request was submitted with result: " + ANSI_GREEN + true + ANSI_RESET);
            return true;
        }
    }

    private void checkLeakage() {
        long currentTime = timeProvider.getCurrentTime();
        currentCapacity = currentCapacity - (double) (currentTime - this.lastLeakCheck) / 1000 * leakRate;
        this.lastLeakCheck = currentTime;
    }
}

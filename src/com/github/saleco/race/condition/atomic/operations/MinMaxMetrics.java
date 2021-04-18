package com.github.saleco.race.condition.atomic.operations;

public class MinMaxMetrics {

    // Add all necessary member variables
    private volatile long min = Long.MAX_VALUE;
    private volatile long max = Long.MIN_VALUE;


    /**
     * Initializes all member variables
     */
    public MinMaxMetrics() {
        // Add code here
    }

    /**
     * Adds a new sample to our metrics.
     */
    public void addSample(long newSample) {

        synchronized (this) {
            this.min = Math.min(newSample, this.min);
            this.max = Math.max(newSample, this.max);
        }

    }

    /**
     * Returns the smallest sample we've seen so far.
     */
    public long getMin() {
        // Add code here
        return this.min;
    }

    /**
     * Returns the biggest sample we've seen so far.
     */
    public long getMax() {
        return this.max;
    }

}

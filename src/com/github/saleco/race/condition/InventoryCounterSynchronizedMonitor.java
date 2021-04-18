package com.github.saleco.race.condition;

public class InventoryCounterSynchronizedMonitor {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();

        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        /*
        Running one thread at a time, its not an issue
        incrementingThread.start();
        incrementingThread.join();

        decrementingThread.start();
        decrementingThread.join();
         */

        /*
            Running concurrently will return wrong values because InventoryCount
            is using items++ and items-- operation and when running concurrently
            it results in wrong values - non atomic operations
         */
        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("We currently have " + inventoryCounter.getItems() + " items.");
    }

    public static class IncrementingThread extends Thread {

        private InventoryCounter inventoryCounter;

        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }
    }

    public static class DecrementingThread extends Thread {

        private InventoryCounter inventoryCounter;

        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    private static class InventoryCounter {
        private int items = 0;

        public synchronized void increment() {
            //not atomic operation - when running concurrently threads we got wrong values
            items++;
        }

        public synchronized void decrement() {
            //not atomic operation - when running concurrently threads we got wrong values
            items--;
        }

        public synchronized int getItems() {
            return items;
        }
    }
}

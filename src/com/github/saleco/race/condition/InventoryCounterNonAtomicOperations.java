package com.github.saleco.race.condition;

public class InventoryCounterNonAtomicOperations {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();

        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        /*
        Running one thread at a time, its not an issue
        incrementingThread.start();

        Wait for the thread finish
        incrementingThread.join();

        Starts new Thread
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

        public void increment() {
            //not atomic operation - when running concurrently threads got wrong values
            /*
                1. Get current value of items (currentValue = 0)
                2. Increment current value by 1 (currentValue + 1 = 1)
                3. items <---- newValue=1 (items = 1)
             */
            items++;
        }

        public void decrement() {
            //not atomic operation - when running concurrently threads got wrong values
            items--;
        }

        public int getItems() {
            return items;
        }
    }
}

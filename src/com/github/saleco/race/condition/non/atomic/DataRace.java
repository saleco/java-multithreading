package com.github.saleco.race.condition.non.atomic;

public class DataRace {

    public static void main(String[] args) {
         SharedClass sharedClass = new SharedClass();

         Thread thread1 = new Thread(() -> {
             for (int i = 0; i < Integer.MAX_VALUE; i++) {
                 sharedClass.increment();
             }
         });

         Thread thread2 = new Thread(() -> {
             for (int i = 0; i < Integer.MAX_VALUE; i++) {
                 sharedClass.checkForDataRace();
             }
         });

         thread1.start();
         thread2.start();
    }

    public static class SharedClass {

        private volatile int x = 0;
        private volatile int y = 0;

        //incrementing x & y are not order garanteed by the OS
        //means checkForDataRace might occur that Y is greater then X
        //Possible solutions:
        // Synchronization of methods which modofiy shared variables - no concurrency between methods call - low performance
        //Declaration of shared variables with volatile keyword - will reduce the overhead of locking and we'll guarantee order
        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace() {
            if(y > x) {
                System.out.println("y > x - Data Race is detected");
            }
        }
    }
}

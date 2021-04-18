package com.github.saleco.joins;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadJoin {

    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(10000099999l, 3435L, 35435L, 2324L, 3656L, 23L, 2435L, 5566L);

        //we want to calculate the !0l, !3435L, !35435L, !2324L, !3656L, !23L, !2435L, !5566L
        //factorial is high consuming cpu operation and using multithread we can create
        //different threads for each one of the numbers in parallel

        List<FactorialThread> threads = new ArrayList<>(inputNumbers.size());
        inputNumbers.forEach(n -> threads.add(new FactorialThread(n)));

        threads.forEach(thread -> {
            //will allow the application to terminate with still running thread
            thread.setDaemon(true);
            thread.start();
        });

        //it will garantee all the threads are finished before continuing the program
        //always setup a max time to wait
        for (Thread thread: threads) {
            thread.join(2000);
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progrss");
            }
        }
    }

    public static class FactorialThread extends Thread {

        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;

            for(long i = n; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}

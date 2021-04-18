package com.github.saleco.example;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
           //Code that will run in a new thread
            System.out.println("We are now in Thread '" + Thread.currentThread().getName() + "' with priority " + Thread.currentThread().getPriority());
        });

        System.out.println("We are in thread: '" + Thread.currentThread().getName() +  "' before starting a new thread");
        thread.setName("my-first-thread");
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        System.out.println("We are in thread: '" + Thread.currentThread().getName() +  "' after starting a new thread");

        Thread.sleep(10000);
    }
}

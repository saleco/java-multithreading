package com.github.saleco.example;

public class MainExceptionHandling {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            throw new RuntimeException("Booooooooooom");
        });

        thread.setName("Boom Thread");

        thread.setUncaughtExceptionHandler((t, throwable) -> {
            System.out.println("A critical error happened in thread '" + t.getName()
             + "' the error is " + throwable.getMessage());
        });

        thread.start();
    }
}

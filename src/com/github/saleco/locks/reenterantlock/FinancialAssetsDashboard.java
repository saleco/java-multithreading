package com.github.saleco.locks.reenterantlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FinancialAssetsDashboard {

    public static void main(String[] args) {
        PricesContainer pricesContainer = new PricesContainer();
        PriceUpdater priceUpdater = new PriceUpdater(pricesContainer);
        PricesDashboard pricesDashboard = new PricesDashboard(pricesContainer);
        priceUpdater.start();
        pricesDashboard.start();
    }

    public static class PricesContainer {

        private Lock lockObject = new ReentrantLock();

        private double bitcoinPrice;
        private double etherPrice;
        private double litecoinPrice;
        private double bitcoinCashPrice;
        private double ripplePrice;

        public Lock getLockObject() {
            return lockObject;
        }

        public void setLockObject(Lock lockObject) {
            this.lockObject = lockObject;
        }

        public double getBitcoinPrice() {
            return bitcoinPrice;
        }

        public void setBitcoinPrice(double bitcoinPrice) {
            this.bitcoinPrice = bitcoinPrice;
        }

        public double getEtherPrice() {
            return etherPrice;
        }

        public void setEtherPrice(double etherPrice) {
            this.etherPrice = etherPrice;
        }

        public double getLitecoinPrice() {
            return litecoinPrice;
        }

        public void setLitecoinPrice(double litecoinPrice) {
            this.litecoinPrice = litecoinPrice;
        }

        public double getBitcoinCashPrice() {
            return bitcoinCashPrice;
        }

        public void setBitcoinCashPrice(double bitcoinCashPrice) {
            this.bitcoinCashPrice = bitcoinCashPrice;
        }

        public double getRipplePrice() {
            return ripplePrice;
        }

        public void setRipplePrice(double ripplePrice) {
            this.ripplePrice = ripplePrice;
        }

        @Override
        public String toString() {
            return "PricesContainer{" +
              "lockObject=" + lockObject +
              ", bitcoinPrice=" + bitcoinPrice +
              ", etherPrice=" + etherPrice +
              ", litecoinPrice=" + litecoinPrice +
              ", bitcoinCashPrice=" + bitcoinCashPrice +
              ", ripplePrice=" + ripplePrice +
              '}';
        }
    }

    public static class PricesDashboard extends Thread {
        private PricesContainer pricesContainer;

        public PricesDashboard(PricesContainer pricesContainer) {
            this.pricesContainer = pricesContainer;
        }

        @Override
        public void run() {
            while(true) {
                if(pricesContainer.getLockObject().tryLock()) {
                    try {
                        System.out.println("Prices: " + pricesContainer.toString());
                    } finally {
                        pricesContainer.getLockObject().unlock();
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {}
                }
            }
        }
    }

    public static class PriceUpdater extends Thread {
        private PricesContainer pricesContainer;
        private Random random = new Random();

        public PriceUpdater(PricesContainer pricesContainer) {
            this.pricesContainer = pricesContainer;
        }

        @Override
        public void run() {
            while(true) {
                pricesContainer.getLockObject().lock();

                try {
                    pricesContainer.setBitcoinPrice(random.nextInt(2000));
                    pricesContainer.setEtherPrice(random.nextInt(2000));
                    pricesContainer.setLitecoinPrice(random.nextInt(2000));
                    pricesContainer.setBitcoinCashPrice(random.nextInt(2000));
                    pricesContainer.setRipplePrice(random.nextInt(2000));

                } finally {
                    pricesContainer.getLockObject().unlock();
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {}

            }
        }
    }
}

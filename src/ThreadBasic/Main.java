package ThreadBasic;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        Thread thread = new Thread(() -> {
//                throw new RuntimeException("something happened with the thread");
//        });
//
//        thread.setName("Worker Thread");
//
//        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//                System.out.println("A critical exception happened in thread " + t.getName() + " the error is " + e.getMessage());
//            }
//        });
////        System.out.println("We are in the Thread " + Thread.currentThread().getName() + " before starting the thread");
//        thread.start();
////        System.out.println("We are in the Thread " + Thread.currentThread().getName() + " after starting the thread");

        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        incrementingThread.start();

        decrementingThread.start();


        incrementingThread.join();
        decrementingThread.join();

        System.out.println(inventoryCounter.getX());
    }

    private static class InventoryCounter {
        private AtomicInteger x = new AtomicInteger(0);

        public void decrementX() {
            x.getAndDecrement();
        }

        public void incrementX() {
            x.getAndIncrement();
        }

        public int getX() {
            return x.get();
        }
    }

    public static class DecrementingThread extends Thread {
        private InventoryCounter inventoryCounter;
        public DecrementingThread(InventoryCounter counter) {
            this.inventoryCounter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.decrementX();
            }
        }
    }

    public static class IncrementingThread extends Thread {

        private InventoryCounter inventoryCounter;

        public IncrementingThread(InventoryCounter counter) {
            this.inventoryCounter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.incrementX();
            }
        }
    }
}

package virtualThreads;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static int NUMBER_OF_VIRTUAL_THREADS = 2;

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> System.out.println("Inside Thread: " + Thread.currentThread());

//        3. Executors.newVirtualThreadPerTaskExecutor();
//
//        4. Executors.newThreadPerTaskExecutor(ThreadFactory);

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_VIRTUAL_THREADS; i++) {
            Thread virtualThread = Thread.ofVirtual().unstarted(new BlockingTask());
            threads.add(virtualThread);
        }

        for (var thread: threads) {
            thread.start();
        }

        for (var thread: threads) {
            thread.join();
        }
    }


    private static class BlockingTask implements Runnable {
        @Override
        public void run() {
            System.out.println("Inside Thread: " + Thread.currentThread() + " before blocking call");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Inside Thread: " + Thread.currentThread() + " after blocking call");
        }
    }
}

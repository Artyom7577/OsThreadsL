package ThreadCoordination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinThreads {
    public static void main(String[] args) {
        List<Long> inputNumbers = Arrays.asList(0L, 3546L, 3432L, 7654L, 35L, 8976L, 8888L, 9873L);

        List<FactorialThread> threads = new ArrayList<>();

        for(var i: inputNumbers) {
            threads.add(new FactorialThread(i));
        }

        for(var thread: threads) {
            thread.setDaemon(true);
            thread.start();
        }

        for(var thread: threads) {
            try {
                thread.join(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for(int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if(factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            }else {
                System.out.println("Calculation for " + inputNumbers.get(i) + " is still in progress");
            }
        }

    }

    public static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long number) {
            this.inputNumber = number;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }


        public BigInteger factorial(long n) {
            BigInteger tmpResult = BigInteger.ONE;
            for (long i = n; i > 0; i--) {
                tmpResult = tmpResult.multiply(new BigInteger(Long.toString(i)));
            }

            return tmpResult;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}

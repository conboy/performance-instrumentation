package test;

import instrumentation.Instrumentation;

public class CPUOverhead {
    private static Instrumentation ins = Instrumentation.getInstance();
    public static void main(String[] args) throws InterruptedException {
        ins.activate(true);
 
        // CPUtest(1);
        // CPUtest(10);
        // CPUtest(100);
        // CPUtest(1000);
        CPUtest(10000);
        // CPUtest(100000);
    }
    static void CPUtest(int N) {
        int counter = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < N; i++) {
            ins.startTiming("test");
            counter++;
            // System.out.println(String.format("N: %d, iter: %d", N, counter));
            ins.stopTiming("test");
        }
        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1000000;  // convert to milliseconds
        double avgDuration = (double) duration / N; // average time per call in milliseconds

        System.out.println("Total time for " + N + " iterations: " + duration + " ms");
        System.out.println("Average time per call: " + avgDuration + " ms");
    }
}

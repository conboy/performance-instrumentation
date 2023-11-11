package test;

import instrumentation.Instrumentation;

public class MemoryOverhead {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        
        // Warm-up phase
        executeTest(10000); // Run without measurements

        // Suggest garbage collection
        System.gc();
        try {
            Thread.sleep(1000); // Pause to give the garbage collector time to run
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        
        int N = 10000;
        executeTest(N); // Run with measurements
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();

        long memoryUsed = memoryAfter - memoryBefore;
        System.out.println("Memory used for " + N + " iterations: " + memoryUsed + " bytes");
    }

    private static void executeTest(int N) {
        Instrumentation ins = Instrumentation.getInstance();
        ins.activate(true);

        for (int i = 0; i < N; i++) {
            ins.startTiming("test");
            ins.stopTiming("test");
        }
    }
}

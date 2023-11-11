package test;

import java.util.Random;
import sort.BubbleSort;
import sort.QuickSort;
import instrumentation.Instrumentation;

public class TestSort {
    private static Instrumentation ins = Instrumentation.getInstance();
    public static void main(String[] args) {
        ins.activate(true);
        ins.startTiming("main");
        ins.startTiming("populateArray");
        int[] arr = populateArray(10000); // Populate an array randomly
        ins.stopTiming("populateArray");
        int[] arr2 = arr.clone();
        
        ins.startTiming("bubble sort");
        BubbleSort.bubbleSort(arr, arr.length);
        ins.stopTiming("bubble sort");
        ins.startTiming("quick sort");
        QuickSort.quickSort(arr2, 0, arr2.length - 1);
        ins.stopTiming("quick sort");
        ins.stopTiming("main");
        ins.dump("TestSortLogs.txt");
    }

    public static int[] populateArray(int size) {
        Random random = new Random();
        int[] array = new int[size];

        for (int i = 0; i < array.length; i++) {
            // Generate random numbers from 1 to 99999
            array[i] = 1 + random.nextInt(99999);
        }

        return array;
    }
}

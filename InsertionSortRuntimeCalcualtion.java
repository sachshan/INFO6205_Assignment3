package edu.neu.coe.info6205;


import java.util.Arrays;
import java.util.Random;
import edu.neu.coe.info6205.sort.elementary.*;
import edu.neu.coe.info6205.sort.elementary.InsertionSort;
import edu.neu.coe.info6205.util.*;

public class InsertionSortRuntimeCalcualtion {

    static Integer [] randomArray(int n)
    {
        Integer [] randomArray = new Integer[n];
        Random random = new Random();

        for(int i=0; i<n; i++)
            randomArray[i] = random.nextInt();

        return randomArray;

    }

    static Integer [] orderedArray(int n)
    {
        Integer [] arr = new Integer[n];

        for(int i=0; i<n; i++)
            arr[i] = i;

        return arr;

    }

    static Integer [] reverseArray(int n)
    {
        Integer [] arr = new Integer[n];

        for(int i=0; i<n; i++)
            arr[n-i-1] = i;

        return arr;

    }

    static Integer [] partiallyOrdered(int n)
    {
        Integer []array = new Integer[n];

        Random random = new Random();

        for(int i=0; i<n/2; i++)
            array[i] = i;

        for(int i=n/2; i<n; i++)
        {
            array[i] = random.nextInt();
        }

        return array;

    }

    static void printArray(Integer []arr)
    {
        for(int i: arr)
        {
            System.out.print(i+" ");
        }

        System.out.println("\n");
    }

    public static long benchmark(int runs, int n, Integer[] xs)
    {
        Integer[] copy = Arrays.copyOf(xs,n);

        long time = 0;
        InsertionSort instance = new InsertionSort();
        for(int i=0;i<runs;i++)
        {
            long startTime = System.nanoTime();
            instance.sort(copy,0,n);
            long endTime = System.nanoTime();

            time = time + (endTime-startTime);
            copy = Arrays.copyOf(xs,n);
        }
        time = time/runs;
        return time;
    }

    static double[][] test()
    {
        int runs = 100;
        int start = 500;
        double [][]result = new double[4][6];

        for(int i=0;i<5;i++)
        {
            int size = 500*(int)Math.pow(2,i);
            result[0][i] = benchmark(runs, size, randomArray(size));
            result[1][i] = benchmark(runs, size, orderedArray(size));
            result[2][i] = benchmark(runs, size, partiallyOrdered(size));
            result[3][i] = benchmark(runs, size, reverseArray(size));

        }

        return result;
    }

    public static void main(String[] args) {
//        int start = 500;
//        double [][] result = test();
//
//        for(int i=0; i<5; i++)
//        {
//            int size = 500*(int)Math.pow(2,i);
//            System.out.println("Random Array "+size +" :"+ result[0][i]);
//            System.out.println("Ordered Array "+size +" :"+ result[1][i]);
//            System.out.println("Partially Array "+size +" :"+ result[2][i]);
//            System.out.println("Reverse Array "+size +" :"+ result[3][i]);
//
//
//        }

        System.out.println(benchmark(100, 500, randomArray(500)));
        System.out.println(benchmark(100, 500, randomArray(500)));
        System.out.println(benchmark(100, 500, randomArray(500)));
        System.out.println(benchmark(100, 500, randomArray(500)));


    }
}

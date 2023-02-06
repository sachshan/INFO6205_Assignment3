/*
 * Copyright (c) 2017. Phasmid Software
 */

package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.*;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.LazyLogger;
import edu.neu.coe.info6205.util.PrivateMethodTester;
import edu.neu.coe.info6205.util.StatPack;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("ALL")
public class InsertionSortTest {

    @Test
    public void sort0() throws Exception {
        final List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Integer[] xs = list.toArray(new Integer[0]);
        final Config config = Config.setupConfig("true", "0", "1", "", "");
        Helper<Integer> helper = HelperFactory.create("InsertionSort", list.size(), config);
        helper.init(list.size());
        final PrivateMethodTester privateMethodTester = new PrivateMethodTester(helper);
        final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");
        SortWithHelper<Integer> sorter = new InsertionSort<Integer>(helper);
        sorter.preProcess(xs);
        Integer[] ys = sorter.sort(xs);
        assertTrue(helper.sorted(ys));
        sorter.postProcess(ys);
        final int compares = (int) statPack.getStatistics(InstrumentedHelper.COMPARES).mean();
        assertEquals(list.size() - 1, compares);
        final int inversions = (int) statPack.getStatistics(InstrumentedHelper.INVERSIONS).mean();
        assertEquals(0L, inversions);
        final int fixes = (int) statPack.getStatistics(InstrumentedHelper.FIXES).mean();
        assertEquals(inversions, fixes);
    }

    @Test
    public void sort1() throws Exception {
        final List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(2);
        list.add(1);
        Integer[] xs = list.toArray(new Integer[0]);
        BaseHelper<Integer> helper = new BaseHelper<>("InsertionSort", xs.length, Config.load(InsertionSortTest.class));
        GenericSort<Integer> sorter = new InsertionSort<Integer>(helper);
        Integer[] ys = sorter.sort(xs);
        assertTrue(helper.sorted(ys));
        System.out.println(sorter.toString());
    }

    @Test
    public void testMutatingInsertionSort() throws IOException {
        final List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(2);
        list.add(1);
        Integer[] xs = list.toArray(new Integer[0]);
        BaseHelper<Integer> helper = new BaseHelper<>("InsertionSort", xs.length, Config.load(InsertionSortTest.class));
        GenericSort<Integer> sorter = new InsertionSort<Integer>(helper);
        sorter.mutatingSort(xs);
        assertTrue(helper.sorted(xs));
    }

    @Test
    public void testStaticInsertionSort() throws IOException {
        final List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(2);
        list.add(1);
        Integer[] xs = list.toArray(new Integer[0]);
        InsertionSort.sort(xs);
        assertTrue(xs[0] < xs[1] && xs[1] < xs[2] && xs[2] < xs[3]);
    }

    @Test
    public void sort2() throws Exception {
        final Config config = Config.setupConfig("true", "0", "1", "", "");
        int n = 100;
        Helper<Integer> helper = HelperFactory.create("InsertionSort", n, config);
        helper.init(n);
        final PrivateMethodTester privateMethodTester = new PrivateMethodTester(helper);
        final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");
        Integer[] xs = helper.random(Integer.class, r -> r.nextInt(1000));
        SortWithHelper<Integer> sorter = new InsertionSort<Integer>(helper);
        sorter.preProcess(xs);
        Integer[] ys = sorter.sort(xs);
        assertTrue(helper.sorted(ys));
        sorter.postProcess(ys);
        final int compares = (int) statPack.getStatistics(InstrumentedHelper.COMPARES).mean();
        // NOTE: these are suppoed to match within about 12%.
        // Since we set a specific seed, this should always succeed.
        // If we use true random seed and this test fails, just increase the delta a little.
        assertEquals(1.0, 4.0 * compares / n / (n - 1), 0.12);
        final int inversions = (int) statPack.getStatistics(InstrumentedHelper.INVERSIONS).mean();
        final int fixes = (int) statPack.getStatistics(InstrumentedHelper.FIXES).mean();
        System.out.println(statPack);
        assertEquals(inversions, fixes);
    }

    @Test
    public void sort3() throws Exception {
        final Config config = Config.setupConfig("true", "0", "1", "", "");
        int n = 100;
        Helper<Integer> helper = HelperFactory.create("InsertionSort", n, config);
        helper.init(n);
        final PrivateMethodTester privateMethodTester = new PrivateMethodTester(helper);
        final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");
        Integer[] xs = new Integer[n];
        for (int i = 0; i < n; i++) xs[i] = n - i;
        SortWithHelper<Integer> sorter = new InsertionSort<Integer>(helper);
        sorter.preProcess(xs);
        Integer[] ys = sorter.sort(xs);
        assertTrue(helper.sorted(ys));
        sorter.postProcess(ys);
        final int compares = (int) statPack.getStatistics(InstrumentedHelper.COMPARES).mean();
        // NOTE: these are suppoed to match within about 12%.
        // Since we set a specific seed, this should always succeed.
        // If we use true random seed and this test fails, just increase the delta a little.
        assertEquals(4950, compares);
        final int inversions = (int) statPack.getStatistics(InstrumentedHelper.INVERSIONS).mean();
        final int fixes = (int) statPack.getStatistics(InstrumentedHelper.FIXES).mean();
        System.out.println(statPack);
        assertEquals(inversions, fixes);
    }

    @Test
    public void OrderedSort() throws Exception
    {
        System.out.println("Ordered Array Sorting"+"\n");
        int n =1280;
        Integer[] xs = RandomizeArray(n);

        Arrays.sort(xs);
        int runs = 10000;
        long time = Benchmark_run(runs,n,xs);
        System.out.println("time taken on average to Sort a Sorted Array is " + time + " on runs "+runs);
        System.out.println();

    }

    @Test
    public void PartialOrderedSort() throws Exception
    {
        System.out.println("Partial Array Sorting"+"\n");
        int n =640;
        Integer[] xs = RandomizeArray(n);

        Random rand = new Random();
        for (int i = 0; i < n; i++) {xs[i] = i;}
        for (int j = 0; j < n/2; j++)  {
            int x =  rand.nextInt(n/2);
            int y = n/2 + rand.nextInt(n/2);
            int temp = xs[x];
            xs[x] = xs[y];
            xs[y] = temp;
        }

        int runs = 10000;
        long time = Benchmark_run(runs,n,xs);
        System.out.println("time taken on average to Sort a Partial Sorted Array is " + time + " on runs "+runs);
        System.out.println();

    }

    @Test
    public void RandomSort() throws Exception
    {
        System.out.println("Random Array Sorting"+"\n");
        int n =320;
        Integer[] xs = RandomizeArray(n);

        int runs = 10000;
        long time = Benchmark_run(runs,n,xs);
        System.out.println("time taken on average to Sort a Random Array is " + time + " on runs "+runs);
        System.out.println();

    }

    @Test
    public void ReverseOrderedSort() throws Exception
    {
        System.out.println("Reverse Ordered Array Sorting"+"\n");
        int n =320;
        Integer[] xs = RandomizeArray(n);

        Arrays.sort(xs);
        Collections.reverse(Arrays.asList(xs));
        int runs = 10000;
        long time = Benchmark_run(runs,n,xs);
        System.out.println("time taken on average to Sort a Reverse Sorted Array is " + time + " on runs "+runs);
        System.out.println();

    }

    public Integer[] RandomizeArray(int n)
    {
        Random rand = new Random();
        Integer[] xs = new Integer[n];
        for(int i=0;i<n;i++)
        {
            xs[i] = rand.nextInt(n);
        }
        return xs;
    }

    public long Benchmark_run(int runs, int n, Integer[] xs)
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

    final static LazyLogger logger = new LazyLogger(InsertionSort.class);

}
package org.gabrieljcampbell.intro2rxjava3;

import java.util.List;
import java.util.function.Function;

import io.reactivex.rxjava3.core.Flowable;

public class Utility {
    
    public static Long measureSumPerformance(Function<Flowable<Long>, Long> adder, Flowable<Long> longStreamOfNumbers, long n) {
        Long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            Long start = System.nanoTime();
            Long sum = adder.apply(longStreamOfNumbers);
            Long duration = (System.nanoTime() - start) / 1_000_000;        
            System.out.println("Result from iteration "+ i +":" + sum + ": Speed = "+ duration+"ms");
            if (duration < fastest) fastest = duration;
            }
        return fastest;
    }

    public static Long measureCubePerformance(Function<Flowable<Integer>, List<Integer>> cuber, Flowable<Integer> intStreamOfNumbers, int n) {
        Long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            Long start = System.nanoTime();
            cuber.apply(intStreamOfNumbers);
            Long duration = (System.nanoTime() - start) / 1_000_000;        
            System.out.println("Result from iteration "+ i +": Speed = "+ duration+"ms");
            if (duration < fastest) fastest = duration;
            }
        return fastest;
    }
}

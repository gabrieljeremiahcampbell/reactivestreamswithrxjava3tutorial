package org.gabrieljcampbell.intro2rxjava3;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class IntermediateRxJava3 {
  
    public static void main(String[] args) {
        
        long sumMax  = 100_000_000L;        
        Flowable<Long> streamOfSumNumbers = Flowable.rangeLong(1, sumMax); //Generate a sequence of numbers in RxJava
        

        //  //Sequential Sum
        // System.out.println("Sequential Sum  = "+ sequentialSum(streamOfSumNumbers));

        
        //  //Parallel Sum
        // parallelSum(streamOfSumNumbers);

        // //Performance Sequential Sum
        // System.out.println("Least speed for sequential Sum ="+
        // Utility.measureSumPerformance(IntermediateRxJava3::sequentialSum, streamOfSumNumbers, sumMax)+"ms");

        // //Performance Parallel Sum
        // System.out.println("Least speed for parallel Sum ="+
        // Utility.measureSumPerformance(IntermediateRxJava3::parallelSum, streamOfSumNumbers, sumMax)+"ms");

        
        
        
        
        // int cubeMax = 100;
        // Flowable<Integer> streamOfCubeNumbers = Flowable.range(1, cubeMax);
        // //Sequential Cube
        // System.out.println("Avg speed for sequential cube ="+
        // Utility.measureCubePerformance(IntermediateRxJava3::sequentialCube, streamOfCubeNumbers, cubeMax)+"ms");

        // //Parallel Cube
        // System.out.println("Avg speed for parallel cube ="+
        // Utility.measureCubePerformance(IntermediateRxJava3::parallelCube, streamOfCubeNumbers, cubeMax)+"ms");

    


        //Generators and Emitters
        LocalTime now = LocalTime.now();
        int min = now.getMinute() + 1;
        Flowable<String> flow = Flowable.
            generate((timeEmitter) -> {
            
                Thread.sleep(1000);
            LocalDateTime timeNow = 
            LocalDateTime.now();
            if(timeNow.getMinute() < min){
            String timeString = timeNow.toString();
            System.out.println("Emitting : "+timeString);
            timeEmitter.onNext(timeString);
            }else{
                timeEmitter.onComplete();
           }
        });
 
        flow.subscribe();

    }

    public static Long sequentialSum(Flowable<Long> streamOfSumNumbers) {
        Single<Long>  sum = streamOfSumNumbers.reduce(0L, Long::sum);
        return sum.blockingGet();
    }


    public static Long parallelSum(Flowable<Long> streamOfSumNumbers) {
        Flowable<Long> parallelSum = streamOfSumNumbers.parallel(4, 2_500_000 ).reduce( Long::sum); //.runOn(Schedulers.computation())
        System.out.println("Parallel Sum():");
        return parallelSum.blockingFirst();
    }

    // public static void parallelSum(Flowable<Long> streamOfSumNumbers) {
    //     Flowable<Long> parallelSum = streamOfSumNumbers.parallel(8).reduce( Long::sum);
    //     System.out.println("Parallel Sum():");
    //     parallelSum.blockingSubscribe(System.out::println);
    // }

    // public static Long parallelSum(Flowable<Long> streamOfNumbers) {
    //     Single<Long> parallelSum;
    //     parallelSum = streamOfNumbers.subscribeOn(Schedulers.computation()).parallel(4, 2_500_000).reduce(()-> 0L, Long::sum).sequential().reduce(0L, Long::sum);
    //     return parallelSum.blockingGet();
    // }

    public static List<Integer> sequentialCube(Flowable<Integer> streamOfCubeNumbers) {
        List<Integer> cubes = new ArrayList<>();
        streamOfCubeNumbers 
            .observeOn(Schedulers.computation()) 
            .map(v -> v * v ) 
            .blockingSubscribe(cubes::add); 
        
        return cubes;
    }

    public static List<Integer> parallelCube(Flowable<Integer> streamOfCubeNumbers) {
        List<Integer> cubes = new ArrayList<>();
        streamOfCubeNumbers
            .flatMap(v -> 
              Flowable.just(v)
                .subscribeOn(Schedulers.computation())
                .map(w -> w * w ))
            .doOnError(exception -> exception.printStackTrace()) 
            .doOnComplete(() -> System.out.println("Completed")) 
            .blockingSubscribe(cubes::add);
            
        return cubes;
    }  


}

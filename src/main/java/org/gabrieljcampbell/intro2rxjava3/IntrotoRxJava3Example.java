package org.gabrieljcampbell.intro2rxjava3;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;

public class IntrotoRxJava3Example {

    public static void main(String[] args) {
        

    //     //Old way of consuming bread
    //     System.out.println("Old way of bread supply and demand");
    //     Baker baker = new Baker();
    //     ArrayList<Bread> loaves = new ArrayList<Bread>();
    //     loaves.add(baker.makeBread());
    //     loaves.add(baker.makeBread());
    //     loaves.add(baker.makeBread());
    //     for(Bread loaf : loaves)
    //         System.out.println("Eating loaf number: "+ loaf.loafNumber);



    //     // Create publisher for a stream and transgress through it's contents.
    //    Flowable<String> aFlowOfStrings = Flowable.just("I","love","Reactive", "Streams");

    //    //Go through the elements of the stream
    //    aFlowOfStrings.blockingSubscribe(System.out::println);

       // Create a publisher for a stream of numbers and perform a calculation
        // Flowable<Integer> streamOfNumbers = Flowable.range(1, 1000);

        // //Sequential Sum
        // // Single<Integer> sum = streamOfNumbers.reduce(0, Integer::sum);
        // // sum.blockingSubscribe(System.out::println);

        // //Lets now do a parallel sum
        // Flowable<Integer> parallelSum = streamOfNumbers.parallel(8).reduce( Integer::sum);
        // System.out.println("Parallel Sum():");
        // parallelSum.blockingSubscribe(System.out::println);

        // New way of producing/consuming bread
        BakerPublisher bakerPublisher = new BakerPublisher();
        Flowable<Bread> flow = Flowable.fromPublisher(bakerPublisher);
        ConnectableFlowable connectableFlow = flow.replay(1, true);
        //connectableFlow.bac
        ConsumerSubscriber consumerSubscriber = new ConsumerSubscriber();
        ConsumerSubscriber consumerSubscriber2 = new ConsumerSubscriber();

        flow.subscribe(consumerSubscriber);
        flow.subscribe(consumerSubscriber2);
        BakerConsumerSubscription bakerConsumerSubscription =
             new BakerConsumerSubscription( bakerPublisher, consumerSubscriber);
             consumerSubscriber.setSubscription(bakerConsumerSubscription);

        BakerConsumerSubscription bakerConsumerSubscription2 =
             new BakerConsumerSubscription( bakerPublisher, consumerSubscriber2);
             consumerSubscriber.setSubscription(bakerConsumerSubscription2);
     
             new Thread(() -> {
                connectableFlow.connect();
                }).start();  
        
        consumerSubscriber.subscription.request(3);
        consumerSubscriber2.subscription.request(1);

    }



}

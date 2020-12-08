package org.gabrieljcampbell.intro2rxjava3;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class BakerConsumerSubscription implements Subscription {
    
    public static BakerPublisher publisher;
    public static Subscriber<? super Bread> subscriber;

    public BakerConsumerSubscription(BakerPublisher bakerPublisher, Subscriber<? super Bread> subscriber) {
        BakerConsumerSubscription.setPublisher(bakerPublisher);
        BakerConsumerSubscription.setSubscriber(subscriber);
    }

    @Override
    public void request(long n) {
        System.out.println("Loaves requested = "+ n);
        Bread loaf;
        if(publisher != null && subscriber != null && (loaf = publisher.makeBread()) != null){
            for(int i = 0; i < n; i++)
                subscriber.onNext(loaf);
        }
    }

    @Override
    public void cancel() {
        System.out.println("Do some cancellation stuff here. Consumer does nto want any more bread");
    }

    public static BakerPublisher getPublisher(){
        return BakerConsumerSubscription.publisher;
    }

    public static void setPublisher(BakerPublisher publisher){
        BakerConsumerSubscription.publisher = publisher;
    }

    public static Subscriber getSubscriber(){
        return BakerConsumerSubscription.subscriber;
    }

    public static void setSubscriber(Subscriber<? super Bread> subscriber){
        BakerConsumerSubscription.subscriber = subscriber;
    }
}

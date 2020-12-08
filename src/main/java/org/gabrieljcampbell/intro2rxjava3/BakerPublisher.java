package org.gabrieljcampbell.intro2rxjava3;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class BakerPublisher implements Publisher<Bread> {

    @Override
    public void subscribe(Subscriber<? super Bread> subscriber) {
        subscriber.onSubscribe(
                new BakerConsumerSubscription(this, subscriber));
    }
    
    public Bread makeBread(){
        return new Bread();
    }
}

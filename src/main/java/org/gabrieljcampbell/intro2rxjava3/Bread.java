package org.gabrieljcampbell.intro2rxjava3;

import java.util.concurrent.atomic.AtomicLong;

public class Bread {

    public static AtomicLong loafCounter = new AtomicLong();
    public long loafNumber;

    public Bread(){
        loafNumber = loafCounter.incrementAndGet();
    }

}

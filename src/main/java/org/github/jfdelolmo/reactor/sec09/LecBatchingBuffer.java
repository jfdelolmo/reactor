package org.github.jfdelolmo.reactor.sec09;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class LecBatchingBuffer {

    public static void main(String[] args) {
        //defaultBuffer();
        //timeoutBuffer();
        //overlapBuffer();
        droppingBuffer();

        Common.sleepSeconds(5);
    }

    private static void defaultBuffer(){
        eventStream()
                .buffer(5)
                .subscribe(Common.subscriber());
    }

    private static void timeoutBuffer(){
        eventStream()
                .bufferTimeout(5, Duration.ofMillis(600))
                .subscribe(Common.subscriber());
    }

    private static void overlapBuffer(){
        eventStream()
                .buffer(3, 1) //Overlap
                .subscribe(Common.subscriber());
    }

    private static void droppingBuffer(){
        eventStream()
                .buffer(3, 5) //Dropping or sampling
                .subscribe(Common.subscriber());
    }

    private static Flux<String> eventStream(){
        return Flux.interval(Duration.ofMillis(100))
                .map(i -> "Event" + i);
    }
}

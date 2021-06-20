package org.github.jfdelolmo.reactor.sec10;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class LecRepeat {

    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) {
        //simpleRepeat();
        conditionalRepeat();
    }

    private static void simpleRepeat(){
            getIntegers()
                    .repeat(2)
                    .subscribe(Common.subscriber());
    }

    private static Flux<Integer> getIntegers(){
        return Flux.range(1,3)
                .doOnSubscribe(s -> System.out.println("-- Subscribed"))
                .doOnComplete(() -> System.out.println("-- Completed"))
                .map(i -> atomicInteger.getAndIncrement());
    }

    private static void conditionalRepeat(){
        getIntegers()
                .repeat(() -> atomicInteger.get() < 14)
                .subscribe(Common.subscriber());
    }

}

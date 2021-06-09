package org.github.jfdelolmo.reactor.sec06;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class LecPubSubOn {

    public static void main(String[] args) {
        demo();
    }


    private static void demo() {
        Flux<Object> flux = Flux.create(fluxSink -> {
            printThreadName("create");
            for (int i = 0; i < 4; i++) {
                fluxSink.next(i);
            }
            fluxSink.complete();
        })
                .doOnNext(i -> printThreadName("next " + i));

        flux
                .publishOn(Schedulers.parallel())
                .doOnNext(c -> printThreadName("next " + c))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(c -> printThreadName("subs " + c));

        Common.sleepSeconds(5);
    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\t\t: Thread: " + Thread.currentThread().getName());
    }
}

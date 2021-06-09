package org.github.jfdelolmo.reactor.sec06;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class LecPublishOn {

    public static void main(String[] args) {
        defaultPublishOn();
    }

    private static void defaultPublishOn(){
        Flux<Object> flux = Flux.create(fluxSink -> {
            printThreadName("create");
            for (int i = 0; i < 4; i++) {
                fluxSink.next(i);
            }
            fluxSink.complete();
        })
                .doOnNext(i -> printThreadName("next " + i));

        flux
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(c -> printThreadName("next " + c))
                .publishOn(Schedulers.parallel())
                .subscribe(c -> printThreadName("subs " + c));

        Common.sleepSeconds(5);
    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\t\t: Thread: " + Thread.currentThread().getName());
    }
}

package org.github.jfdelolmo.reactor.sec06;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class LecParallel {

    public static void main(String[] args) {
        demo();
    }

    private static void demo() {

        Flux.range(1, 10)
                .parallel(4)
                .runOn(Schedulers.boundedElastic())
                .doOnNext(i -> printThreadName("next " + i))
                .sequential()
                .subscribe(s -> printThreadName("subs " + s));

        Common.sleepSeconds(5);
    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\t\t: Thread: " + Thread.currentThread().getName());
    }
}

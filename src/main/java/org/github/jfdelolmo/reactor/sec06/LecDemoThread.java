package org.github.jfdelolmo.reactor.sec06;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

public class LecDemoThread {

    public static void main(String[] args) {
        //defaultThread();
        runnableThreads();
    }

    private static void defaultThread() {
        Flux<Object> flux = Flux.create(fluxSink -> {
            printThreadName("create");
            fluxSink.next(1);
        })
                .doOnNext(i -> printThreadName("next " + i));

        flux.subscribe(c -> printThreadName("subscribe " + c));
    }

    private static void runnableThreads() {
        Flux<Object> flux = Flux.create(fluxSink -> {
            printThreadName("create");
            fluxSink.next(1);
        })
                .doOnNext(i -> printThreadName("next " + i));

        Runnable runnable = () -> flux.subscribe(c -> printThreadName("subscribe " + c));

        for (int i=0; i<2;i++){
            new Thread(runnable).start();
        }

        Common.sleepSeconds(5);
    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\t\t: Thread: " + Thread.currentThread().getName());
    }
}

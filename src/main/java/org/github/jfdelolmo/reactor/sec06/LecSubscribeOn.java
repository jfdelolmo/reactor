package org.github.jfdelolmo.reactor.sec06;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class LecSubscribeOn {

    public static void main(String[] args) {
        //defaultSubscribeOn();
        //runnableSubscribeOn();
        //runnableMultipleSubscribeOn();
        multipleItems();

    }

    private static void defaultSubscribeOn() {
        Flux<Object> flux = Flux.create(fluxSink -> {
            printThreadName("create");
            fluxSink.next(1);
        })
                .doOnNext(i -> printThreadName("next " + i));

        flux
                .doFirst(() -> printThreadName("first2"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> printThreadName("first1"))
                .subscribe(c -> printThreadName("subs " + c));

        Common.sleepSeconds(3);
    }

    private static void runnableSubscribeOn() {
        Flux<Object> flux = Flux.create(fluxSink -> {
            printThreadName("create");
            fluxSink.next(1);
        })
                .doOnNext(i -> printThreadName("next " + i));

        Runnable runnable = () -> flux
                .doFirst(() -> printThreadName("first2"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> printThreadName("first1"))
                .subscribe(c -> printThreadName("subs " + c));

        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start();
        }

        Common.sleepSeconds(5);
    }

    private static void runnableMultipleSubscribeOn() {
        Flux<Object> flux = Flux.create(fluxSink -> {
            printThreadName("create");
            fluxSink.next(1);
        })
                .subscribeOn(Schedulers.newParallel("Parll")) //The closer to the publisher has precedence
                .doOnNext(i -> printThreadName("next " + i));

        Runnable runnable = () -> flux
                .doFirst(() -> printThreadName("first2"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> printThreadName("first1"))
                .subscribe(c -> printThreadName("subs " + c));

        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start();
        }

        Common.sleepSeconds(5);
    }

    private static void multipleItems() {
        Flux<Object> flux = Flux.create(fluxSink -> {
            printThreadName("create");
            for (int i = 0; i < 20; i++) {
                fluxSink.next(i);
                Common.sleepSeconds(1);
            }
        })
                .doOnNext(i -> printThreadName("next " + i));

        flux
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(c -> printThreadName("subs " + c));


        Common.sleepSeconds(5);
    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\t\t: Thread: " + Thread.currentThread().getName());
    }
}

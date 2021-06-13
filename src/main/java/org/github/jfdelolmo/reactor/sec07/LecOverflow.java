package org.github.jfdelolmo.reactor.sec07;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class LecOverflow {

    public static void main(String[] args) {
        //overflowDefault();
        //overflowDrop();
        //overflowDropQueueSize();
        //overflowLatest();
        //overflowError();
        //overflowBufferSize();
        overflowStrategyAsConstructorParameter();
    }

    private static void overflowDefault() {
        //All the items are pushed before start receiving any
        Flux.create(fluxSink -> {
            for (int i = 0; i < 100; i++) {
                fluxSink.next(i);
                System.out.println("Pushed: " + i);
            }
            fluxSink.complete();
        })
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i-> Common.sleepMillis(10))
                .subscribe(Common.subscriber());

        Common.sleepSeconds(40);
    }

    private static void overflowDrop() {
        //We are able to start receive values
        Flux.create(fluxSink -> {
            for (int i = 0; i < 100; i++) {
                fluxSink.next(i);
                System.out.println("Pushed: " + i);
                Common.sleepMillis(1);
            }
            fluxSink.complete();
        })
                .onBackpressureDrop()
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i-> Common.sleepMillis(10))
                .subscribe(Common.subscriber());

        Common.sleepSeconds(40);
    }

    //Reducing the size of the buffer, some items are not received on the subscriber
    //The list is consuming all these elements
    private static void overflowDropQueueSize() {
        System.setProperty("reactor.bufferSize.small", "16");
        List<Object> list = new ArrayList<>();
        Flux.create(fluxSink -> {
            for (int i = 0; i < 100; i++) {
                fluxSink.next(i);
                System.out.println("Pushed: " + i);
                Common.sleepMillis(1);
            }
            fluxSink.complete();
        })
                .onBackpressureDrop(list::add)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i-> Common.sleepMillis(10))
                .subscribe(Common.subscriber());

        Common.sleepSeconds(5);
        System.out.println(list);
    }

    //The lastest item pushed is cached an always received
    private static void overflowLatest(){
        System.setProperty("reactor.bufferSize.small", "16");

        Flux.create(fluxSink -> {
            for (int i = 0; i < 100; i++) {
                fluxSink.next(i);
                System.out.println("Pushed: " + i);
                Common.sleepMillis(1);
            }
            fluxSink.complete();
        })
                .onBackpressureLatest()
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i-> Common.sleepMillis(10))
                .subscribe(Common.subscriber());

        Common.sleepSeconds(5);
    }

    private static void overflowError(){
        System.setProperty("reactor.bufferSize.small", "16");

        Flux.create(fluxSink -> {
            for (int i = 0; i < 200 && !fluxSink.isCancelled(); i++) {
                fluxSink.next(i);
                System.out.println("Pushed: " + i);
                Common.sleepMillis(1);
            }
            fluxSink.complete();
        })
                .onBackpressureError()
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i-> Common.sleepMillis(10))
                .subscribe(Common.subscriber());

        Common.sleepSeconds(5);
    }

    private static void overflowBufferSize(){
        System.setProperty("reactor.bufferSize.small", "16");

        Flux.create(fluxSink -> {
            for (int i = 0; i < 200 && !fluxSink.isCancelled(); i++) {
                fluxSink.next(i);
                System.out.println("Pushed: " + i);
                Common.sleepMillis(1);
            }
            fluxSink.complete();
        })
                .onBackpressureBuffer(20, dropped -> System.out.println("Dropped: " + dropped) )
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i-> Common.sleepMillis(10))
                .subscribe(Common.subscriber());

        Common.sleepSeconds(5);
    }

    private static void overflowStrategyAsConstructorParameter(){
        System.setProperty("reactor.bufferSize.small", "16");

        Flux.create(fluxSink -> {
            for (int i = 0; i < 200 && !fluxSink.isCancelled(); i++) {
                fluxSink.next(i);
                System.out.println("Pushed: " + i);
                Common.sleepMillis(1);
            }
            fluxSink.complete();
        }, FluxSink.OverflowStrategy.DROP)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i-> Common.sleepMillis(10))
                .subscribe(Common.subscriber());

        Common.sleepSeconds(5);


    }
}

package org.github.jfdelolmo.reactor.sec11;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class LecSinkMany {

    public static void main(String[] args) {
        //sinkUnicast();
        //sinkUnicastMultipleSubscriber();
        //sinkMulticast();
        //sinkMulticastBuffer();
        //sinkMulticastDirectAllOrNothing();
        //sinkMulticastDirectBestEffort();
        sinkReplay();
    }

    private static void sinkUnicast(){
        //Handle through which we would push items
        final Sinks.Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();

        // Handle through which subscribers will receive items
        final Flux<Object> flux = sink.asFlux();

        flux.subscribe(Common.subscriber("Sam"));

        sink.tryEmitNext("Hi");
        sink.tryEmitNext("How are you");
        sink.tryEmitNext("?");
    }

    private static void sinkUnicastMultipleSubscriber(){
        //Handle through which we would push items
        final Sinks.Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();

        // Handle through which subscribers will receive items
        final Flux<Object> flux = sink.asFlux();

        flux.subscribe(Common.subscriber("Sam"));
        flux.subscribe(Common.subscriber("Bill"));

        sink.tryEmitNext("Hi");
        sink.tryEmitNext("How are you");
        sink.tryEmitNext("?");
    }

    private static void sinkMulticast(){
        //Handle through which we would push items
        final Sinks.Many<Object> sink = Sinks.many().multicast().onBackpressureBuffer();

        // Handle through which subscribers will receive items
        final Flux<Object> flux = sink.asFlux();

        flux.subscribe(Common.subscriber("Sam"));
        flux.subscribe(Common.subscriber("Bill"));

        sink.tryEmitNext("Hi");
        sink.tryEmitNext("How are you");
        sink.tryEmitNext("?");
    }

    private static void sinkMulticastBuffer(){
        //Handle through which we would push items
        final Sinks.Many<Object> sink = Sinks.many().multicast().onBackpressureBuffer();

        // Handle through which subscribers will receive items
        final Flux<Object> flux = sink.asFlux();

        sink.tryEmitNext("Hi");
        sink.tryEmitNext("How are you");

        flux.subscribe(Common.subscriber("Sam"));
        flux.subscribe(Common.subscriber("Bill"));
        sink.tryEmitNext("?");
        flux.subscribe(Common.subscriber("Jake"));
        sink.tryEmitNext("New message");
    }

    private static void sinkMulticastDirectAllOrNothing(){
        //Handle through which we would push items
        final Sinks.Many<Object> sink = Sinks.many().multicast().directAllOrNothing();

        // Handle through which subscribers will receive items
        final Flux<Object> flux = sink.asFlux();

        sink.tryEmitNext("Hi");
        sink.tryEmitNext("How are you");

        flux.subscribe(Common.subscriber("Sam"));
        flux.subscribe(Common.subscriber("Bill"));
        sink.tryEmitNext("?");
        flux.subscribe(Common.subscriber("Jake"));
        sink.tryEmitNext("New message");
    }

    private static void sinkMulticastDirectBestEffort(){
        System.setProperty("reactor.bufferSize.small", "16");

        //Handle through which we would push items
        final Sinks.Many<Object> sink = Sinks.many().multicast().directBestEffort();

        // Handle through which subscribers will receive items
        final Flux<Object> flux = sink.asFlux();

        flux.subscribe(Common.subscriber("Sam"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(Common.subscriber("Bill"));

        for (int i = 0; i < 100; i++) {
            sink.tryEmitNext(i);
        }

        Common.sleepSeconds(10);
    }

    private static void sinkReplay(){
        //Handle through which we would push items
        final Sinks.Many<Object> sink = Sinks.many().replay().all();

        // Handle through which subscribers will receive items
        final Flux<Object> flux = sink.asFlux();

        sink.tryEmitNext("Hi");
        sink.tryEmitNext("How are you");

        flux.subscribe(Common.subscriber("Sam"));
        flux.subscribe(Common.subscriber("Bill"));
        sink.tryEmitNext("?");
        flux.subscribe(Common.subscriber("Jake"));
        sink.tryEmitNext("New message");
    }
}

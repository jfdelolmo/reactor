package org.github.jfdelolmo.reactor.sec11;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class LecSinkOne {

    public static void main(String[] args) {
        //simpleSinkOne();
        //handlingSinkOne();
        handlingSinkOneMultipleSubscribers();
    }

    private static void simpleSinkOne(){
        //Mono can emmit 1 value / empty / error
        final Sinks.One<Object> sink = Sinks.one();

        final Mono<Object> mono = sink.asMono();

        mono.subscribe(Common.subscriber("Sam"));

        sink.tryEmitValue("Hi!");
    }

    private static void handlingSinkOne(){
        final Sinks.One<Object> sink = Sinks.one();

        final Mono<Object> mono = sink.asMono();
        mono.subscribe(Common.subscriber("Sam"));

        sink.emitValue("Hi", (signalType, emitResult) -> {
            System.out.println("--- " + signalType.name());
            System.out.println("--- " + emitResult.name());
            return false;
        });

        sink.emitValue("Bye", (signalType, emitResult) -> {
            System.out.println("--- " + signalType.name());
            System.out.println("--- " + emitResult.name());
            return false;
        });
    }

    private static void handlingSinkOneMultipleSubscribers() {
        final Sinks.One<Object> sink = Sinks.one();

        final Mono<Object> mono = sink.asMono();
        mono.subscribe(Common.subscriber("Sam"));
        mono.subscribe(Common.subscriber("Bill"));

        sink.tryEmitValue("Hello!!");
    }

}

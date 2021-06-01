package org.github.jfdelolmo.reactor.sec04;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class LecDoCallbacks {

    public static void main(String[] args) {
        simpleOne();
    }

    private static void simpleOne() {

        Flux.create(LecDoCallbacks::fluxProducer)
                .doOnComplete(() -> System.out.println("doOnComplete"))
                .doFirst(() -> System.out.println("doFirst 1"))
                .doOnNext(o -> System.out.println("doOnNext: " + o))
                .doOnSubscribe(s -> System.out.println("doOnSubscribe: " + s))
                .doOnRequest(r -> System.out.println("doOnRequest: " + r))
                .doOnError(e -> System.out.println("doOnError: " + e))
                .doFirst(() -> System.out.println("doFirst 2"))
                .doOnTerminate(() -> System.out.println("doOnTerminate"))
                .doOnCancel(() -> System.out.println("donOnCancel"))
                .doOnDiscard(Object.class, o -> System.out.println("doOnDiscard: " + o))
                .doFinally(signalType -> System.out.println("doOnFinally" + signalType))
                .subscribe(Common.subscriber());
    }

    private static void fluxProducer(FluxSink<Object> fluxSink) {
        System.out.println("Inside create");
        for (int i = 0; i < 5; i++) {
            fluxSink.next(i);
        }
        fluxSink.complete();
        System.out.println("-- completed");
    }
}

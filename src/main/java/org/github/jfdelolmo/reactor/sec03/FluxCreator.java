package org.github.jfdelolmo.reactor.sec03;

import org.github.jfdelolmo.reactor.common.Common;
import org.github.jfdelolmo.reactor.sec03.helper.NameProducer;
import reactor.core.publisher.Flux;

public class FluxCreator {

    public static void main(String[] args) {
        //simpleFluxCreate();
        //simpleFluxCreateRefactor();
        simpleFluxCreateRefactor2();
        //fluxCreateWithProducer();
    }

    private static void simpleFluxCreate() {
        Flux.create(fluxSink -> {
            String name;
            do {
                name = Common.faker().country().name();
                fluxSink.next(name);
            } while (!"Canada".equals(name));
            fluxSink.complete();
        }).subscribeWith(Common.subscriber());
    }

    private static void simpleFluxCreateRefactor() {
        Flux.create(fluxSink -> {
            String name;
            do {
                name = Common.faker().country().name();
                fluxSink.next(name);
            } while (!"Canada".equals(name) && !fluxSink.isCancelled());
            fluxSink.complete();
        }).subscribeWith(Common.subscriber());
    }

    private static void simpleFluxCreateRefactor2() {
        Flux.create(fluxSink -> {
                        do {
                            fluxSink.next(Common.faker().country().name());
                        } while (!fluxSink.isCancelled());
                        fluxSink.complete();
                    })
//                .log()
                .takeUntil(name -> "Canada".equalsIgnoreCase(name.toString()))
                .subscribeWith(Common.subscriber());
    }


    private static void fluxCreateWithProducer() {
        NameProducer nameProducer = new NameProducer();
        Flux.create(nameProducer)
                .subscribe(Common.subscriber());

        Runnable runnable = nameProducer::produce;
        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
        Common.sleepSeconds(2);
    }

}

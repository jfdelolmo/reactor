package org.github.jfdelolmo.reactor.sec04;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

public class LecHandle {

    public static void main(String[] args) {
        simpleHandle();

        countryHandle();
    }

    private static void simpleHandle() {
        Flux.range(2, 30)
                .handle((integer, synchronousSink) -> {
                    if (integer % 2 == 0)
                        synchronousSink.next(integer);
                    else
                        synchronousSink.next(integer + " a");
                })
                .subscribe(Common.subscriber());
    }

    private static void countryHandle() {
        Flux.generate(synchronousSink -> synchronousSink.next(Common.faker().country().name()))
                .map(String::valueOf)
                .handle((country, sink) -> {
                    sink.next(country);
                    if ("Spain".equalsIgnoreCase(country)) {
                        sink.complete();
                    }
                }).subscribe(Common.subscriber());
    }

}

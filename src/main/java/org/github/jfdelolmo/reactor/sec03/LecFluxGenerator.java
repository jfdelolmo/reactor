package org.github.jfdelolmo.reactor.sec03;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

public class LecFluxGenerator {

    public static void main(String[] args) {
//        generateTake();
//        generateUntilCanada();
        generateWithState();
    }

    private static void generateTake() {
        Flux.generate(synchronousSink -> {
            System.out.println("Emitting");
            synchronousSink.next(Common.faker().ancient().god()); //Only 1 item can be emitted
//            synchronousSink.complete();
//            synchronousSink.error(new RuntimeException("Ooops!"));
        })
                .take(2)
                .subscribe(Common.subscriber());
    }

    private static void generateUntilCanada() {
        Flux.generate(synchronousSink -> synchronousSink.next(Common.faker().country().name()))
                .takeUntil(i -> "canada".equalsIgnoreCase(i.toString()))
                .subscribe(Common.subscriber());
    }

    private static void generateWithState() {
        Flux.generate(
                () -> 1,
                (counter, sink) -> {
                    String country = Common.faker().country().name();
                    sink.next(country);
                    if (counter >= 10 || "Canada".equalsIgnoreCase(country)) {
                        sink.complete();
                    }
                    return counter + 1;
                }
        )
                .take(4)
                .subscribe(Common.subscriber());

    }
}

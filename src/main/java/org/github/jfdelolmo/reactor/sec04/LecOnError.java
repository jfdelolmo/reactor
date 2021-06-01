package org.github.jfdelolmo.reactor.sec04;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class LecOnError {

    public static void main(String[] args) {

        Flux.range(1, 10)
                .log()
                .map(i -> 10 / (5 - i))
                //.onErrorReturn(-1)
                //.onErrorResume(e -> fallback())
                .onErrorContinue((err,object) -> {
                    System.err.println("onErrorContinue");
                })
                .subscribe(Common.subscriber());

    }

    private static Mono<Integer> fallback() {
        return Mono.fromSupplier(() -> Common.faker().random().nextInt(100, 200));
    }
}

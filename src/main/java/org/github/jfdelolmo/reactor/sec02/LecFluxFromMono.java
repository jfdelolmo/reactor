package org.github.jfdelolmo.reactor.sec02;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class LecFluxFromMono {

    public static void main(String[] args) {
        final Mono<String> mono = Mono.just("a");
        doSomething(mono.flux());

        final Flux<String> flux = Flux.from(mono);
        doSomething(flux);

        //Flux to mono with 1 element
        Flux.range(1, 10)
                .filter(i -> i > 3)
                .next()
                .subscribe(Common.onNext(), Common.onError(), Common.onComplete());
        //Flux to mono with all elements
        Flux.range(1, 10)
                .filter(i -> i > 3)
                .collectList()
                .subscribe(Common.onNext(), Common.onError(), Common.onComplete());
    }

    private static void doSomething(Flux<String> flux) {
        flux.subscribe(Common.onNext());
    }
}

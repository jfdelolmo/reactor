package org.github.jfdelolmo.reactor.sec04;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class LecDefaultIfEmpty {

    public static void main(String[] args) {
        getOrderedNumbers()
                .filter(i -> i > 10)
                .defaultIfEmpty(-100)
                .subscribe(Common.subscriber());
    }

    private static Flux<Integer> getOrderedNumbers() {
        return Flux.range(1, 10);
    }
}

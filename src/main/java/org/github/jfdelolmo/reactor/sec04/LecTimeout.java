package org.github.jfdelolmo.reactor.sec04;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class LecTimeout {

    public static void main(String[] args) {
        getOrderedNumbers()
                .log()
                .timeout(Duration.ofSeconds(2), fallback())
                .subscribe(Common.subscriber());

        Common.sleepSeconds(60);
    }

    private static Flux<Integer> getOrderedNumbers() {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(5));
    }

    private static Flux<Integer> fallback(){
        return Flux.range(1,10)
                .delayElements(Duration.ofMillis(200));
    }
}

package org.github.jfdelolmo.reactor.sec08;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class CarPriceAssignment {

    public static void main(String[] args) {
        final int value = 10000;
        Flux.combineLatest(months(), demmand(), (m,d)->{
            return (value - (100 * m)) * d;
        })
                .subscribe(Common.subscriber());

        Common.sleepSeconds(15);
    }

    private static Flux<Long> months(){
        return Flux.interval(Duration.ZERO, Duration.ofSeconds(1));
    }

    private static Flux<Double> demmand(){
        return Flux.interval(Duration.ofSeconds(3))
                .map(i-> Common.faker().random().nextInt(80, 120) / 100d)
                .startWith(1D);
    }


}

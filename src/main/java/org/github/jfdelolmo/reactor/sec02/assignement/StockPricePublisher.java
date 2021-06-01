package org.github.jfdelolmo.reactor.sec02.assignement;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class StockPricePublisher {
    public static final Integer STARTING_PRICE = 100; //Dollars

    public static Flux<Integer> getPrice() {
        AtomicInteger stock = new AtomicInteger(STARTING_PRICE);

        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> stock.getAndAccumulate(
                        Common.faker().random().nextInt(-5, +5),
                        Integer::sum)
                );
    }
}

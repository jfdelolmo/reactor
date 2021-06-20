package org.github.jfdelolmo.reactor.sec09.helper;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class OrderService {

    public static Flux<PurchaseOrder> orderStream(){
        return Flux.interval(Duration.ofMillis(100))
                .map(i -> new PurchaseOrder());
    }
}

package org.github.jfdelolmo.reactor.sec05.assignement;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Objects;

public class OrderService {

    private Flux<PurchaseOrder> flux;

    public Flux<PurchaseOrder> orderStream(){
        if (Objects.isNull(flux)){
            flux=getOrderStream();
        }
        return flux;
    }

    private Flux<PurchaseOrder> getOrderStream(){
        return Flux.interval(Duration.ofMillis(100))
                .map(i->new PurchaseOrder())
                .publish()
                .refCount(2);
    }
}

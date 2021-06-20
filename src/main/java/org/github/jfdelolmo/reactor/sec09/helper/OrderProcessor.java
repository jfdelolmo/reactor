package org.github.jfdelolmo.reactor.sec09.helper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class OrderProcessor {

    public static Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>> automotiveProcessor() {
        return flux -> flux
                .doOnNext(p -> p.setPrice(1.1 * p.getPrice()))
                .doOnNext(p -> p.setItem("{{ " + p.getItem() + " }}"));
    }

    public static Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>> kidsProcessor() {
        return flux -> flux
                .doOnNext(p -> p.setPrice(0.5 * p.getPrice()))
                .flatMap(p -> Flux.concat(Mono.just(p), getFreeKidsOrder()));
    }

    public static Mono<PurchaseOrder> getFreeKidsOrder() {
        return Mono.fromSupplier(() -> {
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setItem("FREE - " + purchaseOrder.getItem());
            purchaseOrder.setPrice(0D);
            purchaseOrder.setCategory("Kids");

            return purchaseOrder;
        });
    }

}

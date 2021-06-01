package org.github.jfdelolmo.reactor.sec04.helper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class OrderService {

    private static List<PurchaseOrder> list1 = List.of(
            new PurchaseOrder(1),
            new PurchaseOrder(2),
            new PurchaseOrder(3)
    );

    private static List<PurchaseOrder> list2 = List.of(
            new PurchaseOrder(1),
            new PurchaseOrder(2)
    );

    private static List<PurchaseOrder> list3 = List.of(
            new PurchaseOrder(1),
            new PurchaseOrder(2),
            new PurchaseOrder(3),
            new PurchaseOrder(3)
    );

    private static Map<Integer, List<PurchaseOrder>> db = Map.of(
            1, list1, 2, list2, 3, list3
    );

    public static Flux<PurchaseOrder> getOrders(int userId){
        return Flux.create((FluxSink<PurchaseOrder>purchaseOrderFluxSink) -> {
            db.get(userId).forEach(purchaseOrderFluxSink::next);
            purchaseOrderFluxSink.complete();
        })
                .delayElements(Duration.ofSeconds(1));
    }
}

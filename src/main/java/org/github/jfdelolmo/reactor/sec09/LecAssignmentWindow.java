package org.github.jfdelolmo.reactor.sec09;

import com.sun.source.doctree.SeeTree;
import org.github.jfdelolmo.reactor.common.Common;
import org.github.jfdelolmo.reactor.sec03.FluxCreator;
import org.github.jfdelolmo.reactor.sec09.helper.OrderProcessor;
import org.github.jfdelolmo.reactor.sec09.helper.OrderService;
import org.github.jfdelolmo.reactor.sec09.helper.PurchaseOrder;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class LecAssignmentWindow {

    public static void main(String[] args) {

        Map<String, Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>>> map = Map.of(
                "Kids", OrderProcessor.kidsProcessor(),
                "Automotive", OrderProcessor.automotiveProcessor()
        );

        Set<String> set = map.keySet();

        OrderService.orderStream()
                .filter(p -> set.contains(p.getCategory()))
                .groupBy(PurchaseOrder::getCategory)
                .flatMap(gf -> map.get(gf.key()).apply(gf))
                .subscribe(Common.subscriber());

        Common.sleepSeconds(60);

    }

}

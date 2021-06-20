package org.github.jfdelolmo.reactor.sec09;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class LecBatchingWindow {

    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    private static Set<String> colorGroup = Set.of("blue", "red");

    public static void main(String[] args) {
        //windowDefault();
        windowGroupBy();

        Common.sleepSeconds(40);
    }

    private static void windowDefault() {
        eventStream()
                .window(5)
                .flatMap(LecBatchingWindow::saveEvents)
                .subscribe(Common.subscriber());
    }


    private static void windowGroupBy() {

        Flux.interval(Duration.ofMillis(500))
                .map(i -> Common.faker().color().name())
                .groupBy(c -> colorGroup.contains(c))
                .subscribe(gf -> processColor(gf, gf.key()));

    }


    private static void processColor(Flux<String> flux, boolean key){
        System.out.println("Called");
        flux.subscribe(i -> System.out.println("Key: " + key + ", Item: " + i));
    }


    private static Mono<Integer> saveEvents(Flux<String> flux) {
        return flux.doOnNext(e -> System.out.println("Saving " + e))
                .doOnComplete(() -> {
                    System.out.println("Saved this batch");
                    System.out.println("----------------");
                })
                .then(Mono.just(atomicInteger.getAndIncrement()));
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> "Event" + i);
    }
}

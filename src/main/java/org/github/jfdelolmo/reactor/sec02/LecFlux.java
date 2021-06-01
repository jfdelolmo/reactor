package org.github.jfdelolmo.reactor.sec02;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

public class LecFlux {

    public static void main(String[] args) {
        fluxJust();
        fluxMultipleSubscriber();
        fluxFromArrayOrList();
        fluxFromStream();
        fluxFromRange();
    }

    private static void fluxJust() {
        final Flux<Object> just = Flux.just(
                Common.faker().currency().name(),
                Common.faker().currency().name(),
                Common.faker().currency().name(),
                Common.faker().currency().name(),
                Common.faker().currency().name());
        just.subscribe(Common.onNext(), Common.onError(), Common.onComplete());

        final Flux<Object> empty = Flux.empty();
        empty.subscribe(Common.onNext(), Common.onError(), Common.onComplete());
    }

    private static void fluxMultipleSubscriber() {
        final Flux<Integer> flux = Flux.just(1, 2, 3, 4);
        flux.subscribe(i -> System.out.println("Subscriber 1: " + i));

        final Flux<Integer> evenFlux = flux.filter(i -> i % 2 == 0);
        evenFlux.subscribe(i -> System.out.println("Subscriber 2: " + i));
    }

    private static void fluxFromArrayOrList() {
        Integer[] integers = {1, 2, 3, 4};
        Flux.fromArray(integers)
                .subscribe(Common.onNext());

        final List<String> strings = List.of("a", "b", "c");
        Flux.fromIterable(strings)
                .subscribe(Common.onNext());
    }

    private static void fluxFromStream() {
        final List<Integer> integerList = List.of(1, 2, 3, 4, 5);
        //stream is used to be able to provide a new reference for each subscriber
        final Flux<Integer> integerFlux = Flux.fromStream(integerList::stream);
        integerFlux.subscribe(Common.onNext(), Common.onError(), Common.onComplete());
        integerFlux.subscribe(Common.onNext(), Common.onError(), Common.onComplete());
    }

    private static void fluxFromRange(){
        Flux.range(1, 10).subscribe(Common.onNext());
        Flux.range(4, 3).subscribe(Common.onNext());

        Flux.range(1,10)
                .log()
                .map(i->Common.faker().name().fullName())
                .log()
                .subscribe(Common.onNext());
    }
}

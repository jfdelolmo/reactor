package org.github.jfdelolmo.reactor.sec01;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class Lec02Mono {

    public static void main(String[] args) {
        //monoJust();
        //monoSubscribe();
        //monoSubscribeOnError();
        //monoEmptyOrError();
        //monoFromSupplier();
        //monoFromSupplierRefactor();
        //monoFromFuture();
        monoFromRunnable();
    }

    private static void monoJust() {
        final Mono<Integer> mono = Mono.just(1);
        mono.subscribe(i -> System.out.printf("Received: %d%n", i));
    }

    private static void monoSubscribe() {
        final Mono<String> mono = Mono.just(Common.faker().chuckNorris().fact());
        mono.subscribe(
                System.out::println,
                err -> System.err.println(err.getMessage()),
                () -> System.out.println("Completed")
        );
    }

    private static void monoSubscribeOnError() {
        final Mono<Integer> mono = Mono.just("error")
                .map(String::length)
                .map(l -> l / 0);

//        mono.subscribe(
//                System.out::println,
//                err -> System.err.println(err.getMessage()),
//                () -> System.out.println("Completed")
//        );

        mono.subscribe(
                Common.onNext(),
                Common.onError(),
                Common.onComplete()
        );
    }

    private static void monoEmptyOrError() {
        getUser(1).subscribe(Common.onNext(), Common.onError(), Common.onComplete());
        getUser(2).subscribe(Common.onNext(), Common.onError(), Common.onComplete());
        getUser(3).subscribe(Common.onNext(), Common.onError(), Common.onComplete());
    }

    private static Mono<String> getUser(int userId) {
        if (1 == userId) {
            return Mono.just(Common.faker().name().name());
        } else if (2 == userId) {
            return Mono.empty();
        } else {
            return Mono.error(new RuntimeException("Not allowed"));
        }
    }

    private static void monoFromSupplier() {
        //Just, only to be used when we already have the data, otherwise is time consuming
        //Mono<String> mono = Mono.just(getName());
        Mono<String> mono = Mono.fromSupplier(Lec02Mono::getName);
        mono.subscribe(Common.onNext());

        Supplier<String> stringSupplier = Lec02Mono::getName;
        Mono<String> mono2 = Mono.fromSupplier(stringSupplier);
        mono2.subscribe(Common.onNext());

        Callable<String> stringCallable = Lec02Mono::getName;
        Mono<String> mono3 = Mono.fromCallable(stringCallable);
        mono3.subscribe(Common.onNext());

    }

    private static String getName() {
        System.out.println("Generating name...");
        return Common.faker().funnyName().name();
    }

    private static void monoFromSupplierRefactor() {
        getMonoName();
        getMonoName()
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(Common.onNext());
        getMonoName();

        Common.sleepSeconds(4); // To be able to see the results
    }

    private static Mono<String> getMonoName() {
        System.out.println("Entered in get mono name");
        return Mono.fromSupplier(() -> {
            Common.sleepSeconds(3);
            return getName();
        }).map(String::toUpperCase);
    }

    private static void monoFromFuture() {
        Mono.fromFuture(Lec02Mono::getFuturableName)
                .subscribe(Common.onNext());
        Common.sleepSeconds(1); // To be able to see the results
    }

    private static CompletableFuture<String> getFuturableName() {
        return CompletableFuture.supplyAsync(() -> Common.faker().name().fullName());
    }

    public static void monoFromRunnable() {

        Mono.fromRunnable(timeConsumingProcess())
                .subscribe(Common.onNext(), Common.onError(), Common.onComplete());

    }

    private static Runnable timeConsumingProcess(){
        return () -> {
            System.out.println("I'm processing...");
            Common.sleepSeconds(3);
        };
    }
}

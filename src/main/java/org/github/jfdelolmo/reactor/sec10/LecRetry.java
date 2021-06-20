package org.github.jfdelolmo.reactor.sec10;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

public class LecRetry {

    public static void main(String[] args) {
        //simpleRetry();
        //fixedDelayRetry();
        advancedRetry();
    }

    private static void simpleRetry() {
        getIntegers()
                .retry(2)
                .subscribe(Common.subscriber());

        Common.sleepSeconds(10);
    }

    private static void fixedDelayRetry() {
        getIntegers()
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .subscribe(Common.subscriber());

        Common.sleepSeconds(10);
    }

    private static Flux<Integer> getIntegers() {

        return Flux.range(1, 3)
                .doOnSubscribe(s -> System.out.println("-- Subscribed"))
                .doOnComplete(() -> System.out.println("-- Completed"))
                .map(i -> i / (Common.faker().random().nextInt(1, 5) > 3 ? 0 : 1)) //We are forcing the error
                .doOnError(err -> System.out.println("-- Error: " + err));
    }

    private static void advancedRetry() {
        orderService(Common.faker().business().creditCardNumber())
                .doOnError(err -> System.out.println(err.getMessage()))
                .retryWhen(
                        Retry.from(flux -> flux
                                .doOnNext(rs -> {
                                    System.out.println(rs.totalRetries());
                                    System.out.println(rs.failure());
                                })
                                .handle((rs, synchronousSink) -> {
                                    if ("500".equals(rs.failure().getMessage())) {
                                        synchronousSink.next(1);
                                    } else {
                                        synchronousSink.error(rs.failure());
                                    }
                                })
                                .delayElements(Duration.ofSeconds(1))
                        )
                )
                .subscribe(Common.subscriber());

        Common.sleepSeconds(10);
    }

    //Order service
    private static Mono<String> orderService(String ccNumber) {
        return Mono.fromSupplier(() -> {
            processPayment(ccNumber);
            return Common.faker().idNumber().valid();
        });
    }

    //Payment service
    private static void processPayment(String ccNumber) {
        int random = Common.faker().random().nextInt(1, 10);
        if (random < 8) {
            throw new RuntimeException("500");
        } else if (random < 10) {
            throw new RuntimeException("404");
        }
    }
}

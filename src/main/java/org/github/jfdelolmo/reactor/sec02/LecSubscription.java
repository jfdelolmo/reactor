package org.github.jfdelolmo.reactor.sec02;

import org.github.jfdelolmo.reactor.common.Common;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicReference;

public class LecSubscription {

    public static void main(String[] args) {
        customSubscriber();
    }

    private static void customSubscriber() {
        AtomicReference<Subscription> atomicReference = new AtomicReference<>();
        Flux.range(1, 20)
                .log()
                .subscribeWith(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        System.out.println("Received sub: " + subscription);
                        atomicReference.set(subscription);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.err.println("onError: " + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });

        Common.sleepSeconds(3);
        atomicReference.get().request(3);
        Common.sleepSeconds(5);
        atomicReference.get().request(3);
        Common.sleepSeconds(5);
        System.out.println("I'm going to cancel");
        atomicReference.get().cancel();
        //After canceling no more results will be returned
        Common.sleepSeconds(3);
        atomicReference.get().request(3);
        Common.sleepSeconds(3);
    }

}

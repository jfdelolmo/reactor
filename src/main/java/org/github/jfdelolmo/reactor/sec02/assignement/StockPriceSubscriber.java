package org.github.jfdelolmo.reactor.sec02.assignement;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

import static org.github.jfdelolmo.reactor.sec02.assignement.StockPricePublisher.STARTING_PRICE;

public class StockPriceSubscriber {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        StockPricePublisher.getPrice()
                .subscribeWith(new Subscriber<Integer>() {

                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        System.out.println("onSubscribe: " + subscription);
                        this.subscription = subscription;
                        this.subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer price) {
                        System.out.println(LocalDateTime.now() + " Price: "+ price);
                        if (price > (STARTING_PRICE + 10) || price < (STARTING_PRICE - 10)) {
                            this.subscription.cancel();
                            latch.countDown();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.err.println("onError: " + throwable.getMessage());
                        latch.countDown();;
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
        latch.await();
    }
}

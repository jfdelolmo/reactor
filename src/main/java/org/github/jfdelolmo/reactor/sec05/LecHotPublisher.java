package org.github.jfdelolmo.reactor.sec05;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LecHotPublisher {

    public static void main(String[] args) {
        //shareAspublishRefCount();
        //publishRefCount();
        publishRefCount2();
    }

    private static void shareAspublishRefCount(){
        //Share == publish().refCount(1);
        final Flux<String> movieStream = Flux.fromStream(LecHotPublisher::getMovie)
                .delayElements(Duration.ofSeconds(2))
                .publish()
                .refCount(1);

        movieStream
                .subscribe(Common.subscriber("Sam"));

        Common.sleepSeconds(5);

        movieStream
                .subscribe(Common.subscriber("Mike"));

        Common.sleepSeconds(60);
    }

    private static void publishRefCount() {
        final Flux<String> movieStream = Flux.fromStream(LecHotPublisher::getMovie)
                .delayElements(Duration.ofSeconds(2))
                .publish()
                .refCount(2);

        movieStream
                .subscribe(Common.subscriber("Sam"));

        Common.sleepSeconds(5);

        movieStream
                .subscribe(Common.subscriber("Mike"));

        Common.sleepSeconds(60);
    }

    private static void publishRefCount2() {
        final Flux<String> movieStream = Flux.fromStream(LecHotPublisher::getMovie)
                .delayElements(Duration.ofSeconds(1))
                .publish()
                .refCount(1);

        movieStream
                .subscribe(Common.subscriber("Sam"));

        Common.sleepSeconds(10);

        movieStream
                .subscribe(Common.subscriber("Mike"));

        Common.sleepSeconds(60);
    }

    //Movie theatre
    private static Stream<String> getMovie(){
        System.out.println("Got the movie streaming req");
        return IntStream.range(1, 8).mapToObj(i->"Scene " + i);
    }
}

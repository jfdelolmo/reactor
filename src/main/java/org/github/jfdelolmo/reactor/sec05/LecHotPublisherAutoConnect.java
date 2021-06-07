package org.github.jfdelolmo.reactor.sec05;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LecHotPublisherAutoConnect {

    public static void main(String[] args) {
        publishAutoconnect();
    }

    private static void publishAutoconnect() {
        final Flux<String> movieStream = Flux.fromStream(LecHotPublisherAutoConnect::getMovie)
                .delayElements(Duration.ofSeconds(1))
                .publish()
                .autoConnect(0);

        Common.sleepSeconds(3);

        movieStream
                .subscribe(Common.subscriber("Sam"));

        Common.sleepSeconds(10);

        System.out.println("Mike is going to join");

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

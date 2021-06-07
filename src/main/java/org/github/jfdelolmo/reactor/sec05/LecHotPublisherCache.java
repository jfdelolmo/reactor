package org.github.jfdelolmo.reactor.sec05;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LecHotPublisherCache {

    public static void main(String[] args) {
        publishAutoconnect();
    }

    private static void publishAutoconnect() {
        // cache == publish().replay()
        final Flux<String> movieStream = Flux.fromStream(LecHotPublisherCache::getMovie)
                .delayElements(Duration.ofSeconds(1))
                .cache(2);

        Common.sleepSeconds(2);

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

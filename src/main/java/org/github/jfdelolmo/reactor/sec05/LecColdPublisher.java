package org.github.jfdelolmo.reactor.sec05;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LecColdPublisher {

    public static void main(String[] args) {

        final Flux<String> movieStream = Flux.fromStream(LecColdPublisher::getMovie)
                .delayElements(Duration.ofSeconds(2));

        movieStream
                .subscribe(Common.subscriber("Sam"));

        Common.sleepSeconds(5);

        movieStream
                .subscribe(Common.subscriber("Mike"));

        Common.sleepSeconds(60);
    }

    //netflix
    private static Stream<String> getMovie(){
        System.out.println("Got the movie streaming req");
        return IntStream.range(1, 8).mapToObj(i->"Scene " + i);
    }

}

package org.github.jfdelolmo.reactor.sec04;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class LecDelay {

    public static void main(String[] args) {
        System.setProperty("reactor.bufferSize.x", "9"); //Property from Queues class

        Flux.range(1,100)
                .log()
                .delayElements(Duration.ofSeconds(1))
                .subscribe(Common.subscriber());

        Common.sleepSeconds(10);
    }
}

package org.github.jfdelolmo.reactor.sec08.helper;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Qatar {

    public static Flux<String> generateFlights(){
        return Flux.range(1, Common.faker().random().nextInt(1, 5))
                .delayElements(Duration.ofSeconds(1))
                .map(i-> "Qatar" + Common.faker().random().nextInt(100, 999))
                .filter(f -> Common.faker().random().nextBoolean());
    }
}

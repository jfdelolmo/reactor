package org.github.jfdelolmo.reactor.sec02;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class LecFluxInterval {

    public static void main(String[] args) {
        Flux.interval(Duration.ofSeconds(1))
                .subscribe(Common.onNext());

        Common.sleepSeconds(10);
    }
}

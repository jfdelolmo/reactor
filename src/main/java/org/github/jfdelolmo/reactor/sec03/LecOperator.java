package org.github.jfdelolmo.reactor.sec03;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class LecOperator {

    public static void main(String[] args) {
        Flux.range(1,10)
                .log()
                .take(2)
                .log()
                .subscribe(Common.subscriber());
        Flux.range(1,Integer.MAX_VALUE)
                .takeUntil(i->i>69)
                .subscribe(Common.subscriber());
    }
}

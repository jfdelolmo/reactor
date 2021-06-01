package org.github.jfdelolmo.reactor.sec04;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

public class LecLimitRate {

    public static void main(String[] args) {
        Flux.range(1, 1000)
                .log() //Check how every 75% of the limitRate value a request signal is logged
                .limitRate(100)
                .subscribe(Common.subscriber());  //Subscribe is executed in a different thread than the definition
    }
}

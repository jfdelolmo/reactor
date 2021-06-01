package org.github.jfdelolmo.reactor.sec03;

import org.github.jfdelolmo.reactor.common.Common;
import org.github.jfdelolmo.reactor.sec03.helper.NameProducer;
import reactor.core.publisher.Flux;

public class LecFluxPush {
    public static void main(String[] args) {
        NameProducer nameProducer = new NameProducer();
        Flux.push(nameProducer) //No thread safe, only for a single thread producer
                .subscribe(Common.subscriber());

        Runnable runnable = nameProducer::produce;

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }

        Common.sleepSeconds(2);
    }
}

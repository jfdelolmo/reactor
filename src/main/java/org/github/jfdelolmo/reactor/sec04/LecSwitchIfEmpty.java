package org.github.jfdelolmo.reactor.sec04;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

public class LecSwitchIfEmpty {
    public static void main(String[] args) {
        getFromCache()
                .filter(i -> i > 10)
                .switchIfEmpty(getFromDataBase())
                .subscribe(Common.subscriber());
    }

    private static Flux<Integer> getFromCache() {
        return Flux.range(1, 10);
    }

    private static Flux<Integer> getFromDataBase() {
        return Flux.range(20, 5);
    }

}



package org.github.jfdelolmo.reactor.sec08.helper;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class pokemonGenerator {

    private static List<String> list = new ArrayList<>();

    public static Flux<String> pokemonGenerator(){
        return Flux.generate(syncSink -> {
            System.out.println("Fresh generated");
            Common.sleepMillis(500);
            String pokemon = Common.faker().pokemon().name();
            list.add(pokemon);
            syncSink.next(pokemon);
        })
                .cast(String.class)
                .startWith(getCached());
    }

    private static Flux<String> getCached(){
        return Flux.fromIterable(list);
    }

}

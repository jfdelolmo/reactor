package org.github.jfdelolmo.reactor.sec02.helper;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class NameGenerator {

    public static List<String> getNames(int count){
        List<String> names = new ArrayList<>();
        for(int i=0; i<count; i++){
            names.add(getName());
        }
        return names;
    }

    public static Flux<String> getNamesFlux(int count){
        return Flux.range(1, count)
                .map(i->getName());
    }

    private static String getName(){
        Common.sleepSeconds(1);
        return Common.faker().name().fullName();
    }
}

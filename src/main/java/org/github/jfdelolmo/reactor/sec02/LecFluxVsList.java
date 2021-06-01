package org.github.jfdelolmo.reactor.sec02;

import org.github.jfdelolmo.reactor.common.Common;
import org.github.jfdelolmo.reactor.sec02.helper.NameGenerator;

import java.util.List;

public class LecFluxVsList {

    public static void main(String[] args) {
        List<String> names = NameGenerator.getNames(5);
        System.out.println(names);

        NameGenerator.getNamesFlux(5)
                .subscribe(Common.onNext());
    }
}

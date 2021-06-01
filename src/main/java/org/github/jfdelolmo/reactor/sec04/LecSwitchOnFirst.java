package org.github.jfdelolmo.reactor.sec04;

import org.github.jfdelolmo.reactor.common.Common;
import org.github.jfdelolmo.reactor.sec04.helper.Person;
import reactor.core.publisher.Flux;

import java.util.function.Function;

public class LecSwitchOnFirst {

    public static void main(String[] args) {
        getPerson()
                .switchOnFirst((signal,personFlux)-> {
                    return signal.isOnNext() && signal.get().getAge()>30 ?
                            personFlux :
                            applyFilterMap().apply(personFlux);
                } )
                .subscribe(Common.subscriber());
    }

    public static Flux<Person> getPerson(){
        return Flux.range(1,10)
                .map(i->new Person());
    }

    public static Function<Flux<Person>, Flux<Person>> applyFilterMap(){
        return flux -> flux.filter(p -> p.getAge() > 45)
                .doOnNext(p -> p.setName(p.getName().toUpperCase()))
                .doOnDiscard(Person.class, p -> System.out.println("Not allowed: " + p));


    }

}

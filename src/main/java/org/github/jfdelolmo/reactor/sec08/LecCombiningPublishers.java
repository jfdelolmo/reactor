package org.github.jfdelolmo.reactor.sec08;

import org.github.jfdelolmo.reactor.common.Common;
import org.github.jfdelolmo.reactor.sec08.helper.American;
import org.github.jfdelolmo.reactor.sec08.helper.Emirates;
import org.github.jfdelolmo.reactor.sec08.helper.Qatar;
import org.github.jfdelolmo.reactor.sec08.helper.pokemonGenerator;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class LecCombiningPublishers {

    public static void main(String[] args) {
        //combineStartsWith();
        //combineConcat();
        //combineMerged();
        //combineZip();
        combineLatest();
    }

    private static void combineStartsWith(){
        pokemonGenerator.pokemonGenerator()
                .take(2)
                .subscribe(Common.subscriber("First"));
        pokemonGenerator.pokemonGenerator()
                .take(2)
                .subscribe(Common.subscriber("Second"));
        pokemonGenerator.pokemonGenerator()
                .take(3)
                .subscribe(Common.subscriber("Third"));
        pokemonGenerator.pokemonGenerator()
                .filter("pikachu"::equalsIgnoreCase)
                .take(1)
                .subscribe(Common.subscriber("Fourth"));
    }

    private static void combineConcat(){
        final Flux<String> flux1 = Flux.just("a", "b");
        final Flux<String> flux2 = Flux.just("c", "d", "e");

        final Flux<String> concatenated1 = flux1.concatWith(flux2);
        concatenated1.subscribe(Common.subscriber("concatenated1"));

        final Flux<String> concatenated2 = Flux.concat(flux1, flux2);
        concatenated2.subscribe(Common.subscriber("concatenated2"));

        final Flux<String> flux_error = Flux.error(new RuntimeException("oooops"));

        final Flux<String> concatenatedInterrupted = Flux.concat(flux1, flux_error, flux2);
        concatenatedInterrupted.subscribe(Common.subscriber("concatenatedInterruptedº"));

        Common.sleepMillis(500);

        final Flux<String> concatenatedDelayed = Flux.concatDelayError(flux1, flux_error, flux2);
        concatenatedDelayed.subscribe(Common.subscriber("concatenatedDelayed"));

    }

    private static void combineMerged(){
        final Flux<String> merged = Flux.merge(
                Qatar.generateFlights(),
                Emirates.generateFlights(),
                American.generateFlights());

        merged.subscribe(Common.subscriber("Consumer"));

        Common.sleepSeconds(10);
    }

    private static void combineZip(){
        Flux.zip(getCarBody(), getCarEngine(), getCarTyres())
                .subscribe(Common.subscriber("combinedZip"));
    }

    private static Flux<String> getCarBody(){
        return Flux.range(1, 5)
                .map(i->"CarBody"+i)
                .filter(f->Common.faker().random().nextBoolean());
    }

    private static Flux<String> getCarEngine(){
        return Flux.range(1, 2)
                .map(i->"CarEngine"+i)
                .filter(f->Common.faker().random().nextBoolean());
    }

    private static Flux<String> getCarTyres(){
        return Flux.range(1, 20)
                .map(i->"CarTyres"+i)
                .filter(f->Common.faker().random().nextBoolean());
    }

    private static void combineLatest(){
        Flux.combineLatest(getString(), getNumber(), (s,n)->s + n)
                .subscribe(Common.subscriber("CombineLatest"));

        Common.sleepSeconds(15);
    }

    private static Flux<String> getString(){
        return Flux.just("A","B","C","D")
                .delayElements(Duration.ofSeconds(1));
    }

    private static Flux<Integer> getNumber(){
        return Flux.just(1,2,3)
                .delayElements(Duration.ofSeconds(3));
    }

}

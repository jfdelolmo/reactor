package org.github.jfdelolmo.reactor.test;

import org.github.jfdelolmo.reactor.sec09.helper.BookOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

import java.time.Duration;

public class LecStepVerifierDemoTest {

    @Test
    public void simpleStepVerifier() {
        final Flux<Integer> just = Flux.just(1, 2, 3);

        StepVerifier.create(just)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .verifyComplete();
    }

    @Test
    public void simpleStepVerifier2() {
        final Flux<Integer> just = Flux.just(1, 2, 3);

        StepVerifier.create(just)
                .expectNext(1, 2, 3)
                .verifyComplete();
    }

    @Test
    public void errorStepVerifier() {
        final Flux<Integer> just = Flux.just(1, 2, 3);
        final Flux<Integer> error = Flux.error(new RuntimeException("oops"));
        final Flux<Integer> concat = Flux.concat(just, error);

        StepVerifier.create(concat)
                .expectNext(1, 2, 3)
                .verifyErrorMessage("oops");

        StepVerifier.create(concat)
                .expectNext(1, 2, 3)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void rangeStepVerifier() {
        final Flux<Integer> just = Flux.range(1, 50);

        StepVerifier.create(just)
                .expectNextCount(50)
                .verifyComplete();
    }

    @Test
    public void rangeStepVerifier2() {
        final Flux<Integer> just = Flux.range(1, 50);

        StepVerifier.create(just)
                .thenConsumeWhile(i -> i < 100)
                .verifyComplete();
    }

    @Test
    public void delayStepVerifier() {
        final Mono<BookOrder> mono = Mono.fromSupplier(BookOrder::new).delayElement(Duration.ofSeconds(3));

        StepVerifier.create(mono)
                .assertNext(b -> Assertions.assertNotNull(b.getAuthor()))
                .expectComplete()
                .verify(Duration.ofSeconds(4));
    }

    private Flux<String> timeConsumingFlux() {
        return Flux.range(1, 4)
                .delayElements(Duration.ofSeconds(5))
                .map(i -> i + "a");
    }

    @Test
    public void virtualTimeStepVerifier() {
        //This will take a lot of time to be compelted
        //StepVerifier.create(timeConsumingFlux())
        //      .expectNext("1a", "2a", "3a", "4a")
        //      .verifyComplete();

        StepVerifier.withVirtualTime(this::timeConsumingFlux)
                .thenAwait(Duration.ofSeconds(30))
                .expectNext("1a", "2a", "3a", "4a")
                .verifyComplete();
    }

    @Test
    public void virtualTimeStepVerifier2() {

        StepVerifier.withVirtualTime(this::timeConsumingFlux)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(4))
                .thenAwait(Duration.ofSeconds(20))
                .expectNext("1a", "2a", "3a", "4a")
                .verifyComplete();
    }

    @Test
    public void scenarioNameTest(){
        final Flux<String> flux = Flux.just("a", "b", "c");

        //Useful when the test fails, showing the scenario name
        final StepVerifierOptions scenarioName = StepVerifierOptions.create().scenarioName("Alphabet test");

        /*
        StepVerifier.create(flux, scenarioName)
                .expectNextCount(12)
                .verifyComplete();
        //java.lang.AssertionError: [Alphabet test] expectation "expectNextCount(12)" failed (expected: count = 12; actual: counted = 3; signal: onComplete())
        */

        StepVerifier.create(flux, scenarioName)
                .expectNextCount(3)
                .verifyComplete();
    }


    @Test
    public void scenarioNameTest2(){
        //final Flux<String> flux = Flux.just("a", "b", "c1");
        //java.lang.AssertionError: expectation "c test" failed (expected value: c; actual value: c1)

        final Flux<String> flux = Flux.just("a", "b", "c");

        StepVerifier.create(flux)
                .expectNext("a")
                .as("a test")
                .expectNext("b")
                .as("b test")
                .expectNext("c")
                .as("c test")
                .verifyComplete();
    }

    private Mono<String> getWelcomeMessage(){
        return Mono.deferContextual(ctx -> {
            if (ctx.hasKey("user")){
                return Mono.just("Welcome " + ctx.get("user"));
            }else{
                return Mono.error(new RuntimeException("Unauthenticated"));
            }
        });
    }

    @Test
    public void contextStepVerifier(){
        StepVerifier.create(getWelcomeMessage())
                .verifyError();

        final StepVerifierOptions options = StepVerifierOptions.create().withInitialContext(Context.of("user", "sam"));
        StepVerifier.create(getWelcomeMessage(), options)
                .expectNext("Welcome sam")
                .verifyComplete();
    }

}

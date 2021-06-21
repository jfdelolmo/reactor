package org.github.jfdelolmo.reactor.sec12;

import org.github.jfdelolmo.reactor.common.Common;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class LecContext {

    public static void main(String[] args) {
        //simpleContext();
        //simpleContext2();
        updateContext();
    }

    private static void simpleContext() {
        getWelcomeMessage()
                .contextWrite(Context.of("user", "Jordi"))
                .subscribe(Common.subscriber());
    }

    private static void simpleContext2() {
        getWelcomeMessage()
                .contextWrite(Context.of("user", "Jake"))
                .contextWrite(Context.of("user", "Jordi"))
                .subscribe(Common.subscriber());
    }

    private static void updateContext() {
        getWelcomeMessage()
                .contextWrite(ctx -> ctx.put("user", ctx.get("user").toString().toUpperCase()))
                .contextWrite(Context.of("user", "sam"))
                .subscribe(Common.subscriber());
    }

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(ctx -> {
            if (ctx.hasKey("user")) {
                return Mono.just("Welcome " + ctx.get("user"));
            } else {
                return Mono.error(new RuntimeException(("Unauthenticated")));
            }
        });
    }

}

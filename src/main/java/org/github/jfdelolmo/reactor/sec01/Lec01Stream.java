package org.github.jfdelolmo.reactor.sec01;

import java.util.stream.Stream;

public class Lec01Stream {

    public static void main(String[] args) {

        final Stream<Integer> integerStream = Stream.of(1)
                .map(integer -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return integer * 2;
                });

        integerStream.forEach(System.out::println);
    }
}

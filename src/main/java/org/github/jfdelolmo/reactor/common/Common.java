package org.github.jfdelolmo.reactor.common;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;

import java.util.function.Consumer;

public class Common {

    private static final Faker FAKER =Faker.instance();

    public static Consumer<Object> onNext(){
        return o -> System.out.println("Recevied: " + o);
    }

    public static Consumer<Throwable> onError(){
        return err -> System.err.println("Error: " + err.getMessage());
    }

    public static Runnable onComplete(){
        return () -> System.out.println("Completed");
    }

    public static Faker faker(){
        return FAKER;
    }

    public static void sleepSeconds(int seconds) {
        try {
            Thread.sleep(1000*seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Subscriber<Object> subscriber(){
        return new DefaultSubscriber();
    }

    public static Subscriber<Object> subscriber(String name){
        return new DefaultSubscriber(name);
    }
}

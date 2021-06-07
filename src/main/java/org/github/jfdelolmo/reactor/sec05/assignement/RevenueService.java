package org.github.jfdelolmo.reactor.sec05.assignement;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RevenueService {

    private Map<String, Double> db = new HashMap<>();

    public RevenueService(){
        //db.put("Light Lager", 0.0);
        db.put("Pilsner", 0.0);
        //db.put("European Amber Lager", 0.0);
        //db.put("Dark Lager", 0.0);
        db.put("Bock", 0.0);
        //db.put("Light Hybrid Beer", 0.0);
        //db.put("Amber Hybrid Beer", 0.0);
        //db.put("English Pale Ale", 0.0);
        //db.put("Scottish And Irish Ale", 0.0);
        //db.put("Merican Ale", 0.0);
        //db.put("English Brown Ale", 0.0);
        //db.put("Porter",       0.0);
        //db.put("Stout", 0.0);
        //db.put("India Pale Ale", 0.0);
        //db.put("German Wheat And Rye Beer", 0.0);
        //db.put("Belgian And French Ale", 0.0);
        //db.put("Sour Ale", 0.0);
        //db.put("Belgian Strong Ale", 0.0);
        //db.put("Strong Ale", 0.0);
        //db.put("Fruit Beer", 0.0);
        //db.put("Vegetable Beer", 0.0);
        //db.put("Smoke-flavored", 0.0);
        //db.put("Wood-aged Beer", 0.0);
    }

    public Consumer<PurchaseOrder>subscribeOrderStream(){
        return p -> db.computeIfPresent(
                p.getCategory(),
                (k,v) -> v + p.getPrice());
    }

    public Flux<String> revenueStream(){
        return Flux.interval(Duration.ofSeconds(2))
                .map(i -> db.toString());
    }
}

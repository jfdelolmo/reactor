package org.github.jfdelolmo.reactor.sec05.assignement;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class InventoryService {

    private Map<String, Integer> db = new HashMap<>();

    public InventoryService() {
        //db.put("Light Lager", 100);
        db.put("Pilsner", 100);
        //db.put("European Amber Lager", 100);
        //db.put("Dark Lager", 100);
        db.put("Bock", 100);
        //db.put("Light Hybrid Beer", 100);
        //db.put("Amber Hybrid Beer", 100);
        //db.put("English Pale Ale", 100);
        //db.put("Scottish And Irish Ale", 100);
        //db.put("Merican Ale", 100);
        //db.put("English Brown Ale", 100);
        //db.put("Porter", 100);
        //db.put("Stout", 100);
        //db.put("India Pale Ale", 100);
        //db.put("German Wheat And Rye Beer", 100);
        //db.put("Belgian And French Ale", 100);
        //db.put("Sour Ale", 100);
        //db.put("Belgian Strong Ale", 100);
        //db.put("Strong Ale", 100);
        //db.put("Fruit Beer", 100);
        //db.put("Vegetable Beer", 100);
        //db.put("Smoke-flavored", 100);
        //db.put("Wood-aged Beer", 100);
    }

    public Consumer<PurchaseOrder> subscribeOrderStream() {
        return p -> db.computeIfPresent(
                p.getCategory(),
                (k, v) -> v - p.getQuantity());
    }

    public Flux<String> inventoryStream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(i -> db.toString());
    }

}

package org.github.jfdelolmo.reactor.sec05.assignement;

import lombok.Data;
import lombok.ToString;
import org.github.jfdelolmo.reactor.common.Common;

@Data
@ToString
public class PurchaseOrder {

    private String item;
    private double price;
    private String category;
    private int quantity;

    public PurchaseOrder() {
        this.price = Double.parseDouble(Common.faker().commerce().price().replace(',','.'));
        this.category = Common.faker().beer().style();
        this.item = Common.faker().beer().name();
        this.quantity = Common.faker().random().nextInt(1, 10);
    }
}

package org.github.jfdelolmo.reactor.sec04.helper;

import lombok.Data;
import lombok.ToString;
import org.github.jfdelolmo.reactor.common.Common;

@Data
@ToString
public class PurchaseOrder {

    private String item;
    private String price;
    private int userId;

    public PurchaseOrder(int userId) {
        this.userId = userId;
        this.price = Common.faker().commerce().price();
        this.item = Common.faker().beer().name();
    }
}

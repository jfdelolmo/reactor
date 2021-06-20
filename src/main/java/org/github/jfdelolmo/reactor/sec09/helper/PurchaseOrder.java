package org.github.jfdelolmo.reactor.sec09.helper;

import jdk.jshell.execution.Util;
import lombok.Data;
import lombok.ToString;
import org.github.jfdelolmo.reactor.common.Common;

@Data
@ToString
public class PurchaseOrder {

    private String item;
    private double price;
    private String category;

    public PurchaseOrder(){
        this.item = Common.faker().commerce().productName();
        this.price = Double.parseDouble(Common.faker().commerce().price().replace(",", "."));
        this.category = Common.faker().commerce().department();
    }

}

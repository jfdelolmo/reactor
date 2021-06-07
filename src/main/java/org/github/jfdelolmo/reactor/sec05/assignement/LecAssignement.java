package org.github.jfdelolmo.reactor.sec05.assignement;

import org.github.jfdelolmo.reactor.common.Common;

public class LecAssignement {

    public static void main(String[] args) {

        OrderService orderService = new OrderService();
        RevenueService revenueService = new RevenueService();
        InventoryService inventoryService = new InventoryService();

        // revenue and inventory - observe the order stream
        orderService.orderStream().subscribe(revenueService.subscribeOrderStream());
        orderService.orderStream().subscribe(inventoryService.subscribeOrderStream());

        inventoryService.inventoryStream()
                .subscribe(Common.subscriber("inventory"));

        revenueService.revenueStream()
                .subscribe(Common.subscriber("revenue"));

        Common.sleepSeconds(50);
    }
}

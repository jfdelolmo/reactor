package org.github.jfdelolmo.reactor.sec04;

import org.github.jfdelolmo.reactor.common.Common;
import org.github.jfdelolmo.reactor.sec04.helper.OrderService;
import org.github.jfdelolmo.reactor.sec04.helper.UserService;

public class LecFlatMap {

    public static void main(String[] args) {

        UserService.getUsers()
                .flatMap(user -> OrderService.getOrders(user.getUserId()))
                .subscribe(Common.subscriber());

//        UserService.getUsers()
//                .concatMap(user -> OrderService.getOrders(user.getUserId()))
//                .subscribe(Common.subscriber());

        Common.sleepSeconds(60);

    }


}

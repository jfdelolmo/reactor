package org.github.jfdelolmo.reactor.sec12;

import org.github.jfdelolmo.reactor.common.Common;
import org.github.jfdelolmo.reactor.sec12.helper.BookService;
import org.github.jfdelolmo.reactor.sec12.helper.UserService;
import reactor.util.context.Context;

public class LecCtxRateLimiterDemo {
    public static void main(String[] args) {
        BookService.getBook().subscribe(Common.subscriber());

        System.out.println("--------------------------------");
        BookService
                .getBook()
                .repeat(2)
                .contextWrite(UserService.userCategoryContext())
                .contextWrite(Context.of("user", "sam"))
                .subscribe(Common.subscriber());

        Common.sleepSeconds(1);

        System.out.println("--------------------------------");
        BookService
                .getBook()
                .repeat(2)
                .contextWrite(UserService.userCategoryContext())
                .contextWrite(Context.of("user", "mike"))
                .subscribe(Common.subscriber());

    }
}

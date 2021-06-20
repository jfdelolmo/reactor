package org.github.jfdelolmo.reactor.sec09;

import org.github.jfdelolmo.reactor.common.Common;
import org.github.jfdelolmo.reactor.sec09.helper.BookOrder;
import org.github.jfdelolmo.reactor.sec09.helper.RevenueReport;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LecAssignmentBuffer {

    public static void main(String[] args) {
        Set<String> allowedCategories = Set.of(
                "Science fiction",
                "Fantasy",
                "Suspense/Thriller"
        );

        bookStrem()
                .filter(book -> allowedCategories.contains(book.getCategory()))
                .buffer(Duration.ofSeconds(5))
                .map(list -> revenueCalculator(list))
                .subscribe(Common.subscriber());

        Common.sleepSeconds(60);
    }

    private static Flux<BookOrder> bookStrem() {
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> new BookOrder());

    }

    private static RevenueReport revenueCalculator(List<BookOrder> books) {
        Map<String, Double> map = books.stream()
                .collect(Collectors.groupingBy(BookOrder::getCategory, Collectors.summingDouble(BookOrder::getPrice)));
        return new RevenueReport(map);
    }
}

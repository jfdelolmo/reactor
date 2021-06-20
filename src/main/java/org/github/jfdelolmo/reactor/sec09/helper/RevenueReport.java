package org.github.jfdelolmo.reactor.sec09.helper;

import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@ToString
public class RevenueReport {

    private LocalDateTime localDateTime = LocalDateTime.now();
    private Map<String, Double> revenue;

    public RevenueReport(Map<String, Double> revenue){
        this.revenue = revenue;
    }
}

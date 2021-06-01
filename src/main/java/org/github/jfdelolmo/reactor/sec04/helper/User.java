package org.github.jfdelolmo.reactor.sec04.helper;

import lombok.Data;
import lombok.ToString;
import org.github.jfdelolmo.reactor.common.Common;

@Data
@ToString
public class User {

    private int userId;
    private String name;

    public User(int userId) {
        this.userId = userId;
        this.name = Common.faker().name().fullName();
    }
}

package org.github.jfdelolmo.reactor.sec04.helper;

import lombok.Data;
import org.github.jfdelolmo.reactor.common.Common;

@Data
public class Person {

    private String name;
    private int age;

    public Person() {
        this.name = Common.faker().name().fullName();
        this.age = Common.faker().random().nextInt(10,100);
    }

    @Override
    public String toString() {
        return name + " is " + age + " years old";
    }
}

package org.github.jfdelolmo.reactor.sec11.assignment;

import lombok.Data;

import java.util.function.Consumer;

@Data
public class ChatUser {

    private String name;
    private Consumer<String> messageConsumer;

    public ChatUser(String name) {
        this.name = name;
    }

    void setMessageConsumer(Consumer<String> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    void receiveMsg(String message){
        System.out.println(message);
    }

    String getName(){
        return this.name;
    }

    public void postMsg(String message){
        this.messageConsumer.accept(message);
    }


}

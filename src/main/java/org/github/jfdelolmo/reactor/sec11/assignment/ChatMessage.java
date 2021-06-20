package org.github.jfdelolmo.reactor.sec11.assignment;

import lombok.Data;

@Data
public class ChatMessage {

    private static final String FORMAT = "[%s -> %s] : %s";

    private String msg;
    private String sender;
    private String receiver;

    @Override
    public String toString(){
        return String.format(FORMAT, this.sender, this.receiver, this.msg);
    }

}

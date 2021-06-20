package org.github.jfdelolmo.reactor.sec11.assignment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class ChatRoom {

    private String name;
    private Sinks.Many<ChatMessage> sink;
    private Flux<ChatMessage> flux;

    public ChatRoom(String name){
        this.name = name;
        this.sink = Sinks.many().replay().all();
        this.flux = this.sink.asFlux();
    }

    public void joinRoom(ChatUser chatUser){
        System.out.println(chatUser.getName() + " joined the room: " + this.name);
        this.subscribe(chatUser);
        chatUser.setMessageConsumer(msg -> this.postMsg(msg, chatUser));
    }

    private void subscribe(ChatUser chatUser){
        this.flux
                .filter(cu -> !chatUser.getName().equals(cu.getSender()))
                .doOnNext(sm -> sm.setReceiver(chatUser.getName()))
                .map(ChatMessage::toString)
                .subscribe(chatUser::receiveMsg);
    }

    private void postMsg(String msg, ChatUser chatUser){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMsg(msg);
        chatMessage.setSender(chatUser.getName());
        this.sink.tryEmitNext(chatMessage);
    }
}

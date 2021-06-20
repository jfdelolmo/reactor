package org.github.jfdelolmo.reactor.sec11.assignment;

import org.github.jfdelolmo.reactor.common.Common;

public class ChatAssignment {

    public static void main(String[] args) {
        ChatRoom chatRoom = new ChatRoom(Common.faker().harryPotter().house());

        ChatUser chatUser1 = new ChatUser(Common.faker().harryPotter().character());
        ChatUser chatUser2 = new ChatUser(Common.faker().harryPotter().character());
        ChatUser chatUser3 = new ChatUser(Common.faker().harryPotter().character());

        chatRoom.joinRoom(chatUser1);
        chatRoom.joinRoom(chatUser2);
        chatRoom.joinRoom(chatUser3);

        chatUser1.postMsg(Common.faker().harryPotter().spell());
        Common.sleepSeconds(1);
        chatUser2.postMsg(Common.faker().harryPotter().spell());
        Common.sleepSeconds(1);
        chatUser3.postMsg(Common.faker().harryPotter().spell());
        Common.sleepSeconds(1);
    }
}

package com.ducnv1106.message.model;

public class Friend {

    private String sender;
    private String receiver;
    private boolean friend;

    public Friend(String sender, String receiver, Boolean friend) {
        this.sender = sender;
        this.receiver = receiver;
        this.friend = friend;
    }

    public Friend() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }
}

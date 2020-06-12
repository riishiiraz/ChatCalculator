package com.riishiiraz.chatcalculator;

public class Messege {

    private int id;
    private String text;
    private String sender;
    private String time;

    public Messege(int id, String text, String sender ,String time) {
        this.id = id;
        this.text = text;
        this.sender = sender;
        this.time = time;
    }

    public Messege(String text, String sender ,String time) {
        this.text = text;
        this.sender = sender;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }

    public String getTime()
    {
        return time;
    }
}

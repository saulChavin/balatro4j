package com.balatro.structs;

public class Pack {
    private String type;
    private int size;
    private int choices;

    public Pack(String type, int size, int choices) {
        this.type = type;
        this.size = size;
        this.choices = choices;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getChoices() {
        return choices;
    }

    public void setChoices(int choices) {
        this.choices = choices;
    }
}
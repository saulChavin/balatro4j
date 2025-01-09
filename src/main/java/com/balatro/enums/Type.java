package com.balatro.enums;

public enum Type implements Named {
    Joker("Joker"),
    Tarot("Tarot"),
    Planet("Planet"),
    Spectral("Spectral"),
    PlayingCard("Playing Card");

    private final String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

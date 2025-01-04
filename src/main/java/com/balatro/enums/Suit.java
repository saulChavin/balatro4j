package com.balatro.enums;

public enum Suit implements Named {
    Spades("Spades"),
    Hearts("Hearts"),
    Clubs("Clubs"),
    Diamonds("Diamonds");

    private final String name;

    Suit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

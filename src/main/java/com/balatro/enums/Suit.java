package com.balatro.enums;

import com.balatro.api.Item;

public enum Suit implements Item {
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

package com.balatro.enums;

public enum Enhancement implements Named {
    Bonus("Bonus"),
    Mult("Mult"),
    Wild("Wild"),
    Glass("Glass"),
    Steel("Steel"),
    Stone("Stone"),
    Gold("Gold"),
    Luck("Lucky");

    private final String name;

    Enhancement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

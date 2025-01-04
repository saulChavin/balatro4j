package com.balatro.enums;

public enum LegendaryJoker implements Named {
    Canio("Canio"),
    Triboulet("Triboulet"),
    Yorick("Yorick"),
    Chicot("Chicot"),
    Perke("Perkeo");

    private final String name;

    LegendaryJoker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

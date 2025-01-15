package com.balatro.api;

public class AbstractCard implements Item{

    private final String name;

    public AbstractCard(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

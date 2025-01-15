package com.balatro.enums;

import com.balatro.api.Item;

public enum Specials implements Item {
    BLACKHOLE("Black Hole"),
    THE_SOUL("The Soul");

    private final String name;

    Specials(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}

package com.balatro.api;

import com.balatro.enums.Edition;

public record EditionItem(Item item, Edition edition) implements Item {
    @Override
    public String getName() {
        return item.getName();
    }

    @Override
    public int getYIndex() {
        return item.getYIndex();
    }

    @Override
    public int ordinal() {
        return item.ordinal();
    }
}

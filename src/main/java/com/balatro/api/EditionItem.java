package com.balatro.api;

import com.balatro.enums.Edition;

public record EditionItem(Item item, Edition edition) implements Item {
    @Override
    public String getName() {
        return item.getName();
    }
}

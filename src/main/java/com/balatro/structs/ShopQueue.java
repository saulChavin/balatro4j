package com.balatro.structs;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public final class ShopQueue extends ArrayList<SearchableItem> {

    public ShopQueue(@NotNull Collection<? extends SearchableItem> c) {
        super(c);
    }

    public ShopQueue() {
        super(20);
    }

}

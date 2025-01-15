package com.balatro.structs;

import com.balatro.api.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public final class ShopQueue extends ArrayList<SearchableItem> {

    public ShopQueue(@NotNull Collection<? extends SearchableItem> c) {
        super(c);
    }

    public ShopQueue() {
        super(20);
    }

    public boolean contains(Item named) {
        return this.stream().anyMatch(item -> item.equals(named));
    }
}

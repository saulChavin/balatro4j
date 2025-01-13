package com.balatro.structs;

import com.balatro.api.Named;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class ShopQueue extends ArrayList<SearchableItem> {

    public ShopQueue(@NotNull Collection<? extends SearchableItem> c) {
        super(c);
    }

    public ShopQueue() {
        super(20);
    }

    public boolean contains(Named named) {
        return this.stream().anyMatch(item -> item.hasSticker(named));
    }

    public boolean contains(Named named, @Nullable Named sticker) {
        return this.stream().anyMatch(item -> item.hasSticker(named) && item.hasSticker(sticker));
    }

}

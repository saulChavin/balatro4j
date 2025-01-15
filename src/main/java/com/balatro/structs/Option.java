package com.balatro.structs;

import com.balatro.api.Item;
import org.jetbrains.annotations.Nullable;

public record Option(@Nullable Item sticker, Item name) {

    public Option(Item name) {
        this(null, name);
    }

}

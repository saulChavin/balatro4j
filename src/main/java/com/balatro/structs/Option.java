package com.balatro.structs;

import com.balatro.api.Item;
import org.jetbrains.annotations.Nullable;

public record Option(@Nullable Item sticker, String name) {

    public Option(String name) {
        this(null, name);
    }

}

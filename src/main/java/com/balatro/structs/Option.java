package com.balatro.structs;

import com.balatro.api.Item;
import com.balatro.enums.Edition;
import org.jetbrains.annotations.Nullable;

public record Option(@Nullable Edition edition, Item item) {

    public Option(Item name) {
        this(null, name);
    }

}

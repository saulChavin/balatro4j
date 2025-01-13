package com.balatro.structs;

import com.balatro.api.Named;
import org.jetbrains.annotations.Nullable;

public record Option(@Nullable Named sticker, String name) {

    public Option(String name) {
        this(null, name);
    }

}

package com.balatro.structs;

import com.balatro.api.Named;
import org.jetbrains.annotations.Nullable;

public record SearchableItem(String item, @Nullable Named sticker) {

    public SearchableItem(String item, Named sticker) {
        this.item = item;
        this.sticker = sticker;
    }

    public boolean hasSticker() {
        return sticker != null;
    }

    public boolean hasSticker(Named named) {
        return sticker != null && sticker.equals(named);
    }
}
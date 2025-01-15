package com.balatro.structs;

import com.balatro.api.EditionItem;
import com.balatro.api.Item;
import com.balatro.enums.Edition;
import org.jetbrains.annotations.Nullable;

public record SearchableItem(Item item, @Nullable Item edition) {

    public SearchableItem(Item item, Item edition) {
        this.item = item;
        this.edition = edition;
    }

    public boolean hasSticker() {
        return edition != null;
    }

    public boolean hasEdition(Edition edition) {
        return this.edition != null && this.edition.eq(edition);
    }

    public boolean equals(Item item) {
        if (item instanceof EditionItem editionItem) {
            if (editionItem.edition() != null && edition != null) {
                return editionItem.eq(this.item) && edition.eq(editionItem.edition());
            }
        }

        return item.eq(this.item);
    }
}
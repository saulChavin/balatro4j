package com.balatro.structs;

import com.balatro.api.EditionItem;
import com.balatro.api.Item;
import com.balatro.enums.Edition;
import org.jetbrains.annotations.Nullable;

public record SearchableItem(String item, @Nullable Item edition) {

    public SearchableItem(String item, Item edition) {
        this.item = item;
        this.edition = edition;
    }

    public boolean hasSticker() {
        return edition != null;
    }

    public boolean hasEdition(Edition edition) {
        return this.edition != null && this.edition.equals(edition);
    }

    public boolean equals(Item item) {
        if (item instanceof EditionItem editionItem) {
            if (editionItem.edition() != null && edition != null) {
                return editionItem.getName().equals(this.item) && edition.getName().equals(editionItem.edition().getName());
            }
        }

        return item.getName().equals(this.item);
    }
}
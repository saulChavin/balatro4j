package com.balatro.structs;

import com.balatro.api.EditionItem;
import com.balatro.api.Item;
import com.balatro.enums.Edition;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.Nullable;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
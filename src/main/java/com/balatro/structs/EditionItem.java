package com.balatro.structs;

import com.balatro.api.Item;
import com.balatro.api.Joker;
import com.balatro.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EditionItem(Item item, @Nullable Edition edition) implements Item {

    public EditionItem(@NotNull Item item) {
        this(item, null);
    }

    @JsonIgnore
    public boolean isJoker() {
        return item instanceof Joker;
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

    @Contract(value = " -> new", pure = true)
    public @NotNull JokerData jokerData() {
        return new JokerData(item, 0, edition, null);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        EditionItem that = (EditionItem) object;
        return item.equals(that.item);
    }

    @Override
    public int hashCode() {
        return item.hashCode();
    }

    @Override
    public int ordinal() {
        return item.ordinal();
    }

    @JsonIgnore
    @Override
    public String getName() {
        return item.getName();
    }

    @Override
    public int getYIndex() {
        return item.getYIndex();
    }

    @Override
    public String toString() {
        return item.getName();
    }
}
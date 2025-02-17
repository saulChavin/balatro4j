package com.balatro.api;

import com.balatro.api.filter.InPackFilter;
import com.balatro.api.filter.InShopFilter;
import com.balatro.api.filter.SpectralFilter;
import com.balatro.enums.Edition;
import com.balatro.structs.EditionItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Item {

    String getName();

    @JsonIgnore
    int getYIndex();

    int ordinal();

    default boolean eq(Item item) {
        return getName().equalsIgnoreCase(item.getName());
    }

    default boolean equals(String value) {
        return getName().equals(value);
    }

    default Filter inPack(int ante) {
        return new InPackFilter(ante, this);
    }

    @JsonIgnore
    default Filter inPack() {
        return new InPackFilter(this);
    }

    @JsonIgnore
    default Filter inShop() {
        return new InShopFilter(this);
    }

    default Filter inShop(int ante) {
        return new InShopFilter(ante, this);
    }

    @JsonIgnore
    default Filter inSpectral() {
        return new SpectralFilter(this);
    }

    default Filter inSpectral(int ante) {
        return new SpectralFilter(ante, this);
    }

    default com.balatro.api.EditionItem edition(Edition edition) {
        return new com.balatro.api.EditionItem(this, edition);
    }

    @JsonIgnore
    default EditionItem asOption() {
        return new EditionItem(this);
    }
}

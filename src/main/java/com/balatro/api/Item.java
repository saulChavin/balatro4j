package com.balatro.api;

import com.balatro.api.filter.InPackFilter;
import com.balatro.api.filter.InShopFilter;
import com.balatro.api.filter.SpectralFilter;
import com.balatro.enums.Edition;

public interface Item {

    String getName();

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

    default Filter inPack() {
        return new InPackFilter(this);
    }

    default Filter inShop() {
        return new InShopFilter(this);
    }

    default Filter inShop(int ante) {
        return new InShopFilter(ante, this);
    }

    default Filter inSpectral() {
        return new SpectralFilter(this);
    }

    default Filter inSpectral(int ante) {
        return new SpectralFilter(ante, this);
    }

    default EditionItem edition(Edition edition) {
        return new EditionItem(this, edition);
    }

}

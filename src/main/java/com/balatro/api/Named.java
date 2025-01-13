package com.balatro.api;

public interface Named {

    String getName();

    default boolean equals(String value) {
        return getName().equals(value);
    }

    default Filter inPack(int ante) {
        return new InPackFilter(ante, this);
    }

    default Filter inShop(int ante) {
        return new InShopFilter(ante, this);
    }

}

package com.balatro.api;

import com.balatro.api.filter.InBuffonPackFilter;
import com.balatro.enums.JokerType;

public interface Joker extends Item {

    default Filter inBuffonPack() {
        return new InBuffonPackFilter(this);
    }

    default Filter inBuffonPack(int ante) {
        return new InBuffonPackFilter(ante, this);
    }

    JokerType getType();

    default boolean isRare() {
        return getType() == JokerType.RARE;
    }

    default boolean isCommon() {
        return getType() == JokerType.COMMON;
    }

    default boolean isUncommon() {
        return getType() == JokerType.UNCOMMON;
    }

    default boolean isLegendary() {
        return getType() == JokerType.LEGENDARY;
    }

}

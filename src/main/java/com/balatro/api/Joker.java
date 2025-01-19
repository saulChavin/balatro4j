package com.balatro.api;

import com.balatro.api.filter.InBuffonPackFilter;

public interface Joker extends Item {

    default Filter inBuffonPack() {
        return new InBuffonPackFilter(this);
    }

    default Filter inBuffonPack(int ante) {
        return new InBuffonPackFilter(ante, this);
    }
}

package com.balatro.api.filter;


import com.balatro.api.Filter;
import com.balatro.api.Item;
import com.balatro.api.Run;

public record InBuffonPackFilter(int ante, Item item) implements Filter {

    public InBuffonPackFilter(Item item) {
        this(-1, item);
    }

    @Override
    public boolean filter(Run run) {
        if (ante == -1) {
            return run.hasInBuffonPack(item);
        }

        return run.hasInBuffonPack(ante, item);
    }
}

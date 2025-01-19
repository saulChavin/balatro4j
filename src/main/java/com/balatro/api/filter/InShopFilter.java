package com.balatro.api.filter;

import com.balatro.api.Filter;
import com.balatro.api.Item;
import com.balatro.api.Run;

public record InShopFilter(int ante, Item item) implements Filter {


    public InShopFilter(Item item) {
        this(-1, item);
    }

    @Override
    public boolean filter(Run run) {
        if (ante == -1) return run.hasInShop(item);
        return run.hasInShop(ante, item);
    }
}

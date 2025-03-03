package com.balatro.api.filter;

import com.balatro.api.Filter;
import com.balatro.api.Item;
import com.balatro.api.Run;
import com.balatro.enums.Edition;

public record InPackFilter(int ante, Item item, Edition edition) implements Filter {

    public InPackFilter(Item item) {
        this(-1, item, Edition.NoEdition);
    }

    @Override
    public boolean filter(Run run) {
        if (ante == -1) return run.hasInPack(item, edition);
        return run.hasInPack(ante, item, edition);
    }
}

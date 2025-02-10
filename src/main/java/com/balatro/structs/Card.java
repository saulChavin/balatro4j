package com.balatro.structs;

import com.balatro.api.Item;
import com.balatro.enums.Edition;
import com.balatro.enums.Enhancement;
import com.balatro.enums.Seal;
import org.jetbrains.annotations.Nullable;

public record Card(com.balatro.enums.Card base, @Nullable Enhancement enhancement, Edition edition,
                   Seal seal) implements Item {

    @Override
    public String getName() {
        return base.getName();
    }

    @Override
    public int getYIndex() {
        return -1;
    }

    @Override
    public int ordinal() {
        return 0;
    }
}
package com.balatro.structs;

import com.balatro.api.Item;
import com.balatro.enums.Edition;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.jetbrains.annotations.Nullable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Option(@Nullable Edition edition, Item item) {

    public Option(Item name) {
        this(null, name);
    }

}

package com.balatro.cache;

import com.balatro.enums.Edition;
import org.jetbrains.annotations.Nullable;

public class Query {
    private String item;
    private @Nullable Edition edition;

    public Query() {
    }

    public Query(String item) {
        this.item = item;
    }

    public Query(String item, @Nullable Edition edition) {
        this.item = item;
        this.edition = edition;
    }

    public String getItem() {
        return item;
    }

    public Query setItem(String item) {
        this.item = item;
        return this;
    }

    public @Nullable Edition getEdition() {
        return edition;
    }

    public Query setEdition(Edition edition) {
        this.edition = edition;
        return this;
    }
}

package com.balatro.structs;

import com.balatro.enums.PackKind;
import com.balatro.enums.PackType;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PackInfo {
    private final PackType type;
    private final int size;
    private final int choices;
    private Set<EditionItem> options;

    public PackInfo(PackType type, int size, int choices) {
        this.type = type;
        this.size = size;
        this.choices = choices;
    }

    public Set<EditionItem> getOptions() {
        return options;
    }

    public void setOptions(Set<EditionItem> options) {
        this.options = options;
    }

    public PackType getType() {
        return type;
    }

    public PackKind getKind() {
        return type.getKind();
    }

    public boolean containsOption(String name) {
        for (EditionItem option : options) {
            if (option.item().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public int getSize() {
        return size;
    }

    public int getChoices() {
        return choices;
    }
}
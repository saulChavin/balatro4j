package com.balatro.structs;

import com.balatro.enums.PackKind;
import com.balatro.enums.PackType;

import java.util.Set;

public class PackInfo {
    private final PackType type;
    private final int size;
    private final int choices;
    private Set<Option> options;

    public PackInfo(PackType type, int size, int choices) {
        this.type = type;
        this.size = size;
        this.choices = choices;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    public PackType getType() {
        return type;
    }

    public PackKind getKind() {
        return type.getKind();
    }

    public boolean containsOption(String name) {
        for (Option option : options) {
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
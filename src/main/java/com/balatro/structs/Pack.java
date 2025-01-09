package com.balatro.structs;

import com.balatro.enums.PackKind;
import com.balatro.enums.PackType;

import java.util.Set;

public class Pack {
    private PackType type;
    private int size;
    private int choices;
    private Set<Option> options;

    public Pack(PackType type, int size, int choices) {
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
            if (option.name().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public void setType(PackType type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getChoices() {
        return choices;
    }

    public void setChoices(int choices) {
        this.choices = choices;
    }
}
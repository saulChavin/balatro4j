package com.balatro.enums;

public interface Named {

    String getName();

    default boolean equals(String value) {
        return getName().equals(value);
    }
}

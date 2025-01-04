package com.balatro.enums;

public enum Version {
    v_100n(10014),
    v_101c(10103),
    v_101f(10106);

    private final int version;

    Version(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }
}

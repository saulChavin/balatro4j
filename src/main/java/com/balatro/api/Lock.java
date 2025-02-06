package com.balatro.api;

import org.jetbrains.annotations.NotNull;

public interface Lock {

    void unlock(Item item);

    void lock(@NotNull Item item);

    boolean isLocked(@NotNull Item item);

    void initUnlocks(int ante, boolean freshProfile);

    void initLocks(int ante, boolean freshProfile, boolean freshRun);

    void firstLock();
}

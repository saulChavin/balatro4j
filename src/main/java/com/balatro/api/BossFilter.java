package com.balatro.api;

import com.balatro.enums.Boss;

public record BossFilter(Boss boss) implements Filter{
    @Override
    public boolean filter(Run run) {
        return run.hasBoss(boss);
    }
}

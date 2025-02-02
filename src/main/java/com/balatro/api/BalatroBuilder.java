package com.balatro.api;

import com.balatro.BalatroImpl;
import com.balatro.enums.Deck;
import com.balatro.enums.PackKind;
import com.balatro.enums.Stake;
import com.balatro.enums.Version;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BalatroBuilder {

    private int maxAnte = 8;
    private List<Integer> cardsPerAnte = List.of(15, 50, 50, 50, 50, 50, 50, 50);
    private Deck deck = Deck.RED_DECK;
    private Stake stake = Stake.White_Stake;
    private Version version = Version.v_101f;
    private final String seed;
    private final Set<PackKind> enabledPacks;
    private boolean analyzeTags;
    private boolean analyzeBoss;
    private boolean analyzeShopQueue;

    public BalatroBuilder(String seed, int maxAnte) {
        this.seed = seed;
        this.enabledPacks = new HashSet<>(Set.of(PackKind.Arcana, PackKind.Buffoon, PackKind.Spectral));
        this.analyzeShopQueue = true;
        this.maxAnte = maxAnte;
    }

    public BalatroBuilder analyzeAll() {
        this.enabledPacks.addAll(Set.of(PackKind.values()));
        this.analyzeTags = true;
        this.analyzeBoss = true;
        return this;
    }

    public BalatroBuilder maxAnte(int maxAnte) {
        this.maxAnte = maxAnte;
        return this;
    }

    public int maxAnte() {
        return maxAnte;
    }

    public BalatroBuilder cardsPerAnte(List<Integer> cardsPerAnte) {
        this.cardsPerAnte = cardsPerAnte;
        return this;
    }

    public BalatroBuilder deck(Deck deck) {
        this.deck = deck;
        return this;
    }

    public BalatroBuilder stake(Stake stake) {
        this.stake = stake;
        return this;
    }

    public BalatroBuilder version(Version version) {
        this.version = version;
        return this;
    }

    public BalatroBuilder analyzeStandardPacks() {
        this.enabledPacks.add(PackKind.Standard);
        return this;
    }

    public BalatroBuilder analyzeCelestialPacks() {
        this.enabledPacks.add(PackKind.Celestial);
        return this;
    }

    public BalatroBuilder disablePack(PackKind packKind) {
        this.enabledPacks.remove(packKind);
        return this;
    }

    public BalatroBuilder analyzeTags() {
        this.analyzeTags = true;
        return this;
    }

    public BalatroBuilder analyzeBoss() {
        this.analyzeBoss = true;
        return this;
    }

    public BalatroBuilder disableShopQueue() {
        this.analyzeShopQueue = false;
        return this;
    }

    public Run build() {
        return new BalatroImpl(seed, maxAnte, cardsPerAnte, deck, stake, version, enabledPacks, analyzeTags, analyzeBoss,
                analyzeShopQueue)
                .analyze();
    }
}

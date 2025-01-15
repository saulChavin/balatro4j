package com.balatro.api;

import com.balatro.BalatroImpl;
import com.balatro.enums.Deck;
import com.balatro.enums.Stake;
import com.balatro.enums.Version;

import java.util.List;

public class BalatroBuilder {

    private int maxAnte = 8;
    private List<Integer> cardsPerAnte = List.of(15, 50, 50, 50, 50, 50, 50, 50);
    private Deck deck = Deck.RED_DECK;
    private Stake stake = Stake.White_Stake;
    private Version version = Version.v_101f;
    private final String seed;

    public BalatroBuilder(String seed) {
        this.seed = seed;
    }

    public BalatroBuilder maxAnte(int maxAnte) {
        this.maxAnte = maxAnte;
        return this;
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

    public Run build() {
        return new BalatroImpl(seed, maxAnte, cardsPerAnte, deck, stake, version)
                .analyze();
    }
}

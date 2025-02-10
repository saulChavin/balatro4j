package com.balatro.structs;

import com.balatro.enums.Deck;
import com.balatro.enums.Stake;
import com.balatro.enums.Version;
import com.balatro.enums.Voucher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InstanceParams {

    private Deck deck;
    private Stake stake;
    private boolean showman;
    public int sixesFactor;
    public long version;
    public Set<Voucher> vouchers;

    public InstanceParams(Deck deck, Stake stake, boolean showman, long version) {
        this.deck = deck;
        this.stake = stake;
        this.showman = showman;
        sixesFactor = 1;
        this.version = version;
        vouchers = new HashSet<>();
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Stake getStake() {
        return stake;
    }

    public void setStake(Stake stake) {
        this.stake = stake;
    }

    public boolean isShowman() {
        return showman;
    }

    public void setShowman(boolean showman) {
        this.showman = showman;
    }

    public int getSixesFactor() {
        return sixesFactor;
    }

    public void setSixesFactor(int sixesFactor) {
        this.sixesFactor = sixesFactor;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(Set<Voucher> vouchers) {
        this.vouchers = vouchers;
    }
}
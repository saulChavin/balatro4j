package com.balatro.structs;

import com.balatro.enums.Deck;
import com.balatro.enums.Stake;
import com.balatro.enums.Version;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InstanceParams {

    private Deck deck;
    private Stake stake;
    public boolean showman;
    public int sixesFactor;
    public long version;
    public Set<String> vouchers;

    public InstanceParams() {
        deck = Deck.RED_DECK;
        stake = Stake.White_Stake;
        showman = false;
        sixesFactor = 1;
        version = Version.v_101c.getVersion();
        vouchers = new HashSet<>();
    }

    public InstanceParams(Deck d, Stake s, boolean show, long v) {
        deck = d;
        stake = s;
        showman = show;
        sixesFactor = 1;
        version = v;
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

    public Set<String> getVouchers() {
        return vouchers;
    }

    public void setVouchers(Set<String> vouchers) {
        this.vouchers = vouchers;
    }
}
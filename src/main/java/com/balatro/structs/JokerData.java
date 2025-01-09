package com.balatro.structs;

import com.balatro.enums.Edition;

public class JokerData {
    public String joker;
    public String rarity;
    public Edition edition;
    public JokerStickers stickers;

    public JokerData() {
        this.joker = "Joker";
        this.rarity = "Common";
        this.edition = Edition.NoEdition;
        this.stickers = new JokerStickers();
    }

    public JokerData(String joker, String rarity, Edition edition, JokerStickers stickers) {
        this.joker = joker;
        this.rarity = rarity;
        this.edition = edition;
        this.stickers = stickers;
    }

    public String getJoker() {
        return joker;
    }

    public void setJoker(String joker) {
        this.joker = joker;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public JokerStickers getStickers() {
        return stickers;
    }

    public void setStickers(JokerStickers stickers) {
        this.stickers = stickers;
    }
}
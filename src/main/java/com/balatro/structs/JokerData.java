package com.balatro.structs;

public class JokerData {
    public String joker;
    public String rarity;
    public String edition;
    public JokerStickers stickers;

    public JokerData() {
        this.joker = "Joker";
        this.rarity = "Common";
        this.edition = "No Edition";
        this.stickers = new JokerStickers();
    }

    public JokerData(String joker, String rarity, String edition, JokerStickers stickers) {
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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public JokerStickers getStickers() {
        return stickers;
    }

    public void setStickers(JokerStickers stickers) {
        this.stickers = stickers;
    }
}
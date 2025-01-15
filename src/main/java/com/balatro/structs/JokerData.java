package com.balatro.structs;

import com.balatro.api.Item;
import com.balatro.enums.CommonJoker;
import com.balatro.enums.Edition;

public class JokerData {
    public Item joker;
    public String rarity;
    public Edition edition;
    public JokerStickers stickers;

    public JokerData() {
        this.joker = CommonJoker.Joker;
        this.rarity = "Common";
        this.edition = Edition.NoEdition;
        this.stickers = new JokerStickers();
    }

    public JokerData(Item joker, String rarity, Edition edition, JokerStickers stickers) {
        this.joker = joker;
        this.rarity = rarity;
        this.edition = edition;
        this.stickers = stickers;
    }

    public Item getJoker() {
        return joker;
    }

    public void setJoker(Item joker) {
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
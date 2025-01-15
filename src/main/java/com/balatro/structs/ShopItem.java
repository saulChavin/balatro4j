package com.balatro.structs;

import com.balatro.api.Item;
import com.balatro.enums.CommonJoker;
import com.balatro.enums.Tarot;
import com.balatro.enums.Type;

public class ShopItem {
    private Type type;
    private Item item;
    private JokerData jokerData;

    public ShopItem() {
        this.type = Type.Tarot;
        this.item = Tarot.The_Fool;
        this.jokerData = new JokerData();
    }

    public ShopItem(Type type, Item item) {
        this.type = type;
        this.item = item;
        this.jokerData = new JokerData();
    }

    public ShopItem(Type type, Item item, JokerData jokerData) {
        this.type = type;
        this.item = item;
        this.jokerData = jokerData;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public JokerData getJokerData() {
        return jokerData;
    }

    public void setJokerData(JokerData jokerData) {
        this.jokerData = jokerData;
    }
}
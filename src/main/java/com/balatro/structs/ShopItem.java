package com.balatro.structs;

import com.balatro.enums.Type;

public class ShopItem {
    private Type type;
    private String item;
    private JokerData jokerData;

    public ShopItem() {
        this.type = Type.Tarot;
        this.item = "The Fool";
        this.jokerData = new JokerData();
    }

    public ShopItem(Type type, String item) {
        this.type = type;
        this.item = item;
        this.jokerData = new JokerData();
    }

    public ShopItem(Type type, String item, JokerData jokerData) {
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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public JokerData getJokerData() {
        return jokerData;
    }

    public void setJokerData(JokerData jokerData) {
        this.jokerData = jokerData;
    }
}
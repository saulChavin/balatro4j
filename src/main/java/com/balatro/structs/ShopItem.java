package com.balatro.structs;

public class ShopItem {
    private String type;
    private String item;
    private JokerData jokerData;

    public ShopItem() {
        this.type = "Tarot";
        this.item = "The Fool";
        this.jokerData = new JokerData();
    }

    public ShopItem(String type, String item) {
        this.type = type;
        this.item = item;
        this.jokerData = new JokerData();
    }

    public ShopItem(String type, String item, JokerData jokerData) {
        this.type = type;
        this.item = item;
        this.jokerData = jokerData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
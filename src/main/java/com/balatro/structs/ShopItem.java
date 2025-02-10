package com.balatro.structs;

import com.balatro.api.Item;
import com.balatro.enums.CommonJoker;
import com.balatro.enums.Tarot;
import com.balatro.enums.Type;

public class ShopItem {
    private final Type type;
    private final Item item;
    private final JokerData jokerData;

    public ShopItem(Type type, Item item) {
        this.type = type;
        this.item = item;
        this.jokerData = null;
    }

    public ShopItem(Type type, Item item, JokerData jokerData) {
        this.type = type;
        this.item = item;
        this.jokerData = jokerData;
    }

    public Type getType() {
        return type;
    }

    public Item getItem() {
        return item;
    }

    public JokerData getJokerData() {
        return jokerData;
    }

}
package com.balatro.api;

import com.balatro.enums.Boss;
import com.balatro.enums.LegendaryJoker;
import com.balatro.enums.Tag;
import com.balatro.enums.Voucher;
import com.balatro.structs.Pack;
import com.balatro.structs.ShopQueue;

import java.util.List;
import java.util.Set;

public interface Ante {

    int getAnte();

    ShopQueue getShopQueue();

    Set<Tag> getTags();

    Voucher getVoucher();

    Boss getBoss();

    List<Pack> getPacks();

    int countInPack(String name);

    boolean containsInPack(String name);

    boolean hasLegendary(LegendaryJoker... jokers);

    boolean hasInShop(String named, int index);

    boolean hasInShop(String name);

}

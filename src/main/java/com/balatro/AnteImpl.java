package com.balatro;

import com.balatro.api.Ante;
import com.balatro.api.Named;
import com.balatro.enums.*;
import com.balatro.structs.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class AnteImpl implements Ante {

    private final int ante;
    private final Functions functions;
    private final ShopQueue shopQueue;
    @JsonIgnore
    private final Set<String> shop;
    private final Set<Tag> tags;
    private Voucher voucher;
    private Boss boss;
    private final List<Pack> packs;

    public AnteImpl(int ante, Functions functions) {
        this.ante = ante;
        this.functions = functions;
        this.tags = new HashSet<>(2);
        this.shopQueue = new ShopQueue();
        this.shop = new HashSet<>(20);
        this.packs = new ArrayList<>(10);
    }

    @Override
    public boolean hasInShop(String name) {
        return shop.contains(name);
    }

    @Override
    public boolean hasInShop(String named, int index) {
        if (index > shopQueue.size()) {
            return false;
        }

        for (int i = 0; i < index; i++) {
            if (shopQueue.get(i).item().equalsIgnoreCase(named)) {
                return true;
            }
        }

        return false;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void addToQueue(@NotNull ShopItem value, Named sticker) {
        shop.add(value.getItem());
        shopQueue.add(new SearchableItem(value.getItem(), sticker));
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public void addPack(@NotNull Pack pack, Set<Option> options) {
        pack.setOptions(options);
        packs.add(pack);
    }

    @Override
    public boolean hasLegendary(LegendaryJoker... jokers) {
        int souls = countInPack("The Soul");

        if (souls < jokers.length) return false;

        var jokersFound = new HashSet<String>();

        for (int i = 0; i < jokers.length; i++) {
            jokersFound.add(functions.nextJoker("sou", ante, false).joker);
        }

        for (LegendaryJoker joker : jokers) {
            if (!jokersFound.contains(joker.getName())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean containsInPack(String name) {
        for (Pack pack : packs) {
            if (pack.containsOption(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int countInPack(String name) {
        int count = 0;
        for (Pack pack : packs) {
            if (pack.containsOption(name)) {
                count++;
            }
        }
        return count;
    }


    @Override
    public int getAnte() {
        return ante;
    }

    @JsonIgnore
    public Set<String> getShop() {
        return shop;
    }

    @Override
    public ShopQueue getShopQueue() {
        return new ShopQueue(shopQueue);
    }

    @Override
    public Set<Tag> getTags() {
        return new HashSet<>(tags);
    }

    @Override
    public Voucher getVoucher() {
        return voucher;
    }

    @Override
    public Boss getBoss() {
        return boss;
    }

    @Override
    public List<Pack> getPacks() {
        return new ArrayList<>(packs);
    }
}

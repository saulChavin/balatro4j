package com.balatro.structs;

import com.balatro.Functions;
import com.balatro.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ante {

    private final int ante;
    private final Functions functions;
    private final List<SearchableItem> shopQueue;
    @JsonIgnore
    private final Set<String> shop;
    private final Set<Tag> tags;
    private Voucher voucher;
    private Boss boss;
    private final List<Pack> packs;

    public Ante(int ante, Functions functions) {
        this.ante = ante;
        this.functions = functions;
        this.tags = new HashSet<>(2);
        this.shopQueue = new ArrayList<>(20);
        this.shop = new HashSet<>(20);
        this.packs = new ArrayList<>(10);
    }

    public boolean hasInShop(String name) {
        return shop.contains(name);
    }

    public boolean hasInShop(String named, int index) {
        if (index > shopQueue.size()) {
            return false;
        }

        for (int i = 0; i < index; i++) {
            if (shopQueue.get(i).item.equalsIgnoreCase(named)) {
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

    public void addPack(Pack pack, Set<Option> options) {
        pack.setOptions(options);
        packs.add(pack);
    }

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

    public boolean containsInPack(String name) {
        for (Pack pack : packs) {
            if (pack.containsOption(name)) {
                return true;
            }
        }
        return false;
    }

    public int countInPack(String name) {
        int count = 0;
        for (Pack pack : packs) {
            if (pack.containsOption(name)) {
                count++;
            }
        }
        return count;
    }

    public record SearchableItem(String item, @Nullable Named sticker) {

        public SearchableItem(String item, Named sticker) {
            this.item = item;
            this.sticker = sticker;
        }
    }

    public int getAnte() {
        return ante;
    }

    @JsonIgnore
    public Set<String> getShop() {
        return shop;
    }

    public List<SearchableItem> getShopQueue() {
        return shopQueue;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public Boss getBoss() {
        return boss;
    }

    public List<Pack> getPacks() {
        return packs;
    }
}

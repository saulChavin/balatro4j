package com.balatro.structs;

import com.balatro.Functions;
import com.balatro.enums.Boss;
import com.balatro.enums.LegendaryJoker;
import com.balatro.enums.Tag;
import com.balatro.enums.Voucher;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ante {

    private final int id;
    private final Functions functions;
    private final List<SearchableItem> shopQueue;
    private final Set<String> shop;
    private final Set<Tag> tags;
    private Voucher voucher;
    private Boss boss;
    private final List<SearchablePack> packs;

    public Ante(int id, Functions functions) {
        this.id = id;
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

    public void addToQueue(@NotNull ShopItem value, String sticker) {
        shop.add(value.getItem());
        shopQueue.add(new SearchableItem(value.getItem(), sticker));
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public void addPack(Pack pack, Set<String> options) {
        packs.add(new SearchablePack(pack, options));
    }

    public boolean hasLegendary(LegendaryJoker joker) {
        if (containsInPack("The Soul")) {
            return functions.nextJoker("sou", id, false).joker.equalsIgnoreCase(joker.getName());
        }
        return false;
    }

    public boolean containsInPack(String name) {
        for (SearchablePack pack : packs) {
            if (pack.options.contains(name)) {
                return true;
            }
        }
        return false;
    }

    public record SearchableItem(String item, String sticker) {
    }

    public record SearchablePack(Pack pack, Set<String> options) {
    }

    public int getId() {
        return id;
    }

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

    public List<SearchablePack> getPacks() {
        return packs;
    }
}

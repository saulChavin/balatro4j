package com.balatro.api;

import com.balatro.enums.Boss;
import com.balatro.enums.LegendaryJoker;
import com.balatro.enums.PackType;
import com.balatro.enums.Voucher;
import org.jetbrains.annotations.NotNull;

public interface Queryable extends CommonQueries {

    boolean hasInPack(int ante, Item item);

    boolean hasInShop(int ante, Item item);

    long countLegendary(int ante);

    boolean hasInShop(int ante, Item item, int index);

    boolean hasLegendary(int ante, LegendaryJoker... jokers);

    boolean hasInSpectral(int ante, @NotNull Item item);

    boolean hasInBuffonPack(int ante, @NotNull Item item);

    boolean hasPack(int ante, PackType packType);

    boolean hasVoucher(int ante, Voucher voucher);

    boolean hasBoss(int ante, Boss boss);

}

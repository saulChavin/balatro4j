package com.balatro.api;

import com.balatro.enums.Boss;
import com.balatro.enums.LegendaryJoker;
import com.balatro.enums.PackType;
import com.balatro.enums.Voucher;
import org.jetbrains.annotations.NotNull;

public interface CommonQueries {

    boolean hasBoss(Boss boss);

    boolean hasInPack(Item item);

    boolean hasInShop(Item item);

    boolean hasInShop(@NotNull Item item, int index);

    long countLegendary();

    int countInPack(@NotNull Item item);

    boolean hasLegendary(LegendaryJoker... jokers);

    boolean hasPack(PackType packType);

    boolean hasInBuffonPack(@NotNull Item item);

    boolean hasInSpectral(@NotNull Item item);

    boolean hasVoucher(Voucher voucher);


}

package com.balatro.api;

import com.balatro.enums.*;
import com.balatro.structs.Pack;
import com.balatro.structs.ShopQueue;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public interface Ante extends CommonQueries {

    int getAnte();

    ShopQueue getShopQueue();

    Set<Tag> getTags();

    Voucher getVoucher();

    Boss getBoss();

    List<Pack> getPacks();

}

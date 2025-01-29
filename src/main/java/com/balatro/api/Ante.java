package com.balatro.api;

import com.balatro.enums.*;
import com.balatro.structs.PackInfo;
import com.balatro.structs.ShopQueue;

import java.util.List;
import java.util.Set;

public interface Ante extends CommonQueries {

    int getAnte();

    ShopQueue getShopQueue();

    Set<Tag> getTags();

    Voucher getVoucher();

    Boss getBoss();

    List<PackInfo> getPacks();

    Set<String> getLegendaryJokers();

    Double getScore();

    int getBufferedJokerCount();

    Set<Joker> getJokers();

    Set<Joker> getRareJokers();

    Set<Joker> getUncommonJokers();

    int getNegativeJokerCount();

    Set<Tarot> getTarots();

    Set<Planet> getPlanets();

    int getStandardPackCount();

    int getJokerPackCount();

    int getSpectralPackCount();

    int getTarotPackCount();

    int getPlanetPackCount();
}

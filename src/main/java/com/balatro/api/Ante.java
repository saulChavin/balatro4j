package com.balatro.api;

import com.balatro.enums.*;
import com.balatro.structs.JokerData;
import com.balatro.structs.PackInfo;
import com.balatro.structs.ShopQueue;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Ante extends CommonQueries {

    int getAnte();

    ShopQueue getShopQueue();

    Set<Tag> getTags();

    Voucher getVoucher();

    Boss getBoss();

    List<PackInfo> getPacks();

    @JsonIgnore
    Map<String, JokerData> getLegendaryJokers();

    @JsonIgnore
    Double getScore();

    @JsonIgnore
    int getBufferedJokerCount();

    @JsonIgnore
    Set<Joker> getJokers();

    @JsonIgnore
    Set<Joker> getRareJokers();

    @JsonIgnore
    Set<Joker> getUncommonJokers();

    @JsonIgnore
    int getNegativeJokerCount();

    @JsonIgnore
    Set<Tarot> getTarots();

    @JsonIgnore
    Set<Planet> getPlanets();

    @JsonIgnore
    int getStandardPackCount();

    @JsonIgnore
    int getJokerPackCount();

    @JsonIgnore
    int getSpectralPackCount();

    @JsonIgnore
    int getTarotPackCount();

    @JsonIgnore
    int getPlanetPackCount();
}

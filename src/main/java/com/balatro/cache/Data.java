package com.balatro.cache;

import com.balatro.api.*;
import com.balatro.enums.*;
import com.balatro.structs.JokerData;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public final class Data {

    private final String seed;
    private final long[] data;

    public Data(String seed, long[] data) {
        this.seed = seed;
        this.data = data;
    }

    public Data(@NotNull Run run) {
        this.seed = run.seed();
        this.data = new long[13];

        for (Ante ante : run.antes()) {
            for (Joker joker : ante.getJokers()) {
                turnOn(joker);
            }

            turnOn(ante.getVoucher());
            turnOn(ante.getBoss());

            for (Tag tag : ante.getTags()) {
                turnOn(tag);
            }

            for (Tarot tarot : ante.getTarots()) {
                turnOn(tarot);
            }

            for (Planet planet : ante.getPlanets()) {
                turnOn(planet);
            }

            for (JokerData value : ante.getLegendaryJokers().values()) {
                turnOn(value);
            }

            for (Spectral spectral : ante.getSpectrals()) {
                turnOn(spectral);
            }
        }
    }

    public String getSeed() {
        return seed;
    }

    public boolean contains(@NotNull List<Item> items) {
        for (Item item : items) {
            if (!isOn(item)) {
                return false;
            }
        }

        return true;
    }

    public void write(@NotNull ByteArrayOutputStream baos) {
        baos.writeBytes(seed.getBytes());
        int byteLength = data.length * Long.BYTES; // Long.BYTES is 8
        byte[] byteArray = new byte[byteLength];
        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);

        for (int i = 0; i < data.length; i++) {
            byteBuffer.putLong(0, data[i]);
            System.arraycopy(byteBuffer.array(), 0, byteArray, i * Long.BYTES, Long.BYTES);
        }

        baos.writeBytes(byteArray);
    }

    private void turnOn(@NotNull Item item) {
        data[item.getYIndex()] = data[item.getYIndex()] | (1L << item.ordinal());
    }

    private boolean isOn(@NotNull Item item) {
        return (data[item.getYIndex()] & (1L << item.ordinal())) != 0;
    }


}

package com.balatro.cache;

import com.balatro.api.*;
import com.balatro.enums.*;
import com.balatro.structs.JokerData;
import com.balatro.structs.EditionItem;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public final class Data {

    public static final int DATA_SIZE = 12;
    private final String seed;
    private final long[] data;
    private final long[] editions;
    private final int score;

    public Data(String seed, int score, long[] data, long[] editions) {
        this.seed = seed;
        this.data = data;
        this.editions = editions;
        this.score = score;
    }

    public Data(@NotNull Run run) {
        this.seed = run.seed();
        this.data = new long[DATA_SIZE];
        List<JokerData> jokerDataList = new ArrayList<>();
        this.score = (int) run.getScore();

        for (Ante ante : run.antes()) {
            for (EditionItem joker : ante.getJokers()) {
                turnOn(joker);

                if (joker.edition() != null) {
                    jokerDataList.add(joker.jokerData());
                }
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

                if (value.getEdition() != null && value.getEdition() != Edition.NoEdition) {
                    jokerDataList.add(value);
                }
            }

            for (Spectral spectral : ante.getSpectrals()) {
                turnOn(spectral);
            }
        }

        editions = new long[jokerDataList.size() * 3];

        for (int i = 0; i < jokerDataList.size(); i += 3) {
            JokerData jokerData = jokerDataList.get(i);
            editions[i * 3] = jokerData.getJoker().ordinal();
            editions[i * 3 + 1] = jokerData.getJoker().getYIndex();
            editions[i * 3 + 2] = jokerData.getEdition().ordinal();
        }

        jokerDataList.clear();
    }

    public String getSeed() {
        return seed;
    }

    public int getScore() {
        return score;
    }

    public boolean contains(@NotNull List<EditionItem> items) {
        for (EditionItem item : items) {
            if (!isOn(item)) {
                return false;
            }
        }

        return true;
    }

    public void write(@NotNull ByteArrayOutputStream baos) {
        baos.writeBytes(seed.getBytes());
        byte[] longArray = new byte[data.length * Long.BYTES];
        final var longBuffer = ByteBuffer.allocate(Long.BYTES);

        for (int i = 0; i < data.length; i++) {
            longBuffer.putLong(0, data[i]);
            System.arraycopy(longBuffer.array(), 0, longArray, i * Long.BYTES, Long.BYTES);
        }

        baos.writeBytes(longArray);

        baos.writeBytes(ByteBuffer.allocate(Integer.BYTES).putInt(score).array());
        baos.writeBytes(ByteBuffer.allocate(Integer.BYTES).putInt(editions.length).array());

        if (editions.length == 0) {
            return;
        }

        longArray = new byte[editions.length * Long.BYTES];

        for (int i = 0; i < editions.length; i++) {
            longBuffer.putLong(0, editions[i]);
            System.arraycopy(longBuffer.array(), 0, longArray, i * Long.BYTES, Long.BYTES);
        }

        baos.writeBytes(longArray);
    }

    private void turnOn(@NotNull Item item) {
        data[item.getYIndex()] = data[item.getYIndex()] | (1L << item.ordinal());
    }

    private boolean isOn(@NotNull EditionItem item) {
        var on = (data[item.getYIndex()] & (1L << item.ordinal())) != 0;

        if (item.edition() == Edition.NoEdition) {
            return on;
        }

        if (!on) {
            return false;
        }

        for (int i = 0; i < editions.length; i += 3) {
            if (editions[i] == item.ordinal() && editions[i + 1] == item.getYIndex() && editions[i + 2] == item.edition().ordinal()) {
                return true;
            }
        }

        return false;
    }


}

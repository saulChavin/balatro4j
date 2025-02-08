package com.balatro.structs;

import com.balatro.api.Item;
import com.balatro.enums.Edition;
import com.balatro.enums.Enhancement;
import com.balatro.enums.Seal;
import org.jetbrains.annotations.Nullable;

public class Card implements Item {
    private String base;
    private @Nullable Enhancement enhancement;
    private Edition edition;
    private Seal seal;

    public Card(String base, @Nullable Enhancement enhancement, Edition edition, Seal seal) {
        this.base = base;
        this.enhancement = enhancement;
        this.edition = edition;
        this.seal = seal;
    }

    @Override
    public String getName() {
        return base;
    }

    @Override
    public int getYIndex() {
        return -1;
    }

    @Override
    public int ordinal() {
        return 0;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public @Nullable Enhancement getEnhancement() {
        return enhancement;
    }

    public void setEnhancement(@Nullable Enhancement enhancement) {
        this.enhancement = enhancement;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public Seal getSeal() {
        return seal;
    }

    public void setSeal(Seal seal) {
        this.seal = seal;
    }
}
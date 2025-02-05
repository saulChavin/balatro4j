package com.balatro.structs;

import com.balatro.api.Item;
import com.balatro.enums.Edition;
import com.balatro.enums.Seal;

public class Card implements Item {
    private String base;
    private String enhancement;
    private Edition edition;
    private Seal seal;

    public Card(String base, String enhancement, Edition edition, Seal seal) {
        this.base = base;
        this.enhancement = enhancement;
        this.edition = edition;
        this.seal = seal;
    }

    @Override
    public String getName() {
        return base;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getEnhancement() {
        return enhancement;
    }

    public void setEnhancement(String enhancement) {
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
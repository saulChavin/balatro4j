package com.balatro.structs;

public class Card {
    private String base;
    private String enhancement;
    private String edition;
    private String seal;

    public Card(String base, String enhancement, String edition, String seal) {
        this.base = base;
        this.enhancement = enhancement;
        this.edition = edition;
        this.seal = seal;
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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getSeal() {
        return seal;
    }

    public void setSeal(String seal) {
        this.seal = seal;
    }
}
package com.balatro.structs;

public class ShopInstance {
    public double jokerRate;
    public double tarotRate;
    public double planetRate;
    public double playingCardRate;
    public double spectralRate;

    public ShopInstance() {
        this.jokerRate = 20;
        this.tarotRate = 4;
        this.planetRate = 4;
        this.playingCardRate = 0;
        this.spectralRate = 0;
    }

    public ShopInstance(double jokerRate, double tarotRate, double planetRate, double playingCardRate, double spectralRate) {
        this.jokerRate = jokerRate;
        this.tarotRate = tarotRate;
        this.planetRate = planetRate;
        this.playingCardRate = playingCardRate;
        this.spectralRate = spectralRate;
    }

    public double getTotalRate() {
        return jokerRate + tarotRate + planetRate + playingCardRate + spectralRate;
    }

    public double getJokerRate() {
        return jokerRate;
    }

    public void setJokerRate(double jokerRate) {
        this.jokerRate = jokerRate;
    }

    public double getTarotRate() {
        return tarotRate;
    }

    public void setTarotRate(double tarotRate) {
        this.tarotRate = tarotRate;
    }

    public double getPlanetRate() {
        return planetRate;
    }

    public void setPlanetRate(double planetRate) {
        this.planetRate = planetRate;
    }

    public double getPlayingCardRate() {
        return playingCardRate;
    }

    public void setPlayingCardRate(double playingCardRate) {
        this.playingCardRate = playingCardRate;
    }

    public double getSpectralRate() {
        return spectralRate;
    }

    public void setSpectralRate(double spectralRate) {
        this.spectralRate = spectralRate;
    }
}
package com.balatro;

import com.balatro.api.Named;

import java.util.HashSet;
import java.util.Set;

sealed class Lock permits Functions {

    private final Set<String> locked = new HashSet<>();

    public void lock(String item) {
        locked.add(item);
    }

    public void unlock(String item) {
        locked.remove(item);
    }

    public void lock(Named named) {
        lock(named.getName());
    }

    public boolean isLocked(Named named) {
        return isLocked(named.getName());
    }

    public boolean isLocked(String item) {
        return locked.contains(item);
    }

    public void initLocks(int ante, boolean freshProfile, boolean freshRun) {
        if (ante < 2) {
            lock("The Mouth");
            lock("The Fish");
            lock("The Wall");
            lock("The House");
            lock("The Mark");
            lock("The Wheel");
            lock("The Arm");
            lock("The Water");
            lock("The Needle");
            lock("The Flint");
            lock("Negative Tag");
            lock("Standard Tag");
            lock("Meteor Tag");
            lock("Buffoon Tag");
            lock("Handy Tag");
            lock("Garbage Tag");
            lock("Ethereal Tag");
            lock("Top-up Tag");
            lock("Orbital Tag");
        }

        if (ante < 3) {
            lock("The Tooth");
            lock("The Eye");
        }

        if (ante < 4) lock("The Plant");
        if (ante < 5) lock("The Serpent");
        if (ante < 6) lock("The Ox");

        if (freshProfile) {
            lock("Negative Tag");
            lock("Foil Tag");
            lock("Holographic Tag");
            lock("Polychrome Tag");
            lock("Rare Tag");
            lock("Golden Ticket");
            lock("Mr. Bones");
            lock("Acrobat");
            lock("Sock and Buskin");
            lock("Swashbuckler");
            lock("Troubadour");
            lock("Certificate");
            lock("Smeared Joker");
            lock("Throwback");
            lock("Hanging Chad");
            lock("Rough Gem");
            lock("Bloodstone");
            lock("Arrowhead");
            lock("Onyx Agate");
            lock("Glass Joker");
            lock("Showman");
            lock("Flower Pot");
            lock("Blueprint");
            lock("Wee Joker");
            lock("Merry Andy");
            lock("Oops! All 6s");
            lock("The Idol");
            lock("Seeing Double");
            lock("Matador");
            lock("Hit the Road");
            lock("The Duo");
            lock("The Trio");
            lock("The Family");
            lock("The Order");
            lock("The Tribe");
            lock("Stuntman");
            lock("Invisible Joker");
            lock("Brainstorm");
            lock("Satellite");
            lock("Shoot the Moon");
            lock("Driver's License");
            lock("Cartomancer");
            lock("Astronomer");
            lock("Burnt Joker");
            lock("Bootstraps");
            lock("Overstock Plus");
            lock("Liquidation");
            lock("Glow Up");
            lock("Reroll Glut");
            lock("Omen Globe");
            lock("Observatory");
            lock("Nacho Tong");
            lock("Recyclomancy");
            lock("Tarot Tycoon");
            lock("Planet Tycoon");
            lock("Money Tree");
            lock("Antimatter");
            lock("Illusion");
            lock("Petroglyph");
            lock("Retcon");
            lock("Palette");
        }
        if (freshRun) {
            lock("Planet X");
            lock("Ceres");
            lock("Eris");
            lock("Five of a Kind");
            lock("Flush House");
            lock("Flush Five");
            lock("Stone Joker");
            lock("Steel Joker");
            lock("Glass Joker");
            lock("Golden Ticket");
            lock("Lucky Cat");
            lock("Cavendish");
            lock("Overstock Plus");
            lock("Liquidation");
            lock("Glow Up");
            lock("Reroll Glut");
            lock("Omen Globe");
            lock("Observatory");
            lock("Nacho Tong");
            lock("Recyclomancy");
            lock("Tarot Tycoon");
            lock("Planet Tycoon");
            lock("Money Tree");
            lock("Antimatter");
            lock("Illusion");
            lock("Petroglyph");
            lock("Retcon");
            lock("Palette");
        }
    }

    public void initUnlocks(int ante, boolean freshProfile) {
        if (ante == 2) {
            unlock("The Mouth");
            unlock("The Fish");
            unlock("The Wall");
            unlock("The House");
            unlock("The Mark");
            unlock("The Wheel");
            unlock("The Arm");
            unlock("The Water");
            unlock("The Needle");
            unlock("The Flint");
            if (!freshProfile) unlock("Negative Tag");
            unlock("Standard Tag");
            unlock("Meteor Tag");
            unlock("Buffoon Tag");
            unlock("Handy Tag");
            unlock("Garbage Tag");
            unlock("Ethereal Tag");
            unlock("Top-up Tag");
            unlock("Orbital Tag");
        }
        if (ante == 3) {
            unlock("The Tooth");
            unlock("The Eye");
        }
        if (ante == 4) unlock("The Plant");
        if (ante == 5) unlock("The Serpent");
        if (ante == 6) unlock("The Ox");
    }

}

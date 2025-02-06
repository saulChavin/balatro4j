package com.balatro;

import com.balatro.api.Item;
import com.balatro.api.Lock;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class LockImpl implements Lock {

    private final Set<String> locked;

    public LockImpl() {
        locked = new HashSet<>(ante2Lock);
    }

    public void lock(String item) {
        locked.add(item);
    }

    private void lock(Collection<String> collection) {
        locked.addAll(collection);
    }

    static Set<String> firstLock = Set.of(
            "Overstock Plus",
            "Liquidation",
            "Glow Up",
            "Reroll Glut",
            "Omen Globe",
            "Observatory",
            "Nacho Tong",
            "Recyclomancy",
            "Tarot Tycoon",
            "Planet Tycoon",
            "Money Tree",
            "Antimatter",
            "Illusion",
            "Petroglyph",
            "Retcon",
            "Palette");

    static Set<String> ante2Lock = Set.of(
            "The Mouth",
            "The Fish",
            "The Wall",
            "The House",
            "The Mark",
            "The Wheel",
            "The Arm",
            "The Water",
            "The Needle",
            "The Flint",
            "Negative Tag",
            "Standard Tag",
            "Meteor Tag",
            "Buffoon Tag",
            "Handy Tag",
            "Garbage Tag",
            "Ethereal Tag",
            "Top-up Tag",
            "Orbital Tag");

    public void firstLock() {
        locked.addAll(firstLock);
    }

    public void unlock(String item) {
        locked.remove(item);
    }

    public void unlock(Item item) {
        unlock(item.getName());
    }

    public void unlock(Collection<String> collection) {
        locked.removeAll(collection);
    }

    public void lock(@NotNull Item item) {
        lock(item.getName());
    }

    public boolean isLocked(@NotNull Item item) {
        return isLocked(item.getName());
    }

    public boolean isLocked(String item) {
        return locked.contains(item);
    }

    public void initLocks(int ante, boolean freshProfile, boolean freshRun) {
//        if (ante < 2) {
//            locked.addAll(ante2Lock);
//        }

        if (ante < 3) {
            lock("The Tooth");
            lock("The Eye");
        }

        if (ante < 4) lock("The Plant");
        if (ante < 5) lock("The Serpent");
        if (ante < 6) lock("The Ox");

        if (freshProfile) {
            lock(Set.of("Negative Tag", "Foil Tag", "Holographic Tag", "Polychrome Tag", "Rare Tag", "Golden Ticket", "Mr. Bones", "Acrobat", "Sock and Buskin", "Swashbuckler", "Troubadour", "Certificate", "Smeared Joker", "Throwback", "Hanging Chad", "Rough Gem", "Bloodstone", "Arrowhead", "Onyx Agate", "Glass Joker", "Showman", "Flower Pot", "Blueprint", "Wee Joker", "Merry Andy", "Oops! All 6s", "The Idol"));
            lock(Set.of("Seeing Double", "Matador", "Hit the Road", "The Duo", "The Trio", "The Family", "The Order", "The Tribe", "Stuntman", "Invisible Joker", "Brainstorm", "Satellite", "Shoot the Moon", "Driver's License", "Cartomancer", "Astronomer", "Burnt Joker", "Bootstraps", "Overstock Plus", "Liquidation", "Glow Up", "Reroll Glut", "Omen Globe"));
            lock(Set.of("Observatory", "Nacho Tong", "Recyclomancy", "Tarot Tycoon", "Planet Tycoon", "Money Tree", "Antimatter", "Illusion", "Petroglyph", "Retcon", "Palette"));
        }
        if (freshRun) {
            lock(Set.of("Planet X", "Ceres", "Eris", "Five of a Kind", "Flush House", "Flush Five", "Stone Joker", "Steel Joker", "Glass Joker", "Golden Ticket", "Lucky Cat", "Cavendish", "Overstock Plus", "Liquidation", "Glow Up", "Reroll Glut", "Omen Globe", "Observatory", "Nacho Tong", "Recyclomancy", "Tarot Tycoon", "Planet Tycoon", "Money Tree", "Antimatter", "Illusion", "Petroglyph", "Retcon", "Palette"));
        }
    }

    public void initUnlocks(int ante, boolean freshProfile) {
        if (ante == 2) {
            unlock(Set.of("The Mouth", "The Fish", "The Wall", "The House", "The Mark", "The Wheel", "The Arm", "The Water", "The Needle", "The Flint"));
            unlock(Set.of("Standard Tag", "Meteor Tag", "Buffoon Tag", "Handy Tag", "Garbage Tag", "Ethereal Tag", "Top-up Tag", "Orbital Tag"));
            if (!freshProfile) unlock("Negative Tag");
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

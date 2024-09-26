package com.mixologyxp;

public class PotionData {

    private static final Potion[] potions = {
            new Potion("Mammoth-might mix", 60, "MMM", 30021, 30011, 54919),  // IDs: 30021, 30011
            new Potion("Mystic mana amalgam", 60, "MMA", 30022, 30012, 54920),  // IDs: 30022, 30012
            new Potion("Marley's moonlight", 60, "MML", 30023, 30013, 54921),  // IDs: 30023, 30013
            new Potion("Alco-augmentator", 76, "AAA", 30024, 30014, 54922),  // IDs: 30024, 30014
            new Potion("Aqualux amalgam", 72, "ALA", 30025, 30015, 54923),  // IDs: 30025, 30015
            new Potion("Azure aura mix", 68, "AAM", 30026, 30016, 54924),  // IDs: 30026, 30016
            new Potion("Liplack liquor", 86, "LLL", 30027, 30017, 54925),  // IDs: 30027, 30017
            new Potion("Anti-leech lotion", 84, "LLA", 30028, 30018, 54926),  // IDs: 30028, 30018
            new Potion("Megalite liquid", 80, "LLM", 30029, 30019, 54927),  // IDs: 30029, 30019
            new Potion("Mixalot", 64, "MAL", 30030, 30020, 54928)  // IDs: 30030, 30020
    };

    public static Potion[] getPotions() {
        return potions;
    }
}

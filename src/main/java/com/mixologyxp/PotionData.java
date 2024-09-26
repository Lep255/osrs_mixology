package com.mixologyxp;

public class PotionData {

    private static final Potion[] potions = {
            new Potion("Mammoth-might mix", 60, "MMM", 30021, 30011, 54919),
            new Potion("Mystic mana amalgam", 60, "MMA", 30022, 30012, 54920),
            new Potion("Marley's moonlight", 60, "MML", 30023, 30013, 54921),
            new Potion("Alco-augmentator", 76, "AAA", 30024, 30014, 54922),
            new Potion("Aqualux amalgam", 72, "ALA", 30025, 30015, 54923),
            new Potion("Azure aura mix", 68, "AAM", 30026, 30016, 54924),
            new Potion("Liplack liquor", 86, "LLL", 30027, 30017, 54925),
            new Potion("Anti-leech lotion", 84, "LLA", 30028, 30018, 54926),
            new Potion("Megalite liquid", 80, "LLM", 30029, 30019, 54927),
            new Potion("Mixalot", 64, "MAL", 30030, 30020, 54928)
    };

    public static Potion[] getPotions() {
        return potions;
    }
}

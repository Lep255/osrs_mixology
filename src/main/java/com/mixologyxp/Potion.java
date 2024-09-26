package com.mixologyxp;

public class Potion {
    private final String name;
    private final int herbloreLevel;
    private final String combination;
    private final int finishedPotionId;  // First potion ID for finished potion
    private final int directionalPotionId;  // Second potion ID for directional object
    private final int morphId;  // Morph object ID

    public Potion(String name, int herbloreLevel, String combination, int finishedPotionId, int directionalPotionId, int morphId) {
        this.name = name;
        this.herbloreLevel = herbloreLevel;
        this.combination = combination;
        this.finishedPotionId = finishedPotionId;
        this.directionalPotionId = directionalPotionId;
        this.morphId = morphId;
    }

    public String getName() {
        return name;
    }

    public int getHerbloreLevel() {
        return herbloreLevel;
    }

    public String getCombination() {
        return combination;
    }

    public int getFinishedPotionId() {
        return finishedPotionId;
    }

    public int getDirectionalPotionId() {
        return directionalPotionId;
    }

    public int getMorphId() {
        return morphId;
    }
}

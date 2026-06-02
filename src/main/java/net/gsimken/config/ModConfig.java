package net.gsimken.config;

import java.util.List;
import java.util.Map;

public class ModConfig {
    private String item;
    private String name;
    private List<String> lore;
    private int CustomModelDataNumber;
    private Map<String, Float> lootTableProbabilities;
    private Map<String, Float> mobLootTableProbabilities;
    private Float genericChestProbability;
    private Float genericMobProbability;

    // Getters y setters

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public int getCustomModelDataNumber() {
        return CustomModelDataNumber;
    }

    public void setCustomModelDataNumber(int customModelDataNumber) {
        CustomModelDataNumber = customModelDataNumber;
    }

    public Map<String, Float> getLootTableProbabilities() {
        return lootTableProbabilities;
    }

    public void setLootTableProbabilities(Map<String, Float> lootTableProbabilities) {
        this.lootTableProbabilities = lootTableProbabilities;
    }

    public Map<String, Float> getMobLootTableProbabilities() {
        return mobLootTableProbabilities;
    }

    public void setMobLootTableProbabilities(Map<String, Float> mobLootTableProbabilities) {
        this.mobLootTableProbabilities = mobLootTableProbabilities;
    }

    public Float getGenericChestProbability() {
        return genericChestProbability;
    }

    public void setGenericChestProbability(Float genericChestProbability) {
        this.genericChestProbability = genericChestProbability;
    }

    public Float getGenericMobProbability() {
        return genericMobProbability;
    }

    public void setGenericMobProbability(Float genericMobProbability) {
        this.genericMobProbability = genericMobProbability;
    }

    public String printConfig(){
        return String.format("========TOEK Config========\nItem:%s\nItem Name: %s\n Lore: %s\nCustom Model Data Numbre:%d\n===========================",
                item,name,lore,CustomModelDataNumber
                );

    }
}

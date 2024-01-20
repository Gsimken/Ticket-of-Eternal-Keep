package net.gsimken.config;

import java.util.List;

public class ModConfig {
    private String item;
    private String name;
    private List<String> lore;
    private int CustomModelDataNumber;

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

    public String printConfig(){
        return String.format("========TOEK Config========\nItem:%s\nItem Name: %s\n Lore: %s\nCustom Model Data Numbre:%d\n===========================",
                item,name,lore,CustomModelDataNumber
                );

    }
}

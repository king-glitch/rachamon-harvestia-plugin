package dev.rachamon.rachamonharvestia.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@ConfigSerializable
public class PlantsConfig {


    @Setting(value = "plants", comment = "All plants")
    protected Map<String, PlantDataConfig> plants = this.initializePlants();

    @ConfigSerializable
    public static class PlantDataConfig {

        @Setting(value = "stage")
        protected int stage;

        @Setting(value = "block")
        protected String block;

        @Nullable
        @Setting(value = "fuel")
        protected String fuel;

        public PlantDataConfig() {
        }

        public PlantDataConfig(String block, @Nullable String fuel, int stage) {
            this.stage = stage;
            this.block = block;
            this.fuel = fuel;
        }

        public int getStage() {
            return stage;
        }

        public String getBlock() {
            return block;
        }

        @Nullable
        public String getFuel() {
            return fuel;
        }
    }

    public Map<String, PlantDataConfig> initializePlants() {
        return new HashMap<String, PlantDataConfig>() {{
            put("minecraft:wheat", new PlantDataConfig("minecraft:wheat", "minecraft:wheat_seeds", 7));
            put("minecraft:carrots", new PlantDataConfig("minecraft:carrots", "minecraft:carrot", 7));
            put("minecraft:potatoes", new PlantDataConfig("minecraft:potatoes", "minecraft:potatoes", 7));
            put("minecraft:beetroots", new PlantDataConfig("minecraft:beetroots", "minecraft:beetroot_seeds", 3));
            put("minecraft:cocoa", new PlantDataConfig("minecraft:cocoa", "minecraft:dye", 2));
            put("minecraft:nether_wart", new PlantDataConfig("minecraft:nether_wart", "minecraft:nether_wart", 3));
            put("minecraft:melon_block", new PlantDataConfig("minecraft:melon_block", null, 0));
            put("minecraft:pumpkin", new PlantDataConfig("minecraft:pumpkin", null, 0));
            put("minecraft:reeds", new PlantDataConfig("minecraft:reeds", "minecraft:reeds", 0));
        }};
    }

    public Map<String, PlantDataConfig> getPlants() {
        return plants;
    }
}
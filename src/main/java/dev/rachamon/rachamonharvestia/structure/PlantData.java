package dev.rachamon.rachamonharvestia.structure;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.item.ItemType;

public class PlantData {
    public int stage;
    public BlockType block;
    public ItemType fuel;
    public ItemType loot;
    public int min;
    public int max;

    public PlantData(BlockType block, ItemType loot, ItemType fuel, int min, int max, int stage) {
        this.stage = stage;
        this.block = block;
        this.fuel = fuel;
        this.loot = loot;
        this.min = min;
        this.max = max;
    }

}

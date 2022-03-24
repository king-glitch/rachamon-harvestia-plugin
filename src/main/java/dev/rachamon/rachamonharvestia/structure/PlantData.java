package dev.rachamon.rachamonharvestia.structure;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.item.ItemType;

import javax.annotation.Nullable;

public class PlantData {
    public int stage;
    public BlockType block;
    public ItemType loot;

    @Nullable
    public ItemType fuel;

    public PlantData(BlockType block, ItemType loot, ItemType fuel, int stage) {
        this.stage = stage;
        this.block = block;
        this.fuel = fuel;
        this.loot = loot;
    }

}

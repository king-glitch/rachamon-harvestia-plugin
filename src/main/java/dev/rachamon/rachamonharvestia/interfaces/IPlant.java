package dev.rachamon.rachamonharvestia.interfaces;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.item.ItemType;

public class IPlant {
    public int stage;
    public BlockType block;
    public ItemType fuel;

    public IPlant(BlockType block, ItemType fuel, int stage) {
        this.stage = stage;
        this.block = block;
        this.fuel = fuel;
    }
}

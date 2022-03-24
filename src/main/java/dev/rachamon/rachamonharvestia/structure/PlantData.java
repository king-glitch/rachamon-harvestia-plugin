package dev.rachamon.rachamonharvestia.structure;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.item.ItemType;

import javax.annotation.Nullable;

/**
 * The type Plant data.
 */
public class PlantData {
    /**
     * The Stage.
     */
    public int stage;
    /**
     * The Block.
     */
    public BlockType block;
    /**
     * The Loot.
     */
    public ItemType loot;

    /**
     * The Fuel.
     */
    @Nullable
    public ItemType fuel;

    /**
     * Instantiates a new Plant data.
     *
     * @param block the block
     * @param loot  the loot
     * @param fuel  the fuel
     * @param stage the stage
     */
    public PlantData(BlockType block, ItemType loot, ItemType fuel, int stage) {
        this.stage = stage;
        this.block = block;
        this.fuel = fuel;
        this.loot = loot;
    }

}

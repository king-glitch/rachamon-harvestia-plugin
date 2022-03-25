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
    private int stage;
    /**
     * The Block.
     */
    private BlockType block;
    /**
     * The Loot.
     */
    private ItemType loot;
    /**
     * The Loot.
     */
    private boolean isFullyGrown;

    /**
     * The Fuel.
     */
    @Nullable
    private ItemType fuel;

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
        this.isFullyGrown = false;
    }

    /**
     * Gets stage.
     *
     * @return the stage
     */
    public int getStage() {
        return stage;
    }

    /**
     * Sets stage.
     *
     * @param stage the stage
     */
    public void setStage(int stage) {
        this.stage = stage;
    }

    /**
     * Gets block.
     *
     * @return the block
     */
    public BlockType getBlock() {
        return block;
    }

    /**
     * Sets block.
     *
     * @param block the block
     */
    public void setBlock(BlockType block) {
        this.block = block;
    }

    /**
     * Gets loot.
     *
     * @return the loot
     */
    public ItemType getLoot() {
        return loot;
    }

    /**
     * Sets loot.
     *
     * @param loot the loot
     */
    public void setLoot(ItemType loot) {
        this.loot = loot;
    }

    /**
     * Is fully grown boolean.
     *
     * @return the boolean
     */
    public boolean isFullyGrown() {
        return isFullyGrown;
    }

    /**
     * Sets fully grown.
     *
     * @param fullyGrown the fully grown
     */
    public void setFullyGrown(boolean fullyGrown) {
        isFullyGrown = fullyGrown;
    }

    /**
     * Gets fuel.
     *
     * @return the fuel
     */
    @Nullable
    public ItemType getFuel() {
        return fuel;
    }

    /**
     * Sets fuel.
     *
     * @param fuel the fuel
     */
    public void setFuel(@Nullable ItemType fuel) {
        this.fuel = fuel;
    }

}

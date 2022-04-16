package dev.rachamon.rachamonharvestia.structure;

import dev.rachamon.rachamonharvestia.config.PlantsConfig;

import javax.annotation.Nullable;

/**
 * The type Plant data.
 */
public class PlantData extends PlantsConfig.PlantDataConfig {

    private boolean isFullyGrown;

    /**
     * Instantiates a new Plant data.
     *
     * @param block the block
     * @param fuel  the fuel
     * @param stage the stage
     */
    public PlantData(String block, @Nullable String fuel, int stage) {
        super(block, fuel, stage);
        this.isFullyGrown = false;
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


}

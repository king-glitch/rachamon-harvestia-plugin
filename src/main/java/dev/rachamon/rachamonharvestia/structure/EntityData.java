package dev.rachamon.rachamonharvestia.structure;

import org.spongepowered.api.entity.living.player.Player;

import java.util.UUID;

/**
 * The type Entity data.
 */
public class EntityData {
    /**
     * The Living.
     */
    private UUID living = null;
    /**
     * The Player.
     */
    private Player player = null;
    /**
     * The Plant data.
     */
    private PlantData plantData = null;

    private boolean isPlantSuccessfully = false;

    /**
     * Instantiates a new Entity data.
     *
     * @param living              the living
     * @param player              the player
     * @param plantData           the plant data
     * @param isPlantSuccessfully the is plant successfully
     */
    public EntityData(UUID living, Player player, PlantData plantData, boolean isPlantSuccessfully) {
        this.living = living;
        this.player = player;
        this.plantData = plantData;
        this.isPlantSuccessfully = isPlantSuccessfully;
    }

    /**
     * Gets living.
     *
     * @return the living
     */
    public UUID getLiving() {
        return living;
    }


    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets plant data.
     *
     * @return the plant data
     */
    public PlantData getPlantData() {
        return plantData;
    }

    /**
     * Is plant successfully boolean.
     *
     * @return the boolean
     */
    public boolean isPlantSuccessfully() {
        return isPlantSuccessfully;
    }
}

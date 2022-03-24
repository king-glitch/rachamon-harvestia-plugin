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
    public UUID living = null;
    /**
     * The Player.
     */
    public Player player = null;
    /**
     * The Plant data.
     */
    public PlantData plantData = null;

    /**
     * Instantiates a new Entity data.
     *
     * @param living    the living
     * @param player    the player
     * @param plantData the plant data
     */
    public EntityData(UUID living, Player player, PlantData plantData) {
        this.living = living;
        this.player = player;
        this.plantData = plantData;
    }
}

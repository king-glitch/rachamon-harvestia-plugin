package dev.rachamon.rachamonharvestia.structure;

import org.spongepowered.api.entity.living.player.Player;

import java.util.UUID;

public class EntityData {
    public UUID living = null;
    public Player player = null;
    public PlantData plantData = null;

    public EntityData(UUID living, Player player, PlantData plantData) {
        this.living = living;
        this.player = player;
        this.plantData = plantData;
    }
}

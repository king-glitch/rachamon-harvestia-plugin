package dev.rachamon.rachamonharvestia.structure;

import org.spongepowered.api.entity.living.player.Player;

import java.util.UUID;

public class EntityData {
    public UUID living = null;
    public Player player = null;

    public EntityData(UUID living, Player player) {
        this.living = living;
        this.player = player;
    }
}

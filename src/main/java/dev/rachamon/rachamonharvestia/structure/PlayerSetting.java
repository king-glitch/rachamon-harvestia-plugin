package dev.rachamon.rachamonharvestia.structure;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class PlayerSetting {
    @Setting(value = "is-auto-pickup-item")
    protected boolean isAutoPickupItem = true;
    @Setting(value = "is-auto-pickup-exp")
    protected boolean isAutoPickupExp = true;
    @Setting(value = "is-auto-replant")
    protected boolean isAutoReplant = true;

    public PlayerSetting() {
    }

    public PlayerSetting(boolean isAutoPickupItem, boolean isAutoPickupExp, boolean isAutoReplant) {
        this.isAutoPickupItem = isAutoPickupItem;
        this.isAutoPickupExp = isAutoPickupExp;
        this.isAutoReplant = isAutoReplant;
    }

    public boolean isAutoPickupItem() {
        return this.isAutoPickupItem;
    }

    public boolean isAutoPickupExp() {
        return this.isAutoPickupExp;
    }

    public boolean isAutoReplant() {
        return this.isAutoReplant;
    }
}

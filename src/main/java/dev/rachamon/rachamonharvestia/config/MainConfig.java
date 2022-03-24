package dev.rachamon.rachamonharvestia.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The type Main config.
 */
@ConfigSerializable
public class MainConfig {
    @Setting(value = "general", comment = "General Settings")
    private final GeneralCategorySetting mainCategorySetting = new GeneralCategorySetting();

    @Setting(value = "permission", comment = "Permission Settings")
    private final PermissionCategorySetting permissionCategorySetting = new PermissionCategorySetting();

    /**
     * Gets main category setting.
     *
     * @return the main category setting
     */
    public GeneralCategorySetting getMainCategorySetting() {
        return mainCategorySetting;
    }

    public PermissionCategorySetting getPermissionCategorySetting() {
        return permissionCategorySetting;
    }

    /**
     * The type General category setting.
     */
    @ConfigSerializable
    public static class GeneralCategorySetting {

        @Setting(comment = "is logging [default: true]", value = "is-debug")
        protected boolean isDebug = true;

        @Setting(comment = "is auto replant [default: true]", value = "is auto replant.")
        protected boolean isAutoReplant = true;

        @Setting(comment = "is all loot go to inventory by default? [default: true]", value = "is-inventory-by-default")
        protected boolean isInventoryByDefault = true;

        @Setting(comment = "is decrease seed [default: true]", value = "is-decrease-seed")
        protected boolean isDecreaseSeed = true;

        @Setting(comment = "is global exp auto pickup enable [default: true]", value = "is-auto-exp-pickup")
        protected boolean isAutoExpPickup = true;

        @Setting(comment = "is global item auto pickup enable [default: true]", value = "is-auto-item-pickup")
        protected boolean isAutoItemPickup = true;


        @Setting(comment = "blacklist block, not replant [default: melon_block]", value = "replant-block-blacklists")
        protected ArrayList<String> replantBlockBlacklists = new ArrayList<String>(Arrays.asList("minecraft:melon_block", "minecraft:pumpkin"));

        public boolean isDebug() {
            return isDebug;
        }

        public boolean isAutoReplant() {
            return isAutoReplant;
        }

        public boolean isInventoryByDefault() {
            return isInventoryByDefault;
        }

        public ArrayList<String> getReplantBlockBlacklists() {
            return replantBlockBlacklists;
        }

        public boolean isDecreaseSeed() {
            return isDecreaseSeed;
        }

        public boolean isAutoExpPickup() {
            return isAutoExpPickup;
        }

        public boolean isAutoItemPickup() {
            return isAutoItemPickup;
        }

    }

    @ConfigSerializable
    public static class PermissionCategorySetting {

        @Setting(comment = "is separate permission by plant [default: true]", value = "is-plant-separate-permission")
        protected boolean isPlantSeparatePermission = true;

        @Setting(comment = "auto replant permission [default: rachamonharvestia.auto.plant.base]", value = "auto-replant-permission")
        protected String autoReplantPermission = "rachamonharvestia.auto.plant.base";

        @Setting(comment = "auto item pickup permission [default: rachamonharvestia.auto.item.base]", value = "auto-item-pickup-permission")
        protected String autoItemPickupPermission = "rachamonharvestia.auto.item.base";

        @Setting(comment = "auto exp pickup permission [default: rachamonharvestia.auto.exp.base]", value = "auto-exp-pickup-permission")
        protected String autoExpPickupPermission = "rachamonharvestia.auto.exp.base";

        public boolean isPlantSeparatePermission() {
            return isPlantSeparatePermission;
        }

        public String getAutoReplantPermission() {
            return autoReplantPermission;
        }

        public String getAutoItemPickupPermission() {
            return autoItemPickupPermission;
        }

        public String getAutoExpPickupPermission() {
            return autoExpPickupPermission;
        }
    }
}
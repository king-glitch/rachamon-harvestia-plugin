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

    /**
     * Gets permission category setting.
     *
     * @return the permission category setting
     */
    public PermissionCategorySetting getPermissionCategorySetting() {
        return permissionCategorySetting;
    }

    /**
     * The type General category setting.
     */
    @ConfigSerializable
    public static class GeneralCategorySetting {

        /**
         * The Is debug.
         */
        @Setting(comment = "is logging [default: true]", value = "is-debug")
        protected boolean isDebug = true;

        /**
         * The Is auto replant.
         */
        @Setting(comment = "is auto replant [default: true]", value = "is auto replant.")
        protected boolean isAutoReplant = true;

        /**
         * The Is inventory by default.
         */
        @Setting(comment = "is all loot go to inventory by default? [default: true]", value = "is-inventory-by-default")
        protected boolean isInventoryByDefault = true;

        /**
         * The Is decrease seed.
         */
        @Setting(comment = "is decrease seed [default: true]", value = "is-decrease-seed")
        protected boolean isDecreaseSeed = true;

        /**
         * The Is auto exp pickup.
         */
        @Setting(comment = "is global exp auto pickup enable [default: true]", value = "is-auto-exp-pickup")
        protected boolean isAutoExpPickup = true;

        /**
         * The Is auto item pickup.
         */
        @Setting(comment = "is global item auto pickup enable [default: true]", value = "is-auto-item-pickup")
        protected boolean isAutoItemPickup = true;


        /**
         * The Replant block blacklists.
         */
        @Setting(comment = "blacklist block, not replant [default: melon_block]", value = "replant-block-blacklists")
        protected ArrayList<String> replantBlockBlacklists = new ArrayList<String>(Arrays.asList("minecraft:melon_block", "minecraft:pumpkin"));

        /**
         * Is debug boolean.
         *
         * @return the boolean
         */
        public boolean isDebug() {
            return isDebug;
        }

        /**
         * Is auto replant boolean.
         *
         * @return the boolean
         */
        public boolean isAutoReplant() {
            return isAutoReplant;
        }

        /**
         * Is inventory by default boolean.
         *
         * @return the boolean
         */
        public boolean isInventoryByDefault() {
            return isInventoryByDefault;
        }

        /**
         * Gets replant block blacklists.
         *
         * @return the replant block blacklists
         */
        public ArrayList<String> getReplantBlockBlacklists() {
            return replantBlockBlacklists;
        }

        /**
         * Is decrease seed boolean.
         *
         * @return the boolean
         */
        public boolean isDecreaseSeed() {
            return isDecreaseSeed;
        }

        /**
         * Is auto exp pickup boolean.
         *
         * @return the boolean
         */
        public boolean isAutoExpPickup() {
            return isAutoExpPickup;
        }

        /**
         * Is auto item pickup boolean.
         *
         * @return the boolean
         */
        public boolean isAutoItemPickup() {
            return isAutoItemPickup;
        }

    }

    /**
     * The type Permission category setting.
     */
    @ConfigSerializable
    public static class PermissionCategorySetting {

        /**
         * The Is plant separate permission.
         */
        @Setting(comment = "is separate permission by plant [default: true]", value = "is-plant-separate-permission")
        protected boolean isPlantSeparatePermission = true;

        /**
         * The Auto replant permission.
         */
        @Setting(comment = "auto replant permission [default: rachamonharvestia.auto.plant.base]", value = "auto-replant-permission")
        protected String autoReplantPermission = "rachamonharvestia.auto.plant.base";


        @Setting(comment = "auto replant base permission [default: rachamonharvestia.auto.plant]", value = "auto-base-replant-permission")
        protected String autoBaseReplantPermission = "rachamonharvestia.auto.plant";

        /**
         * The Auto item pickup permission.
         */
        @Setting(comment = "auto item pickup permission [default: rachamonharvestia.auto.item.base]", value = "auto-item-pickup-permission")
        protected String autoItemPickupPermission = "rachamonharvestia.auto.item.base";

        /**
         * The Auto exp pickup permission.
         */
        @Setting(comment = "auto exp pickup permission [default: rachamonharvestia.auto.exp.base]", value = "auto-exp-pickup-permission")
        protected String autoExpPickupPermission = "rachamonharvestia.auto.exp.base";

        /**
         * Is plant separate permission boolean.
         *
         * @return the boolean
         */
        public boolean isPlantSeparatePermission() {
            return isPlantSeparatePermission;
        }

        /**
         * Gets auto replant permission.
         *
         * @return the auto replant permission
         */
        public String getAutoReplantPermission() {
            return autoReplantPermission;
        }

        /**
         * Gets auto item pickup permission.
         *
         * @return the auto item pickup permission
         */
        public String getAutoItemPickupPermission() {
            return autoItemPickupPermission;
        }

        /**
         * Gets auto exp pickup permission.
         *
         * @return the auto exp pickup permission
         */
        public String getAutoExpPickupPermission() {
            return autoExpPickupPermission;
        }

        public String getAutoBaseReplantPermission() {
            return autoBaseReplantPermission;
        }
    }
}
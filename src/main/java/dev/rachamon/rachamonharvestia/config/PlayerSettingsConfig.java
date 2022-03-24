package dev.rachamon.rachamonharvestia.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Player settings config.
 */
@ConfigSerializable
public class PlayerSettingsConfig {
    /**
     * The Settings.
     */
    @Setting(value = "settings", comment = "Player Settings")
    protected Map<String, PlayerSetting> settings = new HashMap<>();

    /**
     * Gets settings.
     *
     * @return the settings
     */
    public Map<String, PlayerSetting> getSettings() {
        return this.settings;
    }

    /**
     * The type Player setting.
     */
    @ConfigSerializable
    public static class PlayerSetting {
        /**
         * The Is auto pickup item.
         */
        @Setting(value = "is-auto-pickup-item")
        protected boolean isAutoPickupItem = true;
        /**
         * The Is auto pickup exp.
         */
        @Setting(value = "is-auto-pickup-exp")
        protected boolean isAutoPickupExp = true;
        /**
         * The Is auto replant.
         */
        @Setting(value = "is-auto-replant")
        protected boolean isAutoReplant = true;

        /**
         * Instantiates a new Player setting.
         */
        public PlayerSetting() {
        }

        /**
         * Instantiates a new Player setting.
         *
         * @param isAutoPickupItem the is auto pickup item
         * @param isAutoPickupExp  the is auto pickup exp
         * @param isAutoReplant    the is auto replant
         */
        public PlayerSetting(boolean isAutoPickupItem, boolean isAutoPickupExp, boolean isAutoReplant) {
            this.isAutoPickupItem = isAutoPickupItem;
            this.isAutoPickupExp = isAutoPickupExp;
            this.isAutoReplant = isAutoReplant;
        }

        /**
         * Is auto pickup item boolean.
         *
         * @return the boolean
         */
        public boolean isAutoPickupItem() {
            return this.isAutoPickupItem;
        }

        /**
         * Is auto pickup exp boolean.
         *
         * @return the boolean
         */
        public boolean isAutoPickupExp() {
            return this.isAutoPickupExp;
        }

        /**
         * Is auto replant boolean.
         *
         * @return the boolean
         */
        public boolean isAutoReplant() {
            return this.isAutoReplant;
        }

        /**
         * Sets is auto pickup item.
         *
         * @param isAutoPickupItem the is auto pickup item
         */
        public void setIsAutoPickupItem(boolean isAutoPickupItem) {
            this.isAutoPickupItem = isAutoPickupItem;
        }

        /**
         * Sets is auto pickup exp.
         *
         * @param isAutoPickupExp the is auto pickup exp
         */
        public void setIsAutoPickupExp(boolean isAutoPickupExp) {
            this.isAutoPickupExp = isAutoPickupExp;
        }

        /**
         * Sets is auto replant.
         *
         * @param isAutoReplant the is auto replant
         */
        public void setIsAutoReplant(boolean isAutoReplant) {
            this.isAutoReplant = isAutoReplant;
        }
    }
}

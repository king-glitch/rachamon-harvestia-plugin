package dev.rachamon.rachamonharvestia.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The type Language config.
 */
@ConfigSerializable
public class LanguageConfig {
    @Setting(value = "general", comment = "General language")
    private final GeneralCategorySetting mainCategorySetting = new GeneralCategorySetting();

    @Setting(value = "commands", comment = "Command language")
    private final CommandCategorySetting commandCategorySetting = new CommandCategorySetting();

    /**
     * Gets command category setting.
     *
     * @return the command category setting
     */
    public CommandCategorySetting getCommandCategorySetting() {
        return commandCategorySetting;
    }

    /**
     * Gets main category setting.
     *
     * @return the main category setting
     */
    public GeneralCategorySetting getMainCategorySetting() {
        return mainCategorySetting;
    }

    /**
     * The type General category setting.
     */
    @ConfigSerializable
    public static class GeneralCategorySetting {


        /**
         * The Prefix.
         */
        @Setting(value = "prefix")
        protected String prefix = "&8[&c&lHarvestia&r&8]&7 ";
        /**
         * The Disabled.
         */
        @Setting(value = "disabled")
        protected String disabled = "&c&lDisabled&r";
        /**
         * The Enabled.
         */
        @Setting(value = "disabled")
        protected String enabled = "&c&lEnabled&r";

        /**
         * Gets prefix.
         *
         * @return the prefix
         */
        public String getPrefix() {
            return prefix;
        }

        /**
         * Gets disabled.
         *
         * @return the disabled
         */
        public String getDisabled() {
            return disabled;
        }

        /**
         * Gets enabled.
         *
         * @return the enabled
         */
        public String getEnabled() {
            return enabled;
        }
    }

    /**
     * The type Command category setting.
     */
    @ConfigSerializable
    public static class CommandCategorySetting {


        /**
         * The Successfully reloaded.
         */
        @Setting(value = "successfully-command-reloaded")
        protected String successfullyReloaded = "&aSuccessfully Reloaded.";
        /**
         * The Successfully command auto replant.
         */
        @Setting(value = "successfully-command-auto-replant")
        protected String successfullyCommandAutoReplant = "&aSuccessfully {type}&a&l auto replant.";
        /**
         * The Successfully command auto pickup item.
         */
        @Setting(value = "successfully-command-auto-pickup-item")
        protected String successfullyCommandAutoPickupItem = "&aSuccessfully {type}&a&l auto pickup item.";
        /**
         * The Successfully command auto pickup exp.
         */
        @Setting(value = "successfully-command-auto-pickup-exp")
        protected String successfullyCommandAutoPickupExp = "&aSuccessfully {type}&a&l auto pickup exp.";

        /**
         * Gets successfully reloaded.
         *
         * @return the successfully reloaded
         */
        public String getSuccessfullyReloaded() {
            return successfullyReloaded;
        }

        /**
         * Gets successfully command auto replant.
         *
         * @return the successfully command auto replant
         */
        public String getSuccessfullyCommandAutoReplant() {
            return successfullyCommandAutoReplant;
        }

        /**
         * Gets successfully command auto pickup item.
         *
         * @return the successfully command auto pickup item
         */
        public String getSuccessfullyCommandAutoPickupItem() {
            return successfullyCommandAutoPickupItem;
        }

        /**
         * Gets successfully command auto pickup exp.
         *
         * @return the successfully command auto pickup exp
         */
        public String getSuccessfullyCommandAutoPickupExp() {
            return successfullyCommandAutoPickupExp;
        }
    }

}
package dev.rachamon.rachamonharvestia.managers;

import dev.rachamon.rachamonharvestia.RachamonHarvestia;
import dev.rachamon.rachamonharvestia.config.PlayerSettingsConfig;

import java.util.UUID;

/**
 * The type Rachamon harvestia plugin manager.
 */
public class RachamonHarvestiaPluginManager {
    private final RachamonHarvestia plugin = RachamonHarvestia.getInstance();

    /**
     * Instantiates a new Rachamon harvestia plugin manager.
     */
    public RachamonHarvestiaPluginManager() {


    }

    /**
     * Gets player setting or create.
     *
     * @param uuid the uuid
     * @return the player setting or create
     */
    public PlayerSettingsConfig.PlayerSetting getPlayerSettingOrCreate(UUID uuid) {
        PlayerSettingsConfig.PlayerSetting playerSetting = RachamonHarvestia
                .getInstance()
                .getPlayerSettings()
                .getSettings()
                .get(uuid.toString());

        if (playerSetting == null) {
            return this.createPlayerSetting(uuid);
        }

        return playerSetting;
    }

    /**
     * Create player setting player settings config . player setting.
     *
     * @param uuid the uuid
     * @return the player settings config . player setting
     */
    public PlayerSettingsConfig.PlayerSetting createPlayerSetting(UUID uuid) {
        RachamonHarvestia
                .getInstance()
                .getPlayerSettings()
                .getSettings()
                .put(uuid.toString(), new PlayerSettingsConfig.PlayerSetting(true, true, true));
        this.save();
        return RachamonHarvestia.getInstance().getPlayerSettings().getSettings().get(uuid.toString());
    }

    /**
     * Toggle player auto replant setting player settings config . player setting.
     *
     * @param uuid the uuid
     * @return the player settings config . player setting
     */
    public PlayerSettingsConfig.PlayerSetting togglePlayerAutoReplantSetting(UUID uuid) {
        PlayerSettingsConfig.PlayerSetting setting = this.getPlayerSettingOrCreate(uuid);
        if (setting == null) {
            return this.createPlayerSetting(uuid);
        }
        setting.setIsAutoReplant(!setting.isAutoReplant());
        this.save();
        return setting;
    }

    /**
     * Toggle player auto pickup item setting player settings config . player setting.
     *
     * @param uuid the uuid
     * @return the player settings config . player setting
     */
    public PlayerSettingsConfig.PlayerSetting togglePlayerAutoPickupItemSetting(UUID uuid) {
        PlayerSettingsConfig.PlayerSetting setting = this.getPlayerSettingOrCreate(uuid);
        if (setting == null) {
            return this.createPlayerSetting(uuid);
        }
        setting.setIsAutoPickupItem(!setting.isAutoPickupItem());
        this.save();
        return setting;
    }

    /**
     * Toggle player auto pickup exp setting player settings config . player setting.
     *
     * @param uuid the uuid
     * @return the player settings config . player setting
     */
    public PlayerSettingsConfig.PlayerSetting togglePlayerAutoPickupExpSetting(UUID uuid) {
        PlayerSettingsConfig.PlayerSetting setting = this.getPlayerSettingOrCreate(uuid);
        if (setting == null) {
            return this.createPlayerSetting(uuid);
        }
        setting.setIsAutoPickupExp(!setting.isAutoPickupExp());
        this.save();
        return setting;
    }

    /**
     * Save.
     */
    public void save() {
        try {
            RachamonHarvestia.getInstance().getPlayerSettingsManager().save();
        } catch (Exception e) {
            e.printStackTrace();
            RachamonHarvestia.getInstance().getLogger().error("Something wrong while save player setting");
        }
    }
}

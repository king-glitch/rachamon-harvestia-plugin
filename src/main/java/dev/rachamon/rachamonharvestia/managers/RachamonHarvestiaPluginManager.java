package dev.rachamon.rachamonharvestia.managers;

import dev.rachamon.rachamonharvestia.RachamonHarvestia;
import dev.rachamon.rachamonharvestia.config.PlayerSettingsConfig;

import java.util.UUID;

public class RachamonHarvestiaPluginManager {
    private final RachamonHarvestia plugin = RachamonHarvestia.getInstance();

    public RachamonHarvestiaPluginManager() {


    }

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

    public PlayerSettingsConfig.PlayerSetting createPlayerSetting(UUID uuid) {
        try {
            RachamonHarvestia
                    .getInstance()
                    .getPlayerSettings()
                    .getSettings()
                    .put(uuid.toString(), new PlayerSettingsConfig.PlayerSetting(true, true, true));
            RachamonHarvestia.getInstance().getPlayerSettingsManager().save();
            return RachamonHarvestia.getInstance().getPlayerSettings().getSettings().get(uuid.toString());
        } catch (Exception e) {
            e.printStackTrace();
            RachamonHarvestia.getInstance().getLogger().error("Something wrong while save player setting");
        }
        return null;
    }
}

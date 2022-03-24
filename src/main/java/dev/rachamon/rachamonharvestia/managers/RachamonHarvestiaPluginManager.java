package dev.rachamon.rachamonharvestia.managers;

import dev.rachamon.rachamonharvestia.RachamonHarvestia;
import dev.rachamon.rachamonharvestia.structure.PlayerSetting;

import java.util.UUID;

public class RachamonHarvestiaPluginManager {
    private final RachamonHarvestia plugin = RachamonHarvestia.getInstance();

    public RachamonHarvestiaPluginManager() {


    }

    public PlayerSetting getPlayerSettingOrCreate(UUID uuid) {
        PlayerSetting playerSetting = RachamonHarvestia
                .getInstance()
                .getPlayerSettings()
                .getSettings()
                .get(uuid.toString());

        if (playerSetting == null) {
            return this.createPlayerSetting(uuid);
        }

        return playerSetting;
    }

    public PlayerSetting createPlayerSetting(UUID uuid) {
        try {
            RachamonHarvestia
                    .getInstance()
                    .getPlayerSettings()
                    .getSettings()
                    .put(uuid.toString(), new PlayerSetting(true, true, true));
            RachamonHarvestia.getInstance().getPlayerSettingsManager().save();
            return RachamonHarvestia.getInstance().getPlayerSettings().getSettings().get(uuid.toString());
        } catch (Exception e) {
            RachamonHarvestia.getInstance().getLogger().error("Something wrong while save player setting");
        }
        return null;
    }
}

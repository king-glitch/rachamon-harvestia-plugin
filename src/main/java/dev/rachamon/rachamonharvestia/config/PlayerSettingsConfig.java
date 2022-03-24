package dev.rachamon.rachamonharvestia.config;

import dev.rachamon.rachamonharvestia.structure.PlayerSetting;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.HashMap;
import java.util.Map;

@ConfigSerializable
public class PlayerSettingsConfig {
    @Setting(value = "settings", comment = "Player Settings")
    protected Map<String, PlayerSetting> settings = new HashMap<>();

    public Map<String, PlayerSetting> getSettings() {
        return this.settings;
    }
}

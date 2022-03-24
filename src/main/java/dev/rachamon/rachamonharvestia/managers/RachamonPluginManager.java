package dev.rachamon.rachamonharvestia.managers;

import dev.rachamon.api.sponge.config.SpongeAPIConfigFactory;
import dev.rachamon.api.sponge.implement.plugin.IRachamonPluginManager;
import dev.rachamon.rachamonharvestia.RachamonHarvestia;
import dev.rachamon.rachamonharvestia.RachamonHarvestiaModule;
import dev.rachamon.rachamonharvestia.config.LanguageConfig;
import dev.rachamon.rachamonharvestia.config.MainConfig;
import dev.rachamon.rachamonharvestia.config.PlayerSettingsConfig;
import dev.rachamon.rachamonharvestia.listeners.PlayerHarvestListener;
import org.spongepowered.api.Sponge;

public class RachamonPluginManager implements IRachamonPluginManager {
    private final RachamonHarvestia plugin = RachamonHarvestia.getInstance();

    @Override
    public void initialize() {
        this.plugin.setComponents(new RachamonHarvestia.Components());
        this.plugin.setPluginInjector(this.plugin
                .getSpongeInjector()
                .createChildInjector(new RachamonHarvestiaModule()));
        this.plugin.getSpongeInjector().injectMembers(this.plugin.getComponents());
        Sponge.getEventManager().registerListeners(this.plugin, new PlayerHarvestListener());
        this.plugin.setIsInitialized(true);
    }

    @Override
    public void preInitialize() {

    }

    @Override
    public void postInitialize() {
        this.reload();
    }

    @Override
    public void start() {

    }

    @Override
    public void reload() {
        try {
            this.configureConfigs();
        } catch (Exception ignored) {

        }

    }

    public void configureConfigs() {
        SpongeAPIConfigFactory<RachamonHarvestia, MainConfig> config = new SpongeAPIConfigFactory<>(this.plugin, "main.conf");
        SpongeAPIConfigFactory<RachamonHarvestia, LanguageConfig> language = new SpongeAPIConfigFactory<>(this.plugin, "language.conf");
        SpongeAPIConfigFactory<RachamonHarvestia, PlayerSettingsConfig> settings = new SpongeAPIConfigFactory<>(this.plugin, "player-settings.conf");

        this.plugin.setMainConfig(config);
        this.plugin.setMainLanguage(language);

        this.plugin.setConfig(config
                .setHeader("Main Config")
                .setClazz(new MainConfig())
                .setClazzType(MainConfig.class)
                .build());

        this.plugin.setLanguage(language
                .setHeader("Language Config")
                .setClazz(new LanguageConfig())
                .setClazzType(LanguageConfig.class)
                .build());

        this.plugin.setPlayerSettings(settings
                .setHeader("Player Settings")
                .setClazz(new PlayerSettingsConfig())
                .setClazzType(PlayerSettingsConfig.class)
                .build());
    }
}

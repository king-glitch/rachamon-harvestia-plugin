package dev.rachamon.rachamonharvestia.managers;

import dev.rachamon.api.sponge.implement.plugin.IRachamonPluginManager;
import dev.rachamon.rachamonharvestia.RachamonHarvestia;
import dev.rachamon.rachamonharvestia.RachamonHarvestiaModule;
import dev.rachamon.rachamonharvestia.listeners.PlayerHarvestListener;
import org.spongepowered.api.Sponge;

public class RachamonPluginManager implements IRachamonPluginManager {
    private final RachamonHarvestia plugin = RachamonHarvestia.getInstance();

    @Override
    public void initialize() {
        this.plugin.setComponents(new RachamonHarvestia.Components());
        this.plugin.setPluginInjector(this.plugin.getSpongeInjector().createChildInjector(new RachamonHarvestiaModule()));
        this.plugin.getSpongeInjector().injectMembers(this.plugin.getComponents());
        Sponge.getEventManager().registerListeners(this.plugin, new PlayerHarvestListener());
        this.plugin.setIsInitialized(true);
    }

    @Override
    public void preInitialize() {

    }

    @Override
    public void postInitialize() {

    }

    @Override
    public void start() {

    }

    @Override
    public void reload() {

    }
}

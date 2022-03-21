package dev.rachamon.rachamonharvestia;

import com.google.inject.Inject;
import com.google.inject.Injector;
import dev.rachamon.api.sponge.command.SpongeCommandService;
import dev.rachamon.api.sponge.implement.plugin.IRachamonPlugin;
import dev.rachamon.api.sponge.implement.plugin.IRachamonPluginManager;
import dev.rachamon.api.sponge.provider.RachamonSpongePluginProvider;
import dev.rachamon.api.sponge.util.LoggerUtil;
import dev.rachamon.rachamonharvestia.managers.RachamonHarvestiaPluginManager;
import dev.rachamon.rachamonharvestia.managers.RachamonPluginManager;
import ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.nio.file.Path;

@Plugin(id = "rachamonharvestia", name = "RachamonHarvestia", description = "Simple easy Harvest plugin", authors = {"Rachamon"})
public class RachamonHarvestia extends RachamonSpongePluginProvider implements IRachamonPlugin {

    private static RachamonHarvestia instance;
    private static boolean isInitialized = false;
    private Components components;
    private RachamonPluginManager pluginManager;

    @Inject
    private Game game;

    @Inject
    private PluginContainer container;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path directory;

    @Inject
    private GuiceObjectMapperFactory factory;

    @Inject
    private Injector harvestiaInjector;


    /**
     * The Sponge injector.
     */
    @Inject
    Injector spongeInjector;

    public RachamonHarvestia() {
        super("RachamonHarvestia", true);
    }

    public static RachamonHarvestia getInstance() {
        return RachamonHarvestia.instance;
    }

    @Override
    public LoggerUtil getLogger() {
        return this.logger;
    }

    @Override
    public void setLogger(LoggerUtil logger) {

    }

    @Override
    public GuiceObjectMapperFactory getFactory() {
        return this.factory;
    }

    @Override
    public Game getGame() {
        return this.game;
    }

    @Override
    public Path getDirectory() {
        return this.directory;
    }

    @Override
    public PluginContainer getContainer() {
        return this.container;
    }

    @Override
    public SpongeCommandService getCommandService() {
        return SpongeCommandService.getInstance();
    }

    @Override
    public IRachamonPluginManager getPluginManager() {
        return this.pluginManager;
    }

    public Components getComponents() {
        return this.components;
    }

    public RachamonHarvestiaPluginManager getHarvestiaManager() {
        return this.components.harvestiaManager;
    }

    @Override
    public boolean isInitialized() {
        return RachamonHarvestia.isInitialized;
    }

    @Override
    public void setInitialized(boolean isInitialized) {
        RachamonHarvestia.isInitialized = isInitialized;
    }

    @Override
    public Injector getPluginInjector() {
        return this.harvestiaInjector;
    }

    @Override
    public Injector getSpongeInjector() {
        return this.spongeInjector;
    }

    /**
     * The type Components.
     */
    public static class Components {
        @Inject
        private RachamonHarvestiaPluginManager harvestiaManager;
    }
}

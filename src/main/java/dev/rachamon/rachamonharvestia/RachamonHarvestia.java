package dev.rachamon.rachamonharvestia;

import com.google.inject.Inject;
import com.google.inject.Injector;
import dev.rachamon.api.sponge.command.SpongeCommandService;
import dev.rachamon.api.sponge.config.SpongeAPIConfigFactory;
import dev.rachamon.api.sponge.implement.plugin.IRachamonPlugin;
import dev.rachamon.api.sponge.implement.plugin.IRachamonPluginManager;
import dev.rachamon.api.sponge.provider.RachamonSpongePluginProvider;
import dev.rachamon.api.sponge.util.LoggerUtil;
import dev.rachamon.rachamonharvestia.config.LanguageConfig;
import dev.rachamon.rachamonharvestia.config.MainConfig;
import dev.rachamon.rachamonharvestia.config.PlayerSettingsConfig;
import dev.rachamon.rachamonharvestia.managers.RachamonHarvestiaPluginManager;
import dev.rachamon.rachamonharvestia.managers.RachamonPluginManager;
import ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.nio.file.Path;

@Plugin(id = "rachamonharvestia", name = "RachamonHarvestia", description = "Simple easy Harvest plugin", authors = {"Rachamon"})
public class RachamonHarvestia extends RachamonSpongePluginProvider implements IRachamonPlugin {

    private static RachamonHarvestia instance;
    private static boolean isInitialized = false;
    private Components components;
    private RachamonPluginManager pluginManager;
    private LoggerUtil logger;

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
    private Injector injector;
    @Inject
    private Injector pluginInjector;

    private SpongeAPIConfigFactory<RachamonHarvestia, MainConfig> config;
    private SpongeAPIConfigFactory<RachamonHarvestia, LanguageConfig> language;
    private SpongeAPIConfigFactory<RachamonHarvestia, PlayerSettingsConfig> settings;


    public RachamonHarvestia() {
        super("RachamonHarvestia", true);
    }

    public static RachamonHarvestia getInstance() {
        return RachamonHarvestia.instance;
    }

    /**
     * On pre initialize.
     *
     * @param event the event
     */
    @Listener
    public void onPreInitialize(GamePreInitializationEvent event) {
        instance = this;
        this.pluginManager = new RachamonPluginManager();
        this.setLogger(new LoggerUtil(Sponge.getServer()));
        this.getLogger().info("On Pre Initialize " + RachamonHarvestia.getName() + "...");
    }

    /**
     * On initialize.
     *
     * @param event the event
     */
    @Listener(order = Order.EARLY)
    public void onInitialize(GameInitializationEvent event) {
        RachamonHarvestia.getInstance().getLogger().info("On Initialize " + RachamonHarvestia.getName() + "...");
        RachamonHarvestia.getInstance().getPluginManager().initialize();
    }

    /**
     * On start.
     *
     * @param event the event
     */
    @Listener
    public void onStart(GameStartedServerEvent event) {
        if (!this.isInitialized()) return;
        RachamonHarvestia.getInstance().getLogger().info("On Start " + RachamonHarvestia.getName() + "...");
        RachamonHarvestia.getInstance().getPluginManager().start();
    }

    /**
     * On post initialize.
     *
     * @param event the event
     */
    @Listener
    public void onPostInitialize(GamePostInitializationEvent event) {
        RachamonHarvestia.getInstance().getLogger().info("On Post Initialize " + RachamonHarvestia.getName() + "...");
        RachamonHarvestia.getInstance().getPluginManager().postInitialize();
    }

    @Override
    public LoggerUtil getLogger() {
        return this.logger;
    }

    public void setLogger(LoggerUtil logger) {
        this.logger = logger;
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
        return this.getComponents().harvestiaManager;
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
        return this.injector;
    }

    @Override
    public Injector getSpongeInjector() {
        return this.pluginInjector;
    }

    public void setPluginInjector(Injector injector) {
        this.injector = injector;
    }

    public void setComponents(Components components) {
        this.components = components;
    }

    public void setIsInitialized(boolean isInitialized) {
        RachamonHarvestia.isInitialized = isInitialized;
    }

    public MainConfig getConfig() {
        return this.config.getRoot();
    }

    public void setMainConfig(SpongeAPIConfigFactory<RachamonHarvestia, MainConfig> config) {
        this.config = config;
    }

    public void setConfig(MainConfig config) {
        this.config.setClazz(config);
    }

    public LanguageConfig getLanguage() {
        return this.language.getRoot();
    }

    public void setLanguage(LanguageConfig language) {
        this.language.setClazz(language);
    }

    public void setMainLanguage(SpongeAPIConfigFactory<RachamonHarvestia, LanguageConfig> language) {
        this.language = language;
    }

    public PlayerSettingsConfig getPlayerSettings() {
        return this.settings.getRoot();
    }

    public SpongeAPIConfigFactory<RachamonHarvestia, PlayerSettingsConfig> getPlayerSettingsManager() {
        return this.settings;
    }

    public void setPlayerSettings(PlayerSettingsConfig settings) {
        this.settings.setClazz(settings);
    }

    public void setPlayerSettings(SpongeAPIConfigFactory<RachamonHarvestia, PlayerSettingsConfig> settings) {
        this.settings = settings;
    }


    public static class Components {
        @Inject
        private RachamonHarvestiaPluginManager harvestiaManager;
    }
}

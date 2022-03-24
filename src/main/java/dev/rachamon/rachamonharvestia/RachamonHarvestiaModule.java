package dev.rachamon.rachamonharvestia;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import dev.rachamon.rachamonharvestia.managers.RachamonHarvestiaPluginManager;

public class RachamonHarvestiaModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(RachamonHarvestiaPluginManager.class).in(Scopes.SINGLETON);
    }
}

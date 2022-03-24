package dev.rachamon.rachamonharvestia.commands.subcommands;

import dev.rachamon.api.sponge.implement.command.*;
import dev.rachamon.rachamonharvestia.RachamonHarvestia;
import dev.rachamon.rachamonharvestia.config.PlayerSettingsConfig;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

/**
 * The type Harvestia auto pickup item.
 */
@ICommandDescription("toggle auto replant")
@ICommandAliases({"item"})
@ICommandPermission("rachamonharvestia.command.toggle.item")
public class HarvestiaAutoPickupItem implements IPlayerCommand, IParameterizedCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) {
        PlayerSettingsConfig.PlayerSetting setting = RachamonHarvestia
                .getInstance()
                .getHarvestiaManager()
                .togglePlayerAutoPickupItemSetting(source.getUniqueId());
        RachamonHarvestia
                .getInstance()
                .sendMessage(source, RachamonHarvestia
                        .getInstance()
                        .getLanguage()
                        .getCommandCategorySetting()
                        .getSuccessfullyCommandAutoPickupItem()
                        .replaceAll("\\{type}", setting.isAutoPickupItem() ? RachamonHarvestia
                                .getInstance()
                                .getLanguage()
                                .getMainCategorySetting()
                                .getEnabled() : RachamonHarvestia
                                .getInstance()
                                .getLanguage()
                                .getMainCategorySetting()
                                .getDisabled()));
        return CommandResult.success();
    }

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{};
    }
}
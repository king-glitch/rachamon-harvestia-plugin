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
 * The type Harvestia auto pickup exp.
 */
@ICommandDescription("toggle auto replant")
@ICommandAliases({"exp"})
@ICommandPermission("rachamonharvestia.command.toggle.exp")
public class HarvestiaAutoPickupExp implements IPlayerCommand, IParameterizedCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) {
        PlayerSettingsConfig.PlayerSetting setting = RachamonHarvestia
                .getInstance()
                .getHarvestiaManager()
                .togglePlayerAutoPickupExpSetting(source.getUniqueId());

        RachamonHarvestia
                .getInstance()
                .sendMessage(source, RachamonHarvestia
                        .getInstance()
                        .getLanguage()
                        .getCommandCategorySetting()
                        .getSuccessfullyCommandAutoPickupExp()
                        .replaceAll("\\{type}", setting.isAutoPickupExp() ? RachamonHarvestia
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
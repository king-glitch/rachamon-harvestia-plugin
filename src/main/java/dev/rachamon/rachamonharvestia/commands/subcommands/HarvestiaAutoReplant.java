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
 * The type Harvestia auto replant.
 */
@ICommandDescription("toggle auto replant")
@ICommandAliases({"replant"})
@ICommandPermission("rachamonharvestia.command.toggle.replant")
public class HarvestiaAutoReplant implements IPlayerCommand, IParameterizedCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) {
        PlayerSettingsConfig.PlayerSetting setting = RachamonHarvestia
                .getInstance()
                .getHarvestiaManager()
                .togglePlayerAutoReplantSetting(source.getUniqueId());
        RachamonHarvestia
                .getInstance()
                .sendMessage(source, RachamonHarvestia
                        .getInstance()
                        .getLanguage()
                        .getCommandCategorySetting()
                        .getSuccessfullyCommandAutoReplant()
                        .replace("\\{type}", setting.isAutoReplant() ? RachamonHarvestia
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
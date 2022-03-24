package dev.rachamon.rachamonharvestia.commands.subcommands;

import dev.rachamon.api.sponge.implement.command.*;
import dev.rachamon.rachamonharvestia.RachamonHarvestia;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

/**
 * The type Harvestia reload.
 */
@ICommandDescription("reload plugin")
@ICommandAliases({"reload"})
@ICommandPermission("rachamonharvestia.command.reload")
public class HarvestiaReload implements ICommand, IParameterizedCommand {
    @Nonnull
    @Override
    public CommandResult execute(@Nonnull CommandSource source, @Nonnull CommandContext args) throws CommandException {
        RachamonHarvestia.getInstance().getPluginManager().reload();
        RachamonHarvestia
                .getInstance()
                .sendMessage(source, RachamonHarvestia
                        .getInstance()
                        .getLanguage()
                        .getCommandCategorySetting()
                        .getSuccessfullyReloaded());
        return CommandResult.success();
    }

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{GenericArguments.optional(GenericArguments.player(Text.of("name"))), GenericArguments.optional(GenericArguments.integer(Text.of("amount")))};
    }
}
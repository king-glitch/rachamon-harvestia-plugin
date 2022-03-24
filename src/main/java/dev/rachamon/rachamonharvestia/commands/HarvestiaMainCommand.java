package dev.rachamon.rachamonharvestia.commands;

import dev.rachamon.api.sponge.implement.command.*;
import dev.rachamon.rachamonharvestia.commands.subcommands.HarvestiaAutoPickupExp;
import dev.rachamon.rachamonharvestia.commands.subcommands.HarvestiaAutoPickupItem;
import dev.rachamon.rachamonharvestia.commands.subcommands.HarvestiaAutoReplant;
import dev.rachamon.rachamonharvestia.commands.subcommands.HarvestiaReload;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;

import javax.annotation.Nonnull;

/**
 * The type Harvestia main command.
 */
@ICommandChildren({HarvestiaReload.class, HarvestiaAutoPickupItem.class, HarvestiaAutoPickupExp.class, HarvestiaAutoReplant.class})
@ICommandAliases({"harvestia", "harvest", "rachamonharvestia"})
@ICommandHelpText(title = "Main Harvestia Help", command = "help")
@ICommandPermission("rachamonharvestia.command.base")
public class HarvestiaMainCommand implements ICommand {
    @Nonnull
    @Override
    public CommandResult execute(@Nonnull CommandSource source, @Nonnull CommandContext args) throws CommandException {
        return CommandResult.success();
    }
}
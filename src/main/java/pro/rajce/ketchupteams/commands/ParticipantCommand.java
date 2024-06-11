package pro.rajce.ketchupteams.commands;

import dev.nilkoush.thelibrary.commands.TheCommand;
import dev.nilkoush.thelibrary.libraries.commandapi.CommandAPICommand;
import dev.nilkoush.thelibrary.libraries.commandapi.executors.CommandArguments;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pro.rajce.ketchupteams.commands.arguments.GroupArgument;
import pro.rajce.ketchupteams.commands.arguments.PossiblePlayerArgument;
import pro.rajce.ketchupteams.managers.GroupManager;
import pro.rajce.ketchupteams.managers.ParticipantManager;
import pro.rajce.ketchupteams.objects.Group;
import pro.rajce.ketchupteams.objects.Participant;

public class ParticipantCommand extends TheCommand {

    @Override
    public void register() {
        new CommandAPICommand("participant")
                .withPermission("ketchupevent.command.participant")
                .withSubcommand(new CommandAPICommand("setgroup")
                        .withArguments(PossiblePlayerArgument.argument("target"), GroupArgument.argument("group"))
                        .executes(ParticipantCommand::setGroup))
                .withSubcommand(new CommandAPICommand("resetgroup")
                        .withArguments(PossiblePlayerArgument.argument("target"))
                        .executes(ParticipantCommand::resetGroup))
                .withSubcommand(new CommandAPICommand("togglesupervisor")
                        .withOptionalArguments(PossiblePlayerArgument.argument("target"))
                        .executesPlayer(ParticipantCommand::toggleSupervisor))
                .withSubcommand(new CommandAPICommand("togglespectator")
                        .withOptionalArguments(PossiblePlayerArgument.argument("target"))
                        .executesPlayer(ParticipantCommand::toggleSpectator))
                .register();
    }

    public static void setGroup(CommandSender commandSender, CommandArguments commandArguments) {
        Player target = (Player) commandArguments.get("target");
        assert target != null;
        Group group = (Group) commandArguments.get("group");
        assert group != null;

        ParticipantManager.getInstance().setGroup(target, group);
    }

    public static void resetGroup(CommandSender commandSender, CommandArguments commandArguments) {
        Player target = (Player) commandArguments.get("target");
        assert target != null;

        ParticipantManager.getInstance().resetGroup(target);
    }

    public static void toggleSupervisor(Player player, CommandArguments commandArguments) {
        Player target = (Player) commandArguments.getOptional("target").orElse(player);
        ParticipantManager.getInstance().toggleSupervisor(target);
    }

    public static void toggleSpectator(Player player, CommandArguments commandArguments) {
        Player target = (Player) commandArguments.getOptional("target").orElse(player);
        ParticipantManager.getInstance().toggleSpectator(target);
    }
}

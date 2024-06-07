package pro.rajce.ketchupevent.commands;

import dev.nilkoush.thelibrary.commands.TheCommand;
import dev.nilkoush.thelibrary.libraries.commandapi.CommandAPICommand;
import dev.nilkoush.thelibrary.libraries.commandapi.executors.CommandArguments;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pro.rajce.ketchupevent.commands.arguments.GroupArgument;
import pro.rajce.ketchupevent.commands.arguments.PossiblePlayerArgument;
import pro.rajce.ketchupevent.managers.GroupManager;
import pro.rajce.ketchupevent.managers.ParticipantManager;
import pro.rajce.ketchupevent.objects.Group;
import pro.rajce.ketchupevent.objects.Participant;

public class ParticipantCommand extends TheCommand {

    @Override
    public void register() {
        new CommandAPICommand("participant")
                .withPermission("ketchupevent.command.participant")
                .withSubcommand(new CommandAPICommand("setgroup")
                        .withArguments(PossiblePlayerArgument.argument("target"), GroupArgument.argument("group"))
                        .executes(ParticipantCommand::setGroup))
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

        GroupManager.getInstance().getGroups().forEach(possibleGroup -> possibleGroup.removeMember(target));
        Participant participant = ParticipantManager.getInstance().getParticipant(target);
        participant.setGroup(group);
        group.addMember(target);
    }

    public static void toggleSupervisor(Player player, CommandArguments commandArguments) {
        Player target = (Player) commandArguments.getOptional("target").orElse(player);
        Participant participant = ParticipantManager.getInstance().getParticipant(target);
        participant.setSupervisor(true);
    }

    public static void toggleSpectator(Player player, CommandArguments commandArguments) {
        Player target = (Player) commandArguments.getOptional("target").orElse(player);
        Participant participant = ParticipantManager.getInstance().getParticipant(target);
        participant.setSpectator(true);
    }
}

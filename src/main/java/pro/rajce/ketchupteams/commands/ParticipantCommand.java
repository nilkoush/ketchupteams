package pro.rajce.ketchupteams.commands;

import dev.nilkoush.thelibrary.commands.TheCommand;
import dev.nilkoush.thelibrary.libraries.commandapi.CommandAPICommand;
import dev.nilkoush.thelibrary.libraries.commandapi.arguments.IntegerArgument;
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
                .withSubcommand(new CommandAPICommand("setkills")
                        .withOptionalArguments(PossiblePlayerArgument.argument("target"), new IntegerArgument("kills"))
                        .executesPlayer(ParticipantCommand::setKills))
                .withSubcommand(new CommandAPICommand("setdeaths")
                        .withOptionalArguments(PossiblePlayerArgument.argument("target"), new IntegerArgument("deaths"))
                        .executesPlayer(ParticipantCommand::setDeaths))
                .withSubcommand(new CommandAPICommand("getkills")
                        .withOptionalArguments(PossiblePlayerArgument.argument("target"))
                        .executesPlayer(ParticipantCommand::getKills))
                .withSubcommand(new CommandAPICommand("getdeaths")
                        .withOptionalArguments(PossiblePlayerArgument.argument("target"))
                        .executesPlayer(ParticipantCommand::getDeaths))
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

    public static void setKills(Player player, CommandArguments commandArguments) {
        Player target = (Player) commandArguments.getOptional("target").orElse(player);
        Integer kills = (Integer) commandArguments.get("kills");
        ParticipantManager.getInstance().setKills(target, kills);
    }

    public static void setDeaths(Player player, CommandArguments commandArguments) {
        Player target = (Player) commandArguments.getOptional("target").orElse(player);
        Integer deaths = (Integer) commandArguments.get("deaths");
        ParticipantManager.getInstance().setDeaths(target, deaths);
    }

    public static void getKills(Player player, CommandArguments commandArguments) {
        Player target = (Player) commandArguments.getOptional("target").orElse(player);
        int kills = ParticipantManager.getInstance().getParticipant(target).getKills();
        player.sendMessage(target.getName() + "'s Kills: " + kills);
    }

    public static void getDeaths(Player player, CommandArguments commandArguments) {
        Player target = (Player) commandArguments.getOptional("target").orElse(player);
        int deaths = ParticipantManager.getInstance().getParticipant(target).getDeaths();
        player.sendMessage(target.getName() + "'s Deaths: " + deaths);
    }
}

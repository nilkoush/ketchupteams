package pro.rajce.ketchupteams.commands;

import dev.nilkoush.thelibrary.commands.TheCommand;
import dev.nilkoush.thelibrary.libraries.commandapi.CommandAPICommand;
import dev.nilkoush.thelibrary.libraries.commandapi.arguments.IntegerArgument;
import dev.nilkoush.thelibrary.libraries.commandapi.arguments.PotionEffectArgument;
import dev.nilkoush.thelibrary.libraries.commandapi.arguments.TimeArgument;
import dev.nilkoush.thelibrary.libraries.commandapi.executors.CommandArguments;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pro.rajce.ketchupteams.managers.EventManager;
import pro.rajce.ketchupteams.managers.ParticipantManager;
import pro.rajce.ketchupteams.managers.GroupManager;
import pro.rajce.ketchupteams.objects.Group;
import pro.rajce.ketchupteams.objects.Participant;
import pro.rajce.ketchupteams.utils.MessageUtil;

import java.util.List;

public class EventCommand extends TheCommand {

    @Override
    public void register() {
        new CommandAPICommand("event")
                .withPermission("ketchupevent.command.event")
                .withSubcommand(new CommandAPICommand("setlobby")
                        .executesPlayer(EventCommand::setLobby))
                .withSubcommand(new CommandAPICommand("teleportlobby")
                        .executesPlayer(EventCommand::teleportLobby))
                .withSubcommand(new CommandAPICommand("setgamespawn")
                        .executesPlayer(EventCommand::setGameSpawn))
                .withSubcommand(new CommandAPICommand("teleportgamespawn")
                        .executesPlayer(EventCommand::teleportGameSpawn))
                .withSubcommand(new CommandAPICommand("enablebuild")
                        .executes(EventCommand::enableBuild))
                .withSubcommand(new CommandAPICommand("disablebuild")
                        .executes(EventCommand::disableBuild))
                .withSubcommand(new CommandAPICommand("enablepvp")
                        .executes(EventCommand::enablePvp))
                .withSubcommand(new CommandAPICommand("disablepvp")
                        .executes(EventCommand::disablePvp))
                .withSubcommand(new CommandAPICommand("enablecollisions")
                        .executes(EventCommand::enableCollisions))
                .withSubcommand(new CommandAPICommand("disablecollisions")
                        .executes(EventCommand::disableCollisions))
                .withSubcommand(new CommandAPICommand("givehanditem")
                        .executesPlayer(EventCommand::giveHandItem))
                .withSubcommand(new CommandAPICommand("givepotioneffect")
                        .withArguments(new PotionEffectArgument("potion"), new TimeArgument("duration"), new IntegerArgument("strength"))
                        .executes(EventCommand::givePotionEffect))
                .withSubcommand(new CommandAPICommand("randomize")
                        .executes(EventCommand::randomize))
                .withSubcommand(new CommandAPICommand("resetgroups")
                        .executes(EventCommand::resetGroups))
                .register();
    }

    public static void setLobby(Player player, CommandArguments commandArguments) {
        EventManager.getInstance().setLobby(player);
    }

    public static void teleportLobby(Player player, CommandArguments commandArguments) {
        for (Player pp : Bukkit.getOnlinePlayers()) {
            pp.teleport(EventManager.getInstance().getLobby());
        }
        player.sendMessage(MessageUtil.getMessage("event.lobby.teleport"));
    }

    public static void setGameSpawn(Player player, CommandArguments commandArguments) {
        for (Group group : GroupManager.getInstance().getGroups()) {
            GroupManager.getInstance().setGameSpawn(group, player.getLocation());
        }
        player.sendMessage(MessageUtil.getMessage("event.spawn.set"));
    }

    public static void teleportGameSpawn(Player player, CommandArguments commandArguments) {
        for (Group group : GroupManager.getInstance().getGroups()) {
            for (Player pp : group.getMembers()) {
                pp.teleport(group.getGameSpawn());
            }
        }
        player.sendMessage(MessageUtil.getMessage("event.spawn.teleport"));
    }

    public static void enableBuild(CommandSender commandSender, CommandArguments commandArguments) {
        for (Group group : GroupManager.getInstance().getGroups()) {
            group.setCanBuild(true);
        }
        commandSender.sendMessage(MessageUtil.getMessage("event.build.enabled"));
    }

    public static void disableBuild(CommandSender commandSender, CommandArguments commandArguments) {
        for (Group group : GroupManager.getInstance().getGroups()) {
            group.setCanBuild(false);
        }
        commandSender.sendMessage(MessageUtil.getMessage("event.build.disabled"));
    }

    public static void enablePvp(CommandSender commandSender, CommandArguments commandArguments) {
        EventManager.getInstance().setPvp(true);
        commandSender.sendMessage(MessageUtil.getMessage("event.pvp.enabled"));
    }

    public static void disablePvp(CommandSender commandSender, CommandArguments commandArguments) {
        EventManager.getInstance().setPvp(false);
        commandSender.sendMessage(MessageUtil.getMessage("event.pvp.disabled"));
    }

    public static void giveHandItem(Player player, CommandArguments commandArguments) {
        for (Group group : GroupManager.getInstance().getGroups()) {
            for (Player pp : group.getMembers()) {
                pp.getInventory().addItem(player.getInventory().getItemInMainHand());
            }
        }
    }

    public static void givePotionEffect(CommandSender commandSender, CommandArguments commandArguments) {
        PotionEffectType potion = (PotionEffectType) commandArguments.get("potion");
        assert potion != null;
        Integer duration = (Integer) commandArguments.get("duration");
        assert duration != null;
        Integer strength = (Integer) commandArguments.get("strength");
        assert strength != null;

        for (Group group : GroupManager.getInstance().getGroups()) {
            for (Player pp : group.getMembers()) {
                pp.addPotionEffect(new PotionEffect(potion, duration * 10, strength));
            }
        }

        commandSender.sendMessage(MessageUtil.getMessage("event.potion-effect.given", potion.getName(), Integer.toString(duration), Integer.toString(strength)));
    }

    public static void enableCollisions(CommandSender sender, CommandArguments commandArguments) {
        for (Player pp : Bukkit.getOnlinePlayers()) {
            TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(pp.getUniqueId());
            TabAPI.getInstance().getNameTagManager().setCollisionRule(tabPlayer, true);
        }
    }

    public static void disableCollisions(CommandSender sender, CommandArguments commandArguments) {
        for (Player pp : Bukkit.getOnlinePlayers()) {
            TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(pp.getUniqueId());
            TabAPI.getInstance().getNameTagManager().setCollisionRule(tabPlayer, false);
        }
    }

    public static void randomize(CommandSender sender, CommandArguments commandArguments) {
        for (Player pp : Bukkit.getOnlinePlayers()) {
            Participant participant = ParticipantManager.getInstance().getParticipant(pp);
            List<Group> groups = GroupManager.getInstance().getGroups();
            int numberOfGroups = GroupManager.getInstance().getGroups().size();
            int participantsPerGroup = (int) (double) Math.floorDiv(Bukkit.getOnlinePlayers().size(), numberOfGroups);
            if (participant.getGroup() == null) {
                for (int i = 0; i < Bukkit.getOnlinePlayers().size(); i++) {
                    Group group = groups.get(i % numberOfGroups);
                    if (group.getMembers().size() < participantsPerGroup) {
                        if (!participant.isSupervisor()) {
                            ParticipantManager.getInstance().setGroup(pp, group);
                        }
                    }
                }
            }
        }
    }

    public static void resetGroups(CommandSender sender, CommandArguments commandArguments) {
        for (Player pp : Bukkit.getOnlinePlayers()) {
            ParticipantManager.getInstance().resetGroup(pp);
        }
    }
}

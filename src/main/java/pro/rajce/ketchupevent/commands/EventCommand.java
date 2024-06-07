package pro.rajce.ketchupevent.commands;

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
import pro.rajce.ketchupevent.managers.EventManager;
import pro.rajce.ketchupevent.managers.ParticipantManager;
import pro.rajce.ketchupevent.managers.GroupManager;
import pro.rajce.ketchupevent.objects.Group;
import pro.rajce.ketchupevent.objects.Participant;
import pro.rajce.ketchupevent.utils.MessageUtil;

public class EventCommand extends TheCommand {

    @Override
    public void register() {
        new CommandAPICommand("event")
                .withPermission("ketchupevent.command.event")
                .withSubcommand(new CommandAPICommand("enable")
                        .executes(EventCommand::enable))
                .withSubcommand(new CommandAPICommand("disable")
                        .executes(EventCommand::disable))
                .withSubcommand(new CommandAPICommand("start")
                        .executes(EventCommand::start))
                .withSubcommand(new CommandAPICommand("stop")
                        .executes(EventCommand::stop))
                .withSubcommand(new CommandAPICommand("setspawn")
                        .executesPlayer(EventCommand::setSpawn))
                .withSubcommand(new CommandAPICommand("teleportspawn")
                        .executesPlayer(EventCommand::teleportSpawn))
                .withSubcommand(new CommandAPICommand("enablebuild")
                        .executes(EventCommand::enableBuild))
                .withSubcommand(new CommandAPICommand("disablebuild")
                        .executes(EventCommand::disableBuild))
                .withSubcommand(new CommandAPICommand("givehanditem")
                        .executesPlayer(EventCommand::giveHandItem))
                .withSubcommand(new CommandAPICommand("givepotioneffect")
                        .withArguments(new PotionEffectArgument("potion"), new TimeArgument("duration"), new IntegerArgument("strength"))
                        .executes(EventCommand::givePotionEffect))
                .withSubcommand(new CommandAPICommand("enablecollisions")
                        .executes(EventCommand::enableCollisions))
                .withSubcommand(new CommandAPICommand("disablecollisions")
                        .executes(EventCommand::disableCollisions))
                .withSubcommand(new CommandAPICommand("randomize")
                        .executes(EventCommand::randomize))
                .register();
    }

    public static void enable(CommandSender commandSender, CommandArguments commandArguments) {
        EventManager.getInstance().enable();
    }

    public static void disable(CommandSender commandSender, CommandArguments commandArguments) {
        EventManager.getInstance().disable();
    }

    public static void start(CommandSender commandSender, CommandArguments commandArguments) {
        EventManager.getInstance().start();
    }

    public static void stop(CommandSender commandSender, CommandArguments commandArguments) {
        EventManager.getInstance().stop();
    }

    public static void setSpawn(Player player, CommandArguments commandArguments) {
        EventManager.getInstance().setSpawn(player);
        player.sendMessage(MessageUtil.getMessage("event.spawn.set"));
    }

    public static void teleportSpawn(Player player, CommandArguments commandArguments) {
        for (Group group : GroupManager.getInstance().getGroups()) {
            for (Player pp : group.getMembers()) {
                pp.teleport(EventManager.getInstance().getSpawn());
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
                pp.addPotionEffect(new PotionEffect(potion, duration, strength));
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
            if (participant.isSupervisor() || participant.isSpectator()) continue;

        }
    }
}

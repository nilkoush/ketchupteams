package pro.rajce.ketchupteams.commands;

import dev.nilkoush.thelibrary.commands.TheCommand;
import dev.nilkoush.thelibrary.libraries.commandapi.CommandAPICommand;
import dev.nilkoush.thelibrary.libraries.commandapi.arguments.*;
import dev.nilkoush.thelibrary.libraries.commandapi.executors.CommandArguments;
import dev.nilkoush.thelibrary.utils.FormatUtil;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pro.rajce.ketchupteams.commands.arguments.GroupArgument;
import pro.rajce.ketchupteams.objects.Group;
import pro.rajce.ketchupteams.managers.GroupManager;
import pro.rajce.ketchupteams.utils.MessageUtil;

public class GroupCommand extends TheCommand {

    @Override
    public void register() {
        new CommandAPICommand("group")
                .withPermission("ketchupteams.command.group")
                .withSubcommand(new CommandAPICommand("list")
                        .executes(GroupCommand::list))
                .withSubcommand(new CommandAPICommand("create")
                        .withArguments(new StringArgument("name"))
                        .executes(GroupCommand::create))
                .withSubcommand(new CommandAPICommand("delete")
                        .withArguments(GroupArgument.argument("group"))
                        .executes(GroupCommand::delete))
                .withSubcommand(new CommandAPICommand("setcolor")
                        .withArguments(GroupArgument.argument("group"), new AdventureChatColorArgument("color"))
                        .executes(GroupCommand::setColor))
                .withSubcommand(new CommandAPICommand("enablebuild")
                        .withArguments(GroupArgument.argument("group"))
                        .executes(GroupCommand::enableBuild))
                .withSubcommand(new CommandAPICommand("disablebuild")
                        .withArguments(GroupArgument.argument("group"))
                        .executes(GroupCommand::disableBuild))
                .withSubcommand(new CommandAPICommand("givehanditem")
                        .withArguments(GroupArgument.argument("group"))
                        .executesPlayer(GroupCommand::giveHandItem))
                .withSubcommand(new CommandAPICommand("givepotioneffect")
                        .withArguments(GroupArgument.argument("group"), new PotionEffectArgument("potion"), new TimeArgument("duration"), new IntegerArgument("strength"))
                        .executes(GroupCommand::givePotionEffect))
                .register();
    }

    public static void list(CommandSender commandSender, CommandArguments commandArguments) {
        for (Group group : GroupManager.getInstance().getGroups()) {
            commandSender.sendMessage(FormatUtil.format("<gray>- " + group.getName()));
        }
    }

    public static void create(CommandSender commandSender, CommandArguments commandArguments) {
        String name = (String) commandArguments.get("name");
        assert name != null;

        GroupManager.getInstance().createGroup(name);
    }

    public static void delete(CommandSender commandSender, CommandArguments commandArguments) {
        Group group = (Group) commandArguments.get("group");
        assert group != null;

        GroupManager.getInstance().deleteGroup(group.getName());
    }

    public static void setColor(CommandSender commandSender, CommandArguments commandArguments) {
        Group group = (Group) commandArguments.get("group");
        assert group != null;
        NamedTextColor color = (NamedTextColor) commandArguments.get("color");
        assert color != null;

        GroupManager.getInstance().setColor(group, color);
    }

    public static void enableBuild(CommandSender commandSender, CommandArguments commandArguments) {
        Group group = (Group) commandArguments.get("group");
        assert group != null;
        GroupManager.getInstance().setCanBuild(group, true);
        commandSender.sendMessage(MessageUtil.getMessage("group.build.enabled", group.getName()));
    }

    public static void disableBuild(CommandSender commandSender, CommandArguments commandArguments) {
        Group group = (Group) commandArguments.get("group");
        assert group != null;;
        GroupManager.getInstance().setCanBuild(group, false);
        commandSender.sendMessage(MessageUtil.getMessage("group.build.disabled", group.getName()));
    }

    public static void enableIntraPvp(CommandSender commandSender, CommandArguments commandArguments) {
        Group group = (Group) commandArguments.get("group");
        assert group != null;
        GroupManager.getInstance().setIntraPvp(group, true);
        commandSender.sendMessage(MessageUtil.getMessage("group.intra-pvp.enabled", group.getName()));
    }

    public static void disableIntraPvp(CommandSender commandSender, CommandArguments commandArguments) {
        Group group = (Group) commandArguments.get("group");
        assert group != null;;
        GroupManager.getInstance().setIntraPvp(group, false);
        commandSender.sendMessage(MessageUtil.getMessage("group.intra-pvp.disabled", group.getName()));
    }

    public static void giveHandItem(Player player, CommandArguments commandArguments) {
        Group group = (Group) commandArguments.get("group");
        assert group != null;
        for (Player pp : group.getMembers()) {
            pp.getInventory().addItem(player.getInventory().getItemInMainHand());
        }
    }

    public static void givePotionEffect(CommandSender commandSender, CommandArguments commandArguments) {
        Group group = (Group) commandArguments.get("group");
        assert group != null;
        PotionEffectType potion = (PotionEffectType) commandArguments.get("potion");
        assert potion != null;
        Integer duration = (Integer) commandArguments.get("duration");
        assert duration != null;
        Integer strength = (Integer) commandArguments.get("strength");
        assert strength != null;

        for (Player pp : group.getMembers()) {
            pp.addPotionEffect(new PotionEffect(potion, duration, strength));
        }

        commandSender.sendMessage(MessageUtil.getMessage("group.potion-effect.given", group.getName(), potion.getName(), Integer.toString(duration), Integer.toString(strength)));
    }
}

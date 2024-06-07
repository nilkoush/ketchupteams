package pro.rajce.ketchupteams.commands;

import dev.nilkoush.thelibrary.commands.TheCommand;
import dev.nilkoush.thelibrary.libraries.commandapi.CommandAPICommand;
import pro.rajce.ketchupteams.KetchupTeamsPlugin;
import pro.rajce.ketchupteams.utils.MessageUtil;

public class CoreCommand extends TheCommand {

    @Override
    public void register() {
        new CommandAPICommand("ketchupteams")
                .withPermission("ketchupteams.command.core")
                .executes((commandSender, commandArguments) -> {
                    KetchupTeamsPlugin.getInstance().getConfigFile().reload();
                    KetchupTeamsPlugin.getInstance().getMessagesFile().reload();
                    KetchupTeamsPlugin.getInstance().getGroupsFile().reload();
                    commandSender.sendMessage(MessageUtil.getMessage("general.reloaded"));
                })
                .register();
    }
}

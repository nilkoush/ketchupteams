package pro.rajce.ketchupevent.commands;

import dev.nilkoush.thelibrary.commands.TheCommand;
import dev.nilkoush.thelibrary.libraries.commandapi.CommandAPICommand;
import pro.rajce.ketchupevent.KetchupEventPlugin;
import pro.rajce.ketchupevent.utils.MessageUtil;

public class CoreCommand extends TheCommand {

    @Override
    public void register() {
        new CommandAPICommand("ketchupevent")
                .executes((commandSender, commandArguments) -> {
                    KetchupEventPlugin.getInstance().getConfigFile().reload();
                    KetchupEventPlugin.getInstance().getMessagesFile().reload();
                    KetchupEventPlugin.getInstance().getGroupsFile().reload();
                    commandSender.sendMessage(MessageUtil.getMessage("general.reloaded"));
                })
                .register();
    }
}

package pro.rajce.ketchupteams.utils;

import dev.nilkoush.thelibrary.utils.FileBuilder;
import dev.nilkoush.thelibrary.utils.FormatUtil;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import pro.rajce.ketchupteams.KetchupTeamsPlugin;

@UtilityClass
public class MessageUtil {

    public Component getMessage(String id) {
        return getMessage(id, (String[]) null);
    }

    public Component getMessage(String id, String... args) {
        FileBuilder messagesFile = KetchupTeamsPlugin.getInstance().getMessagesFile();

        if (!messagesFile.isSet(id)) {
            return FormatUtil.format("<red>Message with id '" + id + "' does not exist.</red>");
        }

        String message = messagesFile.getString(id);

        if (messagesFile.isSet("general.prefix")) {
            message = message.replace("<prefix>", messagesFile.getString("general.prefix"));
        }

        if (messagesFile.isConfigurationSection("general.colors")) {
            for (String color : messagesFile.getConfigurationSection("general.colors").getKeys(false)) {
                message = message
                        .replace(color, messagesFile.getString("general.colors." + color));
            }
        }

        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace("<" + i + ">", args[i]);
            }
        }

        return FormatUtil.format(message);
    }

}

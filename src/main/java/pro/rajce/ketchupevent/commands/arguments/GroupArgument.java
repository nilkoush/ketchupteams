package pro.rajce.ketchupevent.commands.arguments;

import dev.nilkoush.thelibrary.libraries.commandapi.arguments.Argument;
import dev.nilkoush.thelibrary.libraries.commandapi.arguments.ArgumentSuggestions;
import dev.nilkoush.thelibrary.libraries.commandapi.arguments.CustomArgument;
import dev.nilkoush.thelibrary.libraries.commandapi.arguments.StringArgument;
import pro.rajce.ketchupevent.objects.Group;
import pro.rajce.ketchupevent.managers.GroupManager;

public class GroupArgument {

    public static Argument<Group> argument(String nodeName) {
        return new CustomArgument<>(new StringArgument(nodeName), info -> {
            Group group = GroupManager.getInstance().getGroupByName(info.input());
            if (group != null) {
                return group;
            }
            throw CustomArgument.CustomArgumentException.fromMessageBuilder(new CustomArgument.MessageBuilder("Unknown group: ").appendArgInput());
        }).replaceSuggestions(ArgumentSuggestions.strings(info ->
                GroupManager.getInstance().getGroupNames().toArray(String[]::new))
        );
    }
}

package pro.rajce.ketchupevent.commands.arguments;

import dev.nilkoush.thelibrary.libraries.commandapi.arguments.Argument;
import dev.nilkoush.thelibrary.libraries.commandapi.arguments.ArgumentSuggestions;
import dev.nilkoush.thelibrary.libraries.commandapi.arguments.CustomArgument;
import dev.nilkoush.thelibrary.libraries.commandapi.arguments.StringArgument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PossiblePlayerArgument {

    public static Argument<Player> argument(String nodeName) {
        return new CustomArgument<>(new StringArgument(nodeName), info -> {
            for (String possiblePlayer : Bukkit.getOnlinePlayers().stream().map(Player::getName).toList()) {
                if (possiblePlayer.startsWith(info.input())) {
                    return Bukkit.getPlayer(info.input());
                }
            }
            throw CustomArgument.CustomArgumentException.fromMessageBuilder(new CustomArgument.MessageBuilder("Unknown player: ").appendArgInput());
        }).replaceSuggestions(ArgumentSuggestions.strings(info ->
                Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new))
        );
    }
}

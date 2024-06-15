package pro.rajce.ketchupteams.managers;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pro.rajce.ketchupteams.KetchupTeamsPlugin;
import pro.rajce.ketchupteams.utils.MessageUtil;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventManager {

    private static EventManager INSTANCE;
    private boolean isPvp;

    public void setLobby(Player player) {
        KetchupTeamsPlugin.getInstance().getConfigFile().set("lobby", player.getLocation());
        KetchupTeamsPlugin.getInstance().getConfigFile().save();
        player.sendMessage(MessageUtil.getMessage("event.lobby.set"));
    }

    public Location getLobby() {
        return (Location) KetchupTeamsPlugin.getInstance().getConfigFile().get("lobby");
    }

    public void enablePvp() {
        isPvp = true;
    }

    public void disablePvp() {
        isPvp = false;
    }

    public static EventManager getInstance() {
        if (INSTANCE == null) {
            return INSTANCE = new EventManager();
        }
        return INSTANCE;
    }
}

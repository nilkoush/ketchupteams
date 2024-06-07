package pro.rajce.ketchupteams.managers;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pro.rajce.ketchupteams.KetchupTeamsPlugin;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationManager {

    private static LocationManager INSTANCE;
    private boolean enabled = false;
    private boolean running = false;

    public void setSpawn(Player player) {
        KetchupTeamsPlugin.getInstance().getConfigFile().set("spawn", player.getLocation());
        KetchupTeamsPlugin.getInstance().getConfigFile().save();
    }

    public Location getSpawn() {
        return (Location) KetchupTeamsPlugin.getInstance().getConfigFile().get("spawn");
    }

    public void setGameSpawn(Player player) {
        KetchupTeamsPlugin.getInstance().getConfigFile().set("spawn", player.getLocation());
        KetchupTeamsPlugin.getInstance().getConfigFile().save();
    }

    public Location getGameSpawn() {
        return (Location) KetchupTeamsPlugin.getInstance().getConfigFile().get("spawn");
    }

    public static LocationManager getInstance() {
        if (INSTANCE == null) {
            return INSTANCE = new LocationManager();
        }
        return INSTANCE;
    }
}

package pro.rajce.ketchupevent.managers;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.nametag.NameTagManager;
import me.neznamy.tab.api.tablist.TabListFormatManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import pro.rajce.ketchupevent.KetchupEventPlugin;
import pro.rajce.ketchupevent.listeners.EventListener;
import pro.rajce.ketchupevent.objects.Group;
import pro.rajce.ketchupevent.objects.Participant;
import pro.rajce.ketchupevent.utils.MessageUtil;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationManager {

    private static LocationManager INSTANCE;
    private boolean enabled = false;
    private boolean running = false;

    public void setSpawn(Player player) {
        KetchupEventPlugin.getInstance().getConfigFile().set("spawn", player.getLocation());
        KetchupEventPlugin.getInstance().getConfigFile().save();
    }

    public Location getSpawn() {
        return (Location) KetchupEventPlugin.getInstance().getConfigFile().get("spawn");
    }

    public void setGameSpawn(Player player) {
        KetchupEventPlugin.getInstance().getConfigFile().set("spawn", player.getLocation());
        KetchupEventPlugin.getInstance().getConfigFile().save();
    }

    public Location getGameSpawn() {
        return (Location) KetchupEventPlugin.getInstance().getConfigFile().get("spawn");
    }

    public static LocationManager getInstance() {
        if (INSTANCE == null) {
            return INSTANCE = new LocationManager();
        }
        return INSTANCE;
    }
}

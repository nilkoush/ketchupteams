package pro.rajce.ketchupevent.utils;

import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.nametag.NameTagManager;
import org.bukkit.entity.Player;

public class NicknameUtil {

    public void setColor(Player player, String hexColor) {
        TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(player.getUniqueId());
        if (tabPlayer != null) {
            NameTagManager nameTagManager = TabAPI.getInstance().getNameTagManager();

            TabAPI.getInstance().getNameTagManager().setPrefix(tabPlayer, hexColor);
            TabAPI.getInstance().getTabListFormatManager().setPrefix(tabPlayer, hexColor);
        }
    }

}

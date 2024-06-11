package pro.rajce.ketchupteams.utils;

import lombok.experimental.UtilityClass;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.nametag.NameTagManager;
import me.neznamy.tab.api.tablist.TabListFormatManager;
import org.bukkit.entity.Player;

@UtilityClass
public class NicknameUtil {

    public void setColor(Player player, String hexColor) {
        TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(player.getUniqueId());
        if (tabPlayer != null) {
            NameTagManager nameTagManager = TabAPI.getInstance().getNameTagManager();
            TabListFormatManager tabListFormatManager = TabAPI.getInstance().getTabListFormatManager();
            if (nameTagManager != null) {
                nameTagManager.setPrefix(tabPlayer, hexColor);
            }
            if (tabListFormatManager != null) {
                tabListFormatManager.setPrefix(tabPlayer, hexColor);
            }
        }
    }

    public void reset(Player player) {
        TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(player.getUniqueId());
        if (tabPlayer != null) {
            NameTagManager nameTagManager = TabAPI.getInstance().getNameTagManager();
            TabListFormatManager tabListFormatManager = TabAPI.getInstance().getTabListFormatManager();
            if (nameTagManager != null) {
                nameTagManager.setPrefix(tabPlayer, nameTagManager.getOriginalPrefix(tabPlayer));
            }
            if (tabListFormatManager != null) {
                tabListFormatManager.setPrefix(tabPlayer, tabListFormatManager.getOriginalPrefix(tabPlayer));
            }
        }
    }

}

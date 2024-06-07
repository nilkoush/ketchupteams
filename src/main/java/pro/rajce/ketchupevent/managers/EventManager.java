package pro.rajce.ketchupevent.managers;

import lombok.*;
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
public class EventManager {

    private static EventManager INSTANCE;
    private boolean enabled = false;
    private boolean running = false;

    public void enable() {
        if (enabled) {
            Bukkit.broadcast(MessageUtil.getMessage("event.is-enabled"));
            return;
        }
        enabled = true;
        Bukkit.broadcast(MessageUtil.getMessage("event.enabled"));
        Bukkit.getPluginManager().registerEvents(new EventListener(), KetchupEventPlugin.getInstance());
    }

    public void disable() {
        if (!enabled) {
            Bukkit.broadcast(MessageUtil.getMessage("event.is-disabled"));
            return;
        }
        stop();
        ParticipantManager.getInstance().clearCache();
        HandlerList.unregisterAll(KetchupEventPlugin.getInstance());
        Bukkit.broadcast(MessageUtil.getMessage("event.disabled"));
        enabled = false;
    }

    public void start() {
        if (!enabled) {
            Bukkit.broadcast(MessageUtil.getMessage("event.is-disabled"));
            return;
        }
        if (running) {
            Bukkit.broadcast(MessageUtil.getMessage("event.is-already-running"));
            return;
        }

        running = true;
        for (Player pp : Bukkit.getOnlinePlayers()) {
            Participant participant = ParticipantManager.getInstance().getParticipant(pp);
            Group group = participant.getGroup();
            if (group == null) return;
        }
    }

    public void stop() {
        if (!enabled) {
            Bukkit.broadcast(MessageUtil.getMessage("event.is-disabled"));
            return;
        }
        if (!running) {
            Bukkit.broadcast(MessageUtil.getMessage("event.is-not-running"));
            return;
        }

        for (Player pp : Bukkit.getOnlinePlayers()) {
            TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(pp.getUniqueId());
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
        running = false;
    }

    public static EventManager getInstance() {
        if (INSTANCE == null) {
            return INSTANCE = new EventManager();
        }
        return INSTANCE;
    }
}

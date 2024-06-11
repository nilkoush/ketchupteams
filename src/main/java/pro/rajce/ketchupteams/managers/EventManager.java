package pro.rajce.ketchupteams.managers;

import lombok.*;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.nametag.NameTagManager;
import me.neznamy.tab.api.tablist.TabListFormatManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import pro.rajce.ketchupteams.KetchupTeamsPlugin;
import pro.rajce.ketchupteams.listeners.EventListener;
import pro.rajce.ketchupteams.objects.Group;
import pro.rajce.ketchupteams.objects.Participant;
import pro.rajce.ketchupteams.utils.MessageUtil;
import pro.rajce.ketchupteams.utils.NicknameUtil;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventManager {

    private static EventManager INSTANCE;
    private boolean running = false;

    public void start() {
        if (running) {
            Bukkit.broadcast(MessageUtil.getMessage("event.is-already-running"));
            return;
        }

        running = true;
        for (Player pp : Bukkit.getOnlinePlayers()) {
            Participant participant = ParticipantManager.getInstance().getParticipant(pp);
            Group group = participant.getGroup();
            if (group == null) return;
            NicknameUtil.setColor(pp, group.getColor().asHexString());
        }
    }

    public void stop() {
        if (!running) {
            Bukkit.broadcast(MessageUtil.getMessage("event.is-not-running"));
            return;
        }

        for (Player pp : Bukkit.getOnlinePlayers()) {
            NicknameUtil.reset(pp);
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

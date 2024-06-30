package pro.rajce.ketchupteams.listeners;

import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.event.player.PlayerLoadEvent;
import me.neznamy.tab.api.event.plugin.TabLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pro.rajce.ketchupteams.managers.ParticipantManager;
import pro.rajce.ketchupteams.objects.Group;
import pro.rajce.ketchupteams.objects.Participant;
import pro.rajce.ketchupteams.utils.NicknameUtil;

import java.util.Objects;

public class TabPluginListener {

    public TabPluginListener() {
        Objects.requireNonNull(TabAPI.getInstance().getEventBus()).register(TabLoadEvent.class, event -> {
            for (Player pp : Bukkit.getOnlinePlayers()) {
                Participant participant = ParticipantManager.getInstance().getParticipant(pp);
                Group group = participant.getGroup();
                if (group != null) {
                    NicknameUtil.setColor(pp, group.getColor().asHexString());
                }
            }
        });
    }

}

package pro.rajce.ketchupteams.managers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import pro.rajce.ketchupteams.objects.Participant;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticipantManager {

    private static ParticipantManager INSTANCE;
    private final Map<UUID, Participant> cache = new HashMap<>();

    public Participant getParticipant(Player player) {
        return cache.computeIfAbsent(player.getUniqueId(), uuid -> new Participant(player));
    }

    public void clearCache() {
        cache.clear();
    }

    public static ParticipantManager getInstance() {
        if (INSTANCE == null) {
            return INSTANCE = new ParticipantManager();
        }
        return INSTANCE;
    }
}

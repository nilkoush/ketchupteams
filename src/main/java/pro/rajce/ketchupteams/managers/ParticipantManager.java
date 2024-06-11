package pro.rajce.ketchupteams.managers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import pro.rajce.ketchupteams.objects.Group;
import pro.rajce.ketchupteams.objects.Participant;
import pro.rajce.ketchupteams.utils.NicknameUtil;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticipantManager {

    private static ParticipantManager INSTANCE;
    private final Map<UUID, Participant> cache = new HashMap<>();

    public Participant getParticipant(Player player) {
        return cache.computeIfAbsent(player.getUniqueId(), uuid -> new Participant(player));
    }

    public void setGroup(Player player, Group group) {
        GroupManager.getInstance().getGroups().forEach(possibleGroup -> possibleGroup.removeMember(player));
        Participant participant = ParticipantManager.getInstance().getParticipant(player);
        participant.setGroup(group);
        group.addMember(player);
        NicknameUtil.setColor(player, group.getColor().asHexString());
    }

    public void resetGroup(Player player) {
        GroupManager.getInstance().getGroups().forEach(possibleGroup -> possibleGroup.removeMember(player));
        Participant participant = ParticipantManager.getInstance().getParticipant(player);
        participant.setGroup(null);
        NicknameUtil.reset(player);
    }

    public void toggleSpectator(Player player) {
        Participant participant = getParticipant(player);
        participant.setSpectator(!participant.isSpectator());
    }

    public void toggleSupervisor(Player player) {
        Participant participant = getParticipant(player);
        participant.setSupervisor(!participant.isSupervisor());
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

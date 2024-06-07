package pro.rajce.ketchupevent.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@Data
@RequiredArgsConstructor
public class Participant {

    private final Player player;
    private boolean isSpectator;
    private boolean isSupervisor;
    private Group group;
    private int kills;
    private int deaths;
}

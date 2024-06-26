package pro.rajce.ketchupteams.objects;

import lombok.*;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Data
@AllArgsConstructor
public class Group implements ConfigurationSerializable {

    private String name;
    private NamedTextColor color;
    private Location gameSpawn;
    private boolean canBuild;
    private boolean intraPvp;
    private final List<UUID> members = new ArrayList<>();

    public void addMember(Player player) {
        members.add(player.getUniqueId());
    }

    public void removeMember(Player player) {
        members.remove(player.getUniqueId());
    }

    public List<Player> getMembers() {
        return members.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .toList();
    }

    public static Group deserialize(Map<String, Object> data) {
        String name = (String) data.get("name");
        NamedTextColor color = NamedTextColor.namedColor((Integer) data.get("color"));
        Location gameSpawn = (Location) data.get("game-spawn");
        boolean build = (boolean) data.get("can-build");
        boolean isIntraPvp = (boolean) data.get("intra-pvp");
        return new Group(name, color, gameSpawn, isIntraPvp, build);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("color", color.value());
        data.put("game-spawn", gameSpawn);
        data.put("can-build", canBuild);
        data.put("intra-pvp", intraPvp);
        return data;
    }
}

package pro.rajce.ketchupteams.objects;

import lombok.*;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Data
@AllArgsConstructor
public class Group implements ConfigurationSerializable {

    private String name;
    private NamedTextColor color;
    private boolean canBuild;
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
        boolean build = (boolean) data.get("can-build");
        return new Group(name, color, build);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("color", color.value());
        data.put("can-build", canBuild);
        return data;
    }
}

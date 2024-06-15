package pro.rajce.ketchupteams.managers;

import dev.nilkoush.thelibrary.utils.FileBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pro.rajce.ketchupteams.KetchupTeamsPlugin;
import pro.rajce.ketchupteams.objects.Group;
import pro.rajce.ketchupteams.utils.NicknameUtil;

import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupManager {

    private static GroupManager INSTANCE;

    public void createGroup(String name) {
        Group group = new Group(name, NamedTextColor.WHITE, null, false, false);

        update(name, group);
    }

    public void deleteGroup(String name) {
        update(name, null);
        for (Player pp : getGroupByName(name).getMembers()) {
            ParticipantManager.getInstance().resetGroup(pp);
        }
    }

    public void setGameSpawn(Group group, Location location) {
        group.setGameSpawn(location);
        update(group.getName(), group);
    }

    public void setColor(Group group, NamedTextColor color) {
        group.setColor(color);
        for (Player pp : group.getMembers()) {
            NicknameUtil.setColor(pp, color.asHexString());
        }
        update(group.getName(), group);
    }

    public void setCanBuild(Group group, boolean canBuild) {
        group.setCanBuild(canBuild);
        update(group.getName(), group);
    }

    public void setIntraPvp(Group group, boolean canBuild) {
        group.setIntraPvp(canBuild);
        update(group.getName(), group);
    }

    private void update(String name, Group group) {
        FileBuilder groupsFile = KetchupTeamsPlugin.getInstance().getGroupsFile();
        groupsFile.set("groups." + name, group);
        groupsFile.save();
    }

    public List<String> getGroupNames() {
        FileBuilder groupsFile = KetchupTeamsPlugin.getInstance().getGroupsFile();
        Set<String> keys = groupsFile.getConfigurationSection("groups").getKeys(false);
        return new ArrayList<>(keys);
    }

    public List<Group> getGroups() {
        FileBuilder groupsFile = KetchupTeamsPlugin.getInstance().getGroupsFile();
        List<Group> groups = new ArrayList<>();
        for (String name : getGroupNames()) {
            groups.add((Group) groupsFile.get("groups." + name));
        }
        return groups;
    }

    public Group getGroupByName(String name) {
        FileBuilder groupsFile = KetchupTeamsPlugin.getInstance().getGroupsFile();
        return (Group) groupsFile.get("groups." + name);
    }

    public static GroupManager getInstance() {
        if (INSTANCE == null) {
            return INSTANCE = new GroupManager();
        }
        return INSTANCE;
    }
}

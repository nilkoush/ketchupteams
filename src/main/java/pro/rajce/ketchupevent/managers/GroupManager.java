package pro.rajce.ketchupevent.managers;

import dev.nilkoush.thelibrary.utils.FileBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.format.NamedTextColor;
import pro.rajce.ketchupevent.KetchupEventPlugin;
import pro.rajce.ketchupevent.objects.Group;

import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupManager {

    private static GroupManager INSTANCE;

    public Group createGroup(String name) {
        Group group = new Group(name, NamedTextColor.WHITE, false);

        update(name, group);

        return group;
    }

    public void deleteGroup(String name) {
        update(name, null);
    }

    public void setColor(Group group, NamedTextColor color) {
        group.setColor(color);
        update(group.getName(), group);
    }

    public void setCanBuild(Group group, boolean canBuild) {
        group.setCanBuild(canBuild);
        update(group.getName(), group);
    }

    private void update(String name, Group group) {
        FileBuilder groupsFile = KetchupEventPlugin.getInstance().getGroupsFile();
        groupsFile.set("groups." + name, group);
        groupsFile.save();
    }

    public List<String> getGroupNames() {
        FileBuilder groupsFile = KetchupEventPlugin.getInstance().getGroupsFile();
        Set<String> keys = groupsFile.getConfigurationSection("groups").getKeys(false);
        return new ArrayList<>(keys);
    }

    public List<Group> getGroups() {
        FileBuilder groupsFile = KetchupEventPlugin.getInstance().getGroupsFile();
        List<Group> groups = new ArrayList<>();
        for (String name : getGroupNames()) {
            groups.add((Group) groupsFile.get("groups." + name));
        }
        return groups;
    }

    public Group getGroupByName(String name) {
        FileBuilder groupsFile = KetchupEventPlugin.getInstance().getGroupsFile();
        return (Group) groupsFile.get("groups." + name);
    }

    public static GroupManager getInstance() {
        if (INSTANCE == null) {
            return INSTANCE = new GroupManager();
        }
        return INSTANCE;
    }
}

package pro.rajce.ketchupteams;

import dev.nilkoush.thelibrary.PluginInfo;
import dev.nilkoush.thelibrary.TheLibrary;
import dev.nilkoush.thelibrary.utils.FileBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import pro.rajce.ketchupteams.hooks.PAPIExpansion;
import pro.rajce.ketchupteams.objects.Group;

@Getter
@PluginInfo
public class KetchupTeamsPlugin extends JavaPlugin {

    static {
        ConfigurationSerialization.registerClass(Group.class, "Group");
    }

    private static volatile KetchupTeamsPlugin INSTANCE;

    private FileBuilder configFile;
    private FileBuilder messagesFile;
    private FileBuilder groupsFile;

    @Override
    public void onLoad() {
        TheLibrary.onLoad(this);
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
            saveDefaultConfig();
            saveResource("messages.yml", false);
        }
        configFile = new FileBuilder(getDataFolder().getAbsolutePath(), "config.yml");
        messagesFile = new FileBuilder(getDataFolder().getAbsolutePath(), "messages.yml");
        groupsFile = new FileBuilder(getDataFolder().getAbsolutePath(), "groups.yml");

    }

    @Override
    public void onEnable() {
        TheLibrary.onEnable();
        registerHooks();
    }

    @Override
    public void onDisable() {
        TheLibrary.onDisable();
    }

    private void registerHooks() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPIExpansion().register();
        }
    }

    public static KetchupTeamsPlugin getInstance() {
        if (INSTANCE == null) {
            INSTANCE = JavaPlugin.getPlugin(KetchupTeamsPlugin.class);
        }

        return INSTANCE;
    }
}
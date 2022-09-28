package net.problemzone.hubbibi.util;

import net.problemzone.hubbibi.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigManager {

    private static final String CONFIG_STRING = "config.yml";
    private static ConfigManager instance;

    private final JavaPlugin plugin;
    private YamlConfiguration yamlConfiguration;

    private ConfigManager() {
        this.plugin = Main.getInstance();
    }

    public void setupConfig() {
        plugin.saveDefaultConfig();

        yamlConfiguration = YamlConfiguration.loadConfiguration(getFile(CONFIG_STRING));
    }

    public YamlConfiguration getConfig() {
        return yamlConfiguration;
    }

    private File getFile(String fileName) {
        return new File(plugin.getDataFolder(), fileName);
    }

    public static ConfigManager getInstance() {
        if (instance == null) instance = new ConfigManager();
        return instance;
    }
}

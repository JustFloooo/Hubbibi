package net.problemzone.hubbibi.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Config {

    private final JavaPlugin plugin;

    private File configFile;
    private FileConfiguration config;

    public Config(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public void loadFiles() throws InvalidConfigurationException {

        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!this.configFile.exists()) {
            this.configFile.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }

        this.config = new YamlConfiguration();

        try {
            this.config.load(this.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}

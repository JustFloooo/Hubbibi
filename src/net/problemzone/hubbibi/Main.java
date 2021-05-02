package net.problemzone.hubbibi;

import net.problemzone.hubbibi.modules.messages.MessageListener;
import net.problemzone.hubbibi.modules.navigator.Navigator;
import net.problemzone.hubbibi.modules.navigator.NavigatorListener;
import net.problemzone.hubbibi.utils.Config;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private final Config config = new Config(this);
    private Navigator navigator;

    @Override
    public void onEnable() {
        getLogger().info("Loading Hubbibi Plugin.");

        getLogger().info("Reading Hubbibi Configuration.");
        loadConfiguration();

        getLogger().info("Loading Hubbibi Commands.");
        registerCommands();

        getLogger().info("Loading Hubbibi Listeners.");
        registerListeners();

        getLogger().info("Hubbibi primed and ready.");
    }

    private void loadConfiguration(){
        try {
            config.loadFiles();
            navigator = new Navigator(config);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void registerCommands(){

    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new NavigatorListener(navigator), this);
        getServer().getPluginManager().registerEvents(new MessageListener(), this);
    }

}

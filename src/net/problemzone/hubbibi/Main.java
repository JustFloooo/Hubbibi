package net.problemzone.hubbibi;

import net.problemzone.hubbibi.modules.WorldProtectionListener;
import net.problemzone.hubbibi.modules.messages.MessageListener;
import net.problemzone.hubbibi.modules.navigator.NavigatorListener;
import net.problemzone.hubbibi.util.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        getLogger().info("Loading Hubbibi Plugin.");
        initiatePlugin();

        getLogger().info("Loading Hubbibi Commands.");
        registerCommands();

        getLogger().info("Loading Hubbibi Listeners.");
        registerListeners();

        getLogger().info("Hubbibi primed and ready.");
    }

    private void initiatePlugin() {
        instance = this;
        ConfigManager.getInstance().setupConfig();
    }

    private void registerCommands(){

    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new NavigatorListener(), this);
        getServer().getPluginManager().registerEvents(new MessageListener(), this);
        getServer().getPluginManager().registerEvents(new WorldProtectionListener(), this);
    }

    public static Main getInstance() {
        return instance;
    }

}

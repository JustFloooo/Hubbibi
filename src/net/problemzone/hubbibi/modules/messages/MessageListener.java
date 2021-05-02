package net.problemzone.hubbibi.modules.messages;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MessageListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        e.setQuitMessage("");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.setJoinMessage("");
        e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
    }

}

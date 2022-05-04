package net.problemzone.hubbibi.modules.messages;

import net.problemzone.hubbibi.util.Language;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MessageListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        e.setQuitMessage(Language.PLAYER_LEAVE.getText() + e.getPlayer().getDisplayName());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.setJoinMessage(Language.PLAYER_JOIN.getText() + e.getPlayer().getDisplayName());
    }

}

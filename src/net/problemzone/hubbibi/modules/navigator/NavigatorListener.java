package net.problemzone.hubbibi.modules.navigator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;

import java.util.Objects;

public class NavigatorListener implements Listener {

    private final Navigator navigator;

    public NavigatorListener(Navigator navigator) {
        this.navigator = navigator;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (navigator.isCompass(event.getItem())) {
                navigator.openCompassMenu(event.getPlayer());
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem()!= null && navigator.isCompass(event.getCurrentItem())) {
            event.setCancelled(true);
        }
        if (event.getView().getTitle().equals(navigator.getCOMPASS_NAME())) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR && event.getCurrentItem().hasItemMeta()) {
                Player p = (Player)event.getWhoClicked();
                Vector destination = navigator.getVectorbyName(Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName());
                if(destination!=null){
                    p.teleport(new Location(p.getWorld(), destination.getX(), destination.getY(), destination.getZ()));
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (navigator.isCompass(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        navigator.giveCompass(p);
    }
}

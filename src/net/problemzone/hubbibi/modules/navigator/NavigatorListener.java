package net.problemzone.hubbibi.modules.navigator;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Objects;

public class NavigatorListener implements Listener {

    public NavigatorListener() {
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (NavigatorManager.getInstance().isCompass(event.getItem())) {
                NavigatorManager.getInstance().openCompassMenu(event.getPlayer());
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        event.setCancelled(true);

        if (!event.getView().getTitle().equals(NavigatorManager.getInstance().getCOMPASS_NAME())) return;
        if (!event.getCurrentItem().hasItemMeta()) return;

        Player p = (Player)event.getWhoClicked();
        NavigatorManager.NavigatorWarp warp = NavigatorManager.getInstance().getWarpByName(Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName());
        if (warp == null) return;
        p.teleport(warp.getDestination().toLocation(p.getWorld(), (float) warp.getYaw(), (float) warp.getPitch()));
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (NavigatorManager.getInstance().isCompass(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        NavigatorManager.getInstance().giveCompass(p);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player p = event.getPlayer();
        NavigatorManager.getInstance().giveCompass(p);
    }
}

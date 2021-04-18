package net.problemzone.hubbibi.tools.navigator;

import net.problemzone.hubbibi.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Navigator{

    private final String COMPASS_NAME;
    private final int COMPASS_INV_SIZE;
    private final int COMPASS_SLOT;
    private final Material COMPASS_ITEM;
    private final List<NavigatorWarp> COMPASS_WARPS;

    public Navigator(Config config) {
        COMPASS_NAME = config.getConfig().isString("navigator.name") ? ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfig().getString("navigator.name"))) : ChatColor.RED + "Navigator";
        COMPASS_SLOT = config.getConfig().isInt("navigator.inv-slot") ? config.getConfig().getInt("navigator.inv-slot") : 4;
        COMPASS_INV_SIZE = config.getConfig().isInt("navigator.inv-size") ? config.getConfig().getInt("navigator.inv-size") : 27;
        COMPASS_ITEM = Material.getMaterial(Objects.requireNonNull(config.getConfig().getString("navigator.item")));
        COMPASS_WARPS = new ArrayList<>();
        for(String warp : config.getConfig().getConfigurationSection("navigator-warps").getKeys(false)){
            Bukkit.getLogger().info(warp);
            COMPASS_WARPS.add(new NavigatorWarp(config, "navigator-warps." + warp));
        }
    }

    public boolean isCompass(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            return Objects.requireNonNull(item.getItemMeta()).hasDisplayName() && item.getItemMeta().getDisplayName().equals(COMPASS_NAME);
        } else {
            return false;
        }
    }

    public void openCompassMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, COMPASS_INV_SIZE, COMPASS_NAME);

        for(NavigatorWarp warp : COMPASS_WARPS){
            ItemStack item = new ItemStack(warp.getItem());

            ItemMeta itemMeta = item.getItemMeta();
            assert itemMeta != null;
            itemMeta.setDisplayName(warp.getName());

            List<String> lore = new ArrayList<>();
            for(String lore_line : warp.getLore()){
                lore.add(ChatColor.translateAlternateColorCodes('&', lore_line));
            }
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);

            inv.setItem(warp.getSlot(), item);
        }

        player.openInventory(inv);
    }

    public void giveCompass(Player p) {

        ItemStack compassItem = new ItemStack(COMPASS_ITEM);

        ItemMeta compassMeta = compassItem.getItemMeta();
        assert compassMeta != null;
        compassMeta.setDisplayName(COMPASS_NAME);

        compassItem.setItemMeta(compassMeta);

        p.getInventory().setItem(COMPASS_SLOT, compassItem);
    }

    @Nullable
    public Vector getVectorbyName(String name) {
        for(NavigatorWarp warp : COMPASS_WARPS){
            if(warp.getName().equals(name))
                return warp.getDestination();
        }
        return null;
    }

    public String getCOMPASS_NAME() {
        return COMPASS_NAME;
    }

    private static class NavigatorWarp{

        private final int slot;
        private final Material item;
        private final String name;
        private final Vector destination;
        private final List<String> lore;

        public NavigatorWarp(Config config, String path) {
            this.slot = config.getConfig().getInt(path+".slot");
            this.item = Material.getMaterial(Objects.requireNonNull(config.getConfig().getString(path + ".item")));
            this.name = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfig().getString(path+".name")));
            this.destination = new Vector(config.getConfig().getDouble(path+".coordinates.x"), config.getConfig().getDouble(path+".coordinates.y"), config.getConfig().getDouble(path+".coordinates.z")) ;
            this.lore = config.getConfig().getStringList(path+".lore");
        }

        public int getSlot() {
            return slot;
        }

        public Material getItem() {
            return item;
        }

        public String getName() {
            return name;
        }

        public Vector getDestination() {
            return destination;
        }

        public List<String> getLore() {
            return lore;
        }
    }
}



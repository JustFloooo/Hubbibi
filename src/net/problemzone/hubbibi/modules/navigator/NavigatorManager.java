package net.problemzone.hubbibi.modules.navigator;

import net.problemzone.hubbibi.exceptions.InvalidConfigException;
import net.problemzone.hubbibi.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NavigatorManager {

    private static NavigatorManager instance;

    private final String COMPASS_NAME;
    private final int COMPASS_INV_SIZE;
    private final int COMPASS_SLOT;
    private final Material COMPASS_ITEM;
    private final List<NavigatorWarp> COMPASS_WARPS;

    private NavigatorManager() {
        YamlConfiguration config = ConfigManager.getInstance().getConfig();
        COMPASS_NAME = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("navigator.name")));
        COMPASS_SLOT = config.getInt("navigator.inv-slot");
        COMPASS_INV_SIZE = config.getInt("navigator.inv-size");
        COMPASS_ITEM = Material.getMaterial(Objects.requireNonNull(config.getString("navigator.item")));

        COMPASS_WARPS = new ArrayList<>();
        for (String warp : Objects.requireNonNull(config.getConfigurationSection("navigator-warps")).getKeys(false)) {
            COMPASS_WARPS.add(new NavigatorWarp("navigator-warps." + warp));
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

        for (NavigatorWarp warp : COMPASS_WARPS) {
            ItemStack item = new ItemStack(warp.getItem());

            ItemMeta itemMeta = item.getItemMeta();
            assert itemMeta != null;
            itemMeta.setDisplayName(warp.getName());

            List<String> lore = new ArrayList<>();
            for (String lore_line : warp.getLore()) {
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
    public NavigatorWarp getWarpByName(String name) {
        for (NavigatorWarp warp : COMPASS_WARPS) {
            if (warp.getName().equals(name))
                return warp;
        }
        return null;
    }

    public String getCOMPASS_NAME() {
        return COMPASS_NAME;
    }

    public static NavigatorManager getInstance() {
        if (instance == null) instance = new NavigatorManager();
        return instance;
    }

    public static class NavigatorWarp {

        private final int slot;
        private final Material item;
        private final String name;
        private final Vector destination;
        private final double pitch;
        private final double yaw;
        private final List<String> lore;

        public NavigatorWarp(String path) {
            ConfigurationSection config = ConfigManager.getInstance().getConfig().getConfigurationSection(path);
            if (config == null) throw new InvalidConfigException("The config.yml file is malformed. Please check the navigator warps section for completeness");

            this.slot = config.getInt("slot");
            this.item = Material.getMaterial(Objects.requireNonNull(config.getString("item")));
            this.name = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("name")));
            this.destination = new Vector(config.getDouble("coordinates.x"), config.getDouble( "coordinates.y"), config.getDouble("coordinates.z"));
            this.pitch = config.isSet("direction.pitch") ? config.getDouble("direction.pitch") : 0;
            this.yaw = config.isSet("direction.yaw") ? config.getDouble("direction.yaw") : 0;
            this.lore = config.isSet("lore") ? config.getStringList("lore") : new ArrayList<>();
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

        public double getPitch() {
            return pitch;
        }

        public double getYaw() {
            return yaw;
        }

        public List<String> getLore() {
            return lore;
        }
    }
}



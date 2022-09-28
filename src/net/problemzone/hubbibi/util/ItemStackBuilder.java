package net.problemzone.hubbibi.util;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class ItemStackBuilder {

    private final ItemStack itemStack;

    public ItemStackBuilder(Material material) {
        this(material, null);
    }

    public ItemStackBuilder(Material material, String name) {
        this(material, name, 1);
    }

    public ItemStackBuilder(Material material, String name, int amount) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();
        if (name != null) Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.WHITE + name);
        Objects.requireNonNull(itemMeta).addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(itemMeta);
        this.itemStack = item;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemStackBuilder setMaterial(Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemStackBuilder addLore(String... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setLore(Arrays.asList(lore));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder addAttribute(String name, double amount, EquipmentSlot equipmentSlot, Attribute attribute) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), name, amount, AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);
        Objects.requireNonNull(itemMeta).addAttributeModifier(attribute, attributeModifier);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder addEnchantment(Enchantment enchantment, int level) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder setCustomModelData(int data) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setCustomModelData(data);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public <T,Z> ItemStackBuilder addNBT(NamespacedKey key, PersistentDataType<T,Z> type, Z value) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).getPersistentDataContainer().set(key, type, value);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder addFlags(ItemFlag... itemFlags) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).addItemFlags(itemFlags);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder addPotionMeta(PotionData potionData) {
        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
        Objects.requireNonNull(potionMeta).setBasePotionData(potionData);
        itemStack.setItemMeta(potionMeta);
        return this;
    }

    public ItemStackBuilder setArmorColor(Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        Objects.requireNonNull(leatherArmorMeta).setColor(color);
        itemStack.setItemMeta(leatherArmorMeta);
        return this;
    }

    public ItemStackBuilder setSkullMeta(Player player) {
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        Objects.requireNonNull(skullMeta).setOwningPlayer(player);
        itemStack.setItemMeta(skullMeta);
        return this;
    }

}
package net.problemzone.hubbibi.util;

import org.bukkit.ChatColor;

public enum Language {

    PLAYER_LEAVE(ChatColor.RED + "« " + ChatColor.WHITE),
    PLAYER_JOIN(ChatColor.GREEN + "» " + ChatColor.WHITE);

    private final String text;

    Language(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}

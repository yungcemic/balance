package me.yungcemic.balance.util;

import org.bukkit.ChatColor;

public final class ChatUtil {

    private ChatUtil() {}

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
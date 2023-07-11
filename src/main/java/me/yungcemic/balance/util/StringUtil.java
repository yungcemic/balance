package me.yungcemic.balance.util;

import org.bukkit.ChatColor;

public final class StringUtil {

    private StringUtil() {}

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
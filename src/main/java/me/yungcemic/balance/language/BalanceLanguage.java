package me.yungcemic.balance.language;

import me.yungcemic.balance.util.ChatUtil;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public final class BalanceLanguage {

    private BalanceLanguage() {}

    private static final Map<String, String> language = new HashMap<>();

    public static synchronized void loadMessages(ConfigurationSection section) {
        section.getKeys(false).forEach(s -> language.put(s, section.getString(s)));
    }

    public static String getMessage(String name) {
        return ChatUtil.colorize(language.getOrDefault(name, ""));
    }
}
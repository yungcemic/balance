package me.yungcemic.balance;

import me.yungcemic.balance.cache.InMemoryPlayerCache;
import me.yungcemic.balance.command.BalCommand;
import me.yungcemic.balance.command.EarnCommand;
import me.yungcemic.balance.command.GiveCommand;
import me.yungcemic.balance.command.SetBalCommand;
import me.yungcemic.balance.language.BalanceLanguage;
import me.yungcemic.balance.listener.PlayerListener;
import me.yungcemic.balance.repository.MongoPlayerRepository;
import me.yungcemic.balance.service.PlayerService;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BalancePlugin extends JavaPlugin {

    private PlayerService playerService;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        BalanceLanguage.loadMessages(getConfig().getConfigurationSection("messages"));
        MongoPlayerRepository playerRepository = new MongoPlayerRepository();
        playerRepository.connect(getConfig().getString("database.mongodb-uri"),
                getConfig().getString("database.database-name"),
                getConfig().getString("database.collection-name"));
        playerService = new PlayerService(new InMemoryPlayerCache(new HashMap<>()), playerRepository);
        getServer().getPluginManager().registerEvents(new PlayerListener(playerService), this);
        EarnCommand earnCommand = new EarnCommand(playerService, new HashMap<>(), new Random());
        getCommand("earn").setExecutor(earnCommand);
        getCommand("bal").setExecutor(new BalCommand(playerService));
        getCommand("setbal").setExecutor(new SetBalCommand(playerService));
        getCommand("give").setExecutor(new GiveCommand(playerService));
        getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            playerService.getAllPlayersFromCache().forEach(playerService::savePlayer);
            earnCommand.clearCooldown();
        }, 36000L, 36000L);
    }

    @Override
    public void onDisable() {
        playerService.getAllPlayersFromCache().forEach(playerService::savePlayer);
        playerService.clearCache();
    }
}
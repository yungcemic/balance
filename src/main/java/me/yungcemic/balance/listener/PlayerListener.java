package me.yungcemic.balance.listener;

import me.yungcemic.balance.player.BalancePlayer;
import me.yungcemic.balance.service.PlayerService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public final class PlayerListener implements Listener {

    private final PlayerService playerService;

    public PlayerListener(PlayerService playerService) {
        this.playerService = playerService;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        UUID uniqueId = e.getPlayer().getUniqueId();
        if (playerService.getPlayer(uniqueId).isEmpty()) {
            playerService.addPlayer(new BalancePlayer(uniqueId, 0));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        UUID uniqueId = e.getPlayer().getUniqueId();
        playerService.getPlayer(uniqueId).ifPresent(playerService::savePlayer);
        playerService.removePlayerFromCache(uniqueId);
    }
}
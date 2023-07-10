package me.yungcemic.balance.service;

import me.yungcemic.balance.cache.PlayerCache;
import me.yungcemic.balance.player.BalancePlayer;
import me.yungcemic.balance.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class PlayerService {

    private final PlayerCache cache;
    private final PlayerRepository repository;

    public PlayerService(PlayerCache cache, PlayerRepository repository) {
        this.cache = cache;
        this.repository = repository;
    }

    public boolean addPlayer(BalancePlayer player) {
        if (getPlayer(player.getUniqueId()).isPresent()) return false;
        cache.add(player);
        repository.save(player);
        return true;
    }

    public Optional<BalancePlayer> getPlayer(UUID uniqueId) {
        Optional<BalancePlayer> player = Optional.ofNullable(cache.get(uniqueId));
        if (player.isEmpty()) {
            player = repository.findByUniqueId(uniqueId);
            player.ifPresent(cache::add);
        }
        return player;
    }

    public void savePlayer(BalancePlayer player) {
        repository.save(player);
    }

    public void removePlayerFromCache(UUID uniqueId) {
        cache.delete(uniqueId);
    }

    public List<BalancePlayer> getAllPlayersFromCache() {
        return cache.getAllPlayers();
    }

    public void clearCache() {
        cache.clear();
    }
}
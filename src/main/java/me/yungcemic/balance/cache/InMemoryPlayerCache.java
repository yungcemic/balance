package me.yungcemic.balance.cache;

import me.yungcemic.balance.player.BalancePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class InMemoryPlayerCache implements PlayerCache {

    private final Map<UUID, BalancePlayer> cache;

    public InMemoryPlayerCache(Map<UUID, BalancePlayer> cache) {
        this.cache = cache;
    }

    @Override
    public void add(BalancePlayer player) {
        cache.put(player.getUniqueId(), player);
    }

    @Override
    public BalancePlayer get(UUID uniqueId) {
        return cache.get(uniqueId);
    }

    @Override
    public List<BalancePlayer> getAllPlayers() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public void delete(UUID uniqueId) {
        cache.remove(uniqueId);
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
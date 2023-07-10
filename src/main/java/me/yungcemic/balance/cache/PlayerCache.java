package me.yungcemic.balance.cache;

import me.yungcemic.balance.player.BalancePlayer;

import java.util.List;
import java.util.UUID;

public interface PlayerCache {

    void add(BalancePlayer player);
    BalancePlayer get(UUID uniqueId);
    List<BalancePlayer> getAllPlayers();
    void delete(UUID uniqueId);
    void clear();
}
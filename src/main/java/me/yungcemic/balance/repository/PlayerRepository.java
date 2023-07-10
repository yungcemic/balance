package me.yungcemic.balance.repository;

import me.yungcemic.balance.player.BalancePlayer;

import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository {

    Optional<BalancePlayer> findByUniqueId(UUID uniqueId);
    void save(BalancePlayer player);

}
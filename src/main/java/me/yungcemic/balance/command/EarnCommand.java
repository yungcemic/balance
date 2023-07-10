package me.yungcemic.balance.command;

import me.yungcemic.balance.language.BalanceLanguage;
import me.yungcemic.balance.service.PlayerService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class EarnCommand implements CommandExecutor {

    private final PlayerService service;
    private final Map<UUID, Long> cooldown;
    private final Random random;

    public EarnCommand(PlayerService service, Map<UUID, Long> cooldown, Random random) {
        this.service = service;
        this.cooldown = cooldown;
        this.random = random;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player p) {
            UUID uniqueId = p.getUniqueId();
            if (System.currentTimeMillis() >= cooldown.getOrDefault(uniqueId, Long.MIN_VALUE)) {
                final int amount = random.nextInt(1,6);
                service.getPlayer(uniqueId).ifPresent(player -> {
                    player.addBalance(amount);
                });
                cooldown.put(uniqueId, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1));
                p.sendMessage(BalanceLanguage.getMessage("earn")
                        .replace("%amount%", String.valueOf(amount)));
                return true;
            }
            long remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(cooldown.get(uniqueId) - System.currentTimeMillis());
            p.sendMessage(BalanceLanguage.getMessage("earn-cooldown").replace("%time%", String.valueOf(remainingSeconds)));
            return true;
        }
        return false;
    }

    public void clearCooldown() {
        cooldown.entrySet().removeIf(entry -> System.currentTimeMillis() >= entry.getValue());
    }
}
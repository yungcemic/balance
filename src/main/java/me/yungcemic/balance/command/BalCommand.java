package me.yungcemic.balance.command;

import me.yungcemic.balance.language.BalanceLanguage;
import me.yungcemic.balance.service.PlayerService;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class BalCommand implements CommandExecutor {

    private final PlayerService service;

    public BalCommand(PlayerService service) {
        this.service = service;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                service.getPlayer(p.getUniqueId()).ifPresent(balancePlayer ->
                        p.sendMessage(BalanceLanguage.getMessage("balance")
                        .replace("%amount%", String.valueOf(balancePlayer.getBalance()))));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) return true;
            service.getPlayer(target.getUniqueId()).ifPresent(balancePlayer ->
                    p.sendMessage(BalanceLanguage.getMessage("target-balance")
                    .replace("%amount%", String.valueOf(balancePlayer.getBalance()))
                    .replace("%target%", args[0])));
            return true;
        }
        return false;
    }
}

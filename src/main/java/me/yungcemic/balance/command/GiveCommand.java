package me.yungcemic.balance.command;

import me.yungcemic.balance.language.BalanceLanguage;
import me.yungcemic.balance.player.BalancePlayer;
import me.yungcemic.balance.service.PlayerService;
import me.yungcemic.balance.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public final class GiveCommand implements CommandExecutor {

    private final PlayerService service;

    public GiveCommand(PlayerService service) {
        this.service = service;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length != 2) {
                p.sendMessage(BalanceLanguage.getMessage("give-help"));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            double amount = StringUtil.parseDouble(args[1]);
            if (target == null || amount <= 0 || target.equals(p)) return true;
            Optional<BalancePlayer> targetAccount = service.getPlayer(target.getUniqueId());
            if (targetAccount.isEmpty()) return true;
            service.getPlayer(p.getUniqueId()).ifPresent(player -> {
                if (amount > player.getBalance()) return;
                player.removeBalance(amount);
                targetAccount.get().addBalance(amount);
                p.sendMessage(BalanceLanguage.getMessage("give")
                        .replace("%player%", target.getName())
                        .replace("%amount%", args[1]));
                target.sendMessage(BalanceLanguage.getMessage("give-target")
                        .replace("%player%", p.getName())
                        .replace("%amount%", args[1]));
            });
            return true;
        }
        return false;
    }
}

package me.yungcemic.balance.command;

import me.yungcemic.balance.language.BalanceLanguage;
import me.yungcemic.balance.service.PlayerService;
import me.yungcemic.balance.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class SetBalCommand implements CommandExecutor {

    private final PlayerService service;

    public SetBalCommand(PlayerService service) {
        this.service = service;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.isOp()) {
            if (args.length != 2) {
                sender.sendMessage(BalanceLanguage.getMessage("set-balance-help"));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            double amount = StringUtil.parseDouble(args[1]);
            if (target == null || amount <= 0) return true;
            service.getPlayer(target.getUniqueId()).ifPresent(player -> {
                player.setBalance(amount);
                sender.sendMessage(BalanceLanguage.getMessage("set-balance")
                        .replace("%player%", target.getName())
                        .replace("%amount%", args[1]));
            });
            return true;
        }
        return false;
    }
}
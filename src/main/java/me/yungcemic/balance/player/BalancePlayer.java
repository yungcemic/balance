package me.yungcemic.balance.player;

import java.util.UUID;

public final class BalancePlayer {

    private final UUID uniqueId;
    private double balance;

    public BalancePlayer(UUID uniqueId, double balance) {
        this.uniqueId = uniqueId;
        this.balance = balance;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addBalance(double amount) {
        if (amount <= 0) throw new IllegalArgumentException();
        this.balance = this.balance + amount;
    }

    public void removeBalance(double amount) {
        if (amount <= 0) throw new IllegalArgumentException();
        this.balance = this.balance - amount;
    }
}
package task1.main;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Account {
    private static int nextId = 0;
    private final int accountId;
    private final Lock accountLock;
    private int funds;

    public Account(int startingBalance) {
        this.accountId = generateNextId();
        this.funds = startingBalance;
        this.accountLock = new ReentrantLock();
    }

    private static synchronized int generateNextId() {
        return nextId++;
    }

    public int getId() {
        return accountId;
    }

    public int getBalance() {
        accountLock.lock();
        try {
            return funds;
        } finally {
            accountLock.unlock();
        }
    }

    public boolean withdraw(int sum) {
        if (sum <= 0) {
            return false;
        }

        accountLock.lock();
        try {
            if (funds >= sum) {
                funds -= sum;
                return true;
            }
            return false;
        } finally {
            accountLock.unlock();
        }
    }

    public void deposit(int sum) {
        if (sum <= 0) {
            return;
        }

        accountLock.lock();
        try {
            funds += sum;
        } finally {
            accountLock.unlock();
        }
    }

    Lock getAccountLock() {
        return accountLock;
    }

    @Override
    public String toString() {
        return String.format("Рахунок #%d (баланс: %d грн)", accountId, getBalance());
    }
}
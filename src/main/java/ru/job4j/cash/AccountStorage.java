package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        accounts.put(account.id(), account);
        return accounts.containsKey(account.id());
    }

    public synchronized boolean update(Account account) {
        return add(account);
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> from = getById(fromId);
        Optional<Account> to = getById(toId);

        if (from.isEmpty() || to.isEmpty()) {
            return false;
        }
        int newFromAmount = from.get().amount() - amount;
        int newToAmount = to.get().amount() + amount;
        if (newFromAmount < 0) {
            return false;
        }

        update(new Account(from.get().id(), newFromAmount));
        update(new Account(to.get().id(), newToAmount));

        return true;
    }
}
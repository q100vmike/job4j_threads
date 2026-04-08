package ru.job4j.cash;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        accounts.putIfAbsent(account.id(), account);
        return accounts.containsKey(account.id());
    }

    public synchronized boolean update(Account account) {
        Account replace = accounts.replace(account.id(), account);
        return !Objects.isNull(replace);
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
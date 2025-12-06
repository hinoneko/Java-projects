package task1.main;


public class Bank {
    public void transfer(Account sender, Account receiver, int sum) {
        if (sum <= 0 || sender.getId() == receiver.getId()) {
            return;
        }

        Account firstLock = sender.getId() < receiver.getId() ? sender : receiver;
        Account secondLock = sender.getId() < receiver.getId() ? receiver : sender;

        firstLock.getAccountLock().lock();
        try {
            secondLock.getAccountLock().lock();
            try {
                if (sender.withdraw(sum)) {
                    receiver.deposit(sum);
                }
            } finally {
                secondLock.getAccountLock().unlock();
            }
        } finally {
            firstLock.getAccountLock().unlock();
        }
    }
}
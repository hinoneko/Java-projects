package task1.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BankTransferTest {

    private static final int TOTAL_ACCOUNTS = 100;
    private static final int MAX_START_BALANCE = 10000;
    private static final int TOTAL_OPERATIONS = 100000;
    private static final int MAX_TRANSFER_SUM = 50;
    private static final int THREAD_COUNT = 50;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Тестування багатопотокових переказів");

        Bank bankSystem = new Bank();
        List<Account> accountsList = initializeAccounts();

        int startingTotal = calculateTotalBalance(accountsList);
        System.out.println("Початковий баланс банку: " + startingTotal + " грн");
        System.out.println("Кількість рахунків: " + TOTAL_ACCOUNTS);
        System.out.println("Кількість операцій: " + TOTAL_OPERATIONS);
        System.out.println("Кількість потоків: " + THREAD_COUNT);

        System.out.println("\nЗапуск операцій переказу\n");
        long startTime = System.currentTimeMillis();
        performTransfers(bankSystem, accountsList);
        long duration = System.currentTimeMillis() - startTime;

        int endingTotal = calculateTotalBalance(accountsList);

        System.out.println("\nФінальний баланс банку: " + endingTotal + " грн");
        System.out.println("Час виконання: " + duration + " мс");
        System.out.println("Різниця балансів: " + (endingTotal - startingTotal) + " грн");

        if (startingTotal == endingTotal) {
            System.out.println("\nБаланс залишився незмінним. Всі транзакції виконано коректно. Deadlock не виник.");
        } else {
            System.out.println("\nПомилка: Виявлено розбіжність у балансах.");
        }
    }

    private static List<Account> initializeAccounts() {
        Random rng = new Random();
        List<Account> accounts = new ArrayList<>(TOTAL_ACCOUNTS);

        for (int i = 0; i < TOTAL_ACCOUNTS; i++) {
            int initialFunds = rng.nextInt(MAX_START_BALANCE);
            accounts.add(new Account(initialFunds));
        }

        return accounts;
    }

    private static void performTransfers(Bank bank, List<Account> accounts)
            throws InterruptedException {

        CountDownLatch completionLatch = new CountDownLatch(TOTAL_OPERATIONS);

        try (ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT)) {
            for (int i = 0; i < TOTAL_OPERATIONS; i++) {
                threadPool.submit(() -> {
                    try {
                        Random random = new Random();

                        Account fromAccount = accounts.get(random.nextInt(TOTAL_ACCOUNTS));
                        Account toAccount = accounts.get(random.nextInt(TOTAL_ACCOUNTS));

                        int transferAmount = random.nextInt(MAX_TRANSFER_SUM) + 1;

                        bank.transfer(fromAccount, toAccount, transferAmount);
                    } finally {
                        completionLatch.countDown();
                    }
                });
            }
            completionLatch.await();
        }

        System.out.println("Всі операції завершено");
    }

    private static int calculateTotalBalance(List<Account> accounts) {
        int totalSum = 0;
        for (Account account : accounts) {
            totalSum += account.getBalance();
        }
        return totalSum;
    }
}
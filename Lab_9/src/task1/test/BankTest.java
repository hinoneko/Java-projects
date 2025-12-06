package task1.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task1.main.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;


class BankTest {

    private Bank bankSystem;

    @BeforeEach
    void initialize() {
        bankSystem = new Bank();
    }

    @Test
    void testValidTransferBetweenAccounts() {
        Account sender = new Account(1200);
        Account recipient = new Account(400);

        bankSystem.transfer(sender, recipient, 300);

        assertEquals(900, sender.getBalance());
        assertEquals(700, recipient.getBalance());
    }

    @Test
    void testTransferFailsWithInsufficientBalance() {
        Account sender = new Account(80);
        Account recipient = new Account(400);

        bankSystem.transfer(sender, recipient, 150);

        assertEquals(80, sender.getBalance());
        assertEquals(400, recipient.getBalance());
    }

    @Test
    void testTransferWithNegativeAmountDoesNothing() {
        Account sender = new Account(1200);
        Account recipient = new Account(400);

        bankSystem.transfer(sender, recipient, -50);

        assertEquals(1200, sender.getBalance());
        assertEquals(400, recipient.getBalance());
    }

    @Test
    void testTransferToSameAccountIsIgnored() {
        Account singleAccount = new Account(1200);
        bankSystem.transfer(singleAccount, singleAccount, 150);

        assertEquals(1200, singleAccount.getBalance());
    }

    @Test
    void testTransferZeroAmount() {
        Account sender = new Account(1200);
        Account recipient = new Account(400);

        bankSystem.transfer(sender, recipient, 0);

        assertEquals(1200, sender.getBalance());
        assertEquals(400, recipient.getBalance());
    }

    @Test
    void testNoDeadlockWithBidirectionalTransfers() throws InterruptedException {
        final int OPERATIONS_COUNT = 10000;
        final Account firstAccount = new Account(100000);
        final Account secondAccount = new Account(100000);

        CountDownLatch syncLatch = new CountDownLatch(2);

        try (ExecutorService threadPool = Executors.newFixedThreadPool(2)) {
            threadPool.submit(() -> {
                try {
                    for (int i = 0; i < OPERATIONS_COUNT; i++) {
                        bankSystem.transfer(firstAccount, secondAccount, 1);
                    }
                } finally {
                    syncLatch.countDown();
                }
            });

            threadPool.submit(() -> {
                try {
                    for (int i = 0; i < OPERATIONS_COUNT; i++) {
                        bankSystem.transfer(secondAccount, firstAccount, 1);
                    }
                } finally {
                    syncLatch.countDown();
                }
            });

            boolean completedInTime = syncLatch.await(5, TimeUnit.SECONDS);
            assertTrue(completedInTime, "Операції не завершились вчасно - можливий deadlock");
        }

        assertEquals(200000, firstAccount.getBalance() + secondAccount.getBalance());
    }

    @Test
    void testMassiveConcurrentTransfers() throws InterruptedException {
        int accountsCount = 10;
        int threadsCount = 20;
        int transfersPerThread = 1000;

        Account[] accountsArray = new Account[accountsCount];
        int startingTotalBalance = 0;

        for (int i = 0; i < accountsCount; i++) {
            accountsArray[i] = new Account(1000);
            startingTotalBalance += accountsArray[i].getBalance();
        }

        CountDownLatch completionLatch = new CountDownLatch(threadsCount);

        try (ExecutorService executor = Executors.newFixedThreadPool(threadsCount)) {
            for (int i = 0; i < threadsCount; i++) {
                executor.submit(() -> {
                    try {
                        for (int j = 0; j < transfersPerThread; j++) {
                            int senderIndex = (int) (Math.random() * accountsCount);
                            int receiverIndex = (int) (Math.random() * accountsCount);
                            bankSystem.transfer(accountsArray[senderIndex],
                                    accountsArray[receiverIndex], 5);
                        }
                    } finally {
                        completionLatch.countDown();
                    }
                });
            }

            boolean completed = completionLatch.await(10, TimeUnit.SECONDS);
            assertTrue(completed, "Тест перевищив ліміт часу - можливий deadlock");
        }

        int finalTotalBalance = 0;
        for (Account acc : accountsArray) {
            finalTotalBalance += acc.getBalance();
        }

        assertEquals(startingTotalBalance, finalTotalBalance);
    }

    @Test
    void testMultipleSmallTransfers() {
        Account first = new Account(500);
        Account second = new Account(300);

        for (int i = 0; i < 10; i++) {
            bankSystem.transfer(first, second, 20);
        }

        assertEquals(300, first.getBalance());
        assertEquals(500, second.getBalance());
    }

    @Test
    void testTransferExactBalance() {
        Account sender = new Account(250);
        Account recipient = new Account(100);

        bankSystem.transfer(sender, recipient, 250);

        assertEquals(0, sender.getBalance());
        assertEquals(350, recipient.getBalance());
    }
}
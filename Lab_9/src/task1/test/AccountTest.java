package task1.test;

import org.junit.jupiter.api.Test;
import task1.main.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.junit.jupiter.api.Assertions.*;


class AccountTest {

    @Test
    void testAccountCreationWithBalance() {
        Account account = new Account(1500);
        assertEquals(1500, account.getBalance());
    }

    @Test
    void testAccountsHaveDifferentIds() {
        Account firstAccount = new Account(200);
        Account secondAccount = new Account(200);
        assertNotEquals(firstAccount.getId(), secondAccount.getId());
    }

    @Test
    void testDepositIncreasesBalance() {
        Account account = new Account(300);
        account.deposit(150);
        assertEquals(450, account.getBalance());
    }

    @Test
    void testDepositWithNegativeValueDoesNothing() {
        Account account = new Account(300);
        account.deposit(-50);
        assertEquals(300, account.getBalance());
    }

    @Test
    void testDepositZeroDoesNotChangeBalance() {
        Account account = new Account(300);
        account.deposit(0);
        assertEquals(300, account.getBalance());
    }

    @Test
    void testSuccessfulWithdrawal() {
        Account account = new Account(600);
        boolean success = account.withdraw(250);
        assertTrue(success);
        assertEquals(350, account.getBalance());
    }

    @Test
    void testWithdrawalFailsWhenInsufficientFunds() {
        Account account = new Account(150);
        boolean success = account.withdraw(300);
        assertFalse(success);
        assertEquals(150, account.getBalance());
    }

    @Test
    void testWithdrawAllFunds() {
        Account account = new Account(200);
        boolean success = account.withdraw(200);
        assertTrue(success);
        assertEquals(0, account.getBalance());
    }

    @Test
    void testWithdrawNegativeAmountFails() {
        Account account = new Account(400);
        boolean success = account.withdraw(-100);
        assertFalse(success);
        assertEquals(400, account.getBalance());
    }

    @Test
    void testConcurrentOperationsOnSingleAccount() throws InterruptedException {
        Account sharedAccount = new Account(2000);
        int numberOfThreads = 100;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        try (ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads)) {
            for (int i = 0; i < numberOfThreads; i++) {
                executor.submit(() -> {
                    try {
                        sharedAccount.deposit(20);
                        sharedAccount.withdraw(10);
                    } finally {
                        latch.countDown();
                    }
                });
            }
            latch.await();
        }
        assertEquals(3000, sharedAccount.getBalance());
    }

    @Test
    void testMultipleDeposits() {
        Account account = new Account(100);
        account.deposit(50);
        account.deposit(75);
        account.deposit(25);
        assertEquals(250, account.getBalance());
    }

    @Test
    void testMultipleWithdrawals() {
        Account account = new Account(500);
        account.withdraw(100);
        account.withdraw(150);
        account.withdraw(50);
        assertEquals(200, account.getBalance());
    }
}
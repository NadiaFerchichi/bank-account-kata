package com.bank.account.kata.domain.usecase;

import static com.bank.account.kata.domain.model.operation.OperationType.DEPOSIT;
import static com.bank.account.kata.domain.model.operation.OperationType.WITHDRAWAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bank.account.kata.domain.exception.InvalidDataException;
import com.bank.account.kata.domain.exception.NotFoundException;
import com.bank.account.kata.domain.exception.NotPossibleOperation;
import com.bank.account.kata.domain.model.account.Account;
import com.bank.account.kata.domain.model.operation.Operation;
import com.bank.account.kata.domain.port.secondary.repository.AccountRepository;
import com.bank.account.kata.domain.port.secondary.repository.OperationRepository;
import com.bank.account.kata.domain.port.secondary.transaction.UserTransaction;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PerformOperationOnAccountUseCaseTest {

    @Mock
    AccountRepository accountRepository;
    @Mock
    OperationRepository operationRepository;

    @Mock
    UserTransaction userTransaction;

    PerformOperationOnAccountUseCase useCase;

    @BeforeEach
    void setup() {
        useCase = getPerformOperationOnAccountUseCase();
    }
    private PerformOperationOnAccountUseCase getPerformOperationOnAccountUseCase() {
        return new PerformOperationOnAccountUseCase(
                accountRepository,
                operationRepository,
                userTransaction
        );
    }

    @Test
    void assertThatDepositOperationIntoAnAccountIsSuccessfullyDone_WhenAccountExists() {
        var accountId = UUID.randomUUID();
        Double amount = 5.0;
        Double balanceBeforeOperation = 10.0;
        Account account = new Account(accountId, balanceBeforeOperation);

        when(
                accountRepository.findById(
                        accountId
                )
        )
                .thenReturn(
                        Optional.of(account)
                );

        ArgumentCaptor<Operation> operationCaptor = ArgumentCaptor.forClass(Operation.class);
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

        useCase.handle(accountId, DEPOSIT, amount);

        verify(
                accountRepository,
                times(1)
        ).findById(accountId);
        verify(
                accountRepository,
                times(1)
        ).save(accountCaptor.capture());
        verify(
                operationRepository,
                times(1)
        ).save(operationCaptor.capture());

        Account capturedAccount = accountCaptor.getValue();

        assertNotNull(capturedAccount);
        assertEquals(
                accountId,
                capturedAccount.getId()
        );
        assertEquals(
                balanceBeforeOperation + amount,
                account.getBalance()
        );

        Operation capturedOperation = operationCaptor.getValue();

        assertNotNull(capturedOperation);
        assertEquals(
                accountId,
                capturedOperation.getAccountId()
        );
        assertEquals(
                capturedAccount.getBalance(),
                capturedOperation.getBalance()
        );
        assertEquals(
                amount,
                capturedOperation.getAmount()
        );
        assertEquals(
                capturedAccount.getBalance(),
                capturedOperation.getBalance()
        );
    }

    @Test
    void assertThatWithdrawAmountFromAnAccountIsSuccessfullyDone_WhenAccountExistsAndAmountIsLowerThanAccountBalance() {
        var accountId = UUID.randomUUID();
        Double amount = 5.0;
        Double balanceBeforeOperation = 10.0;
        Account account = new Account(accountId, balanceBeforeOperation);

        when(
                accountRepository.findById(
                        accountId
                )
        )
                .thenReturn(
                        Optional.of(account)
                );

        ArgumentCaptor<Operation> operationCaptor = ArgumentCaptor.forClass(Operation.class);
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

        useCase.handle(accountId, WITHDRAWAL, amount);

        verify(
                accountRepository,
                times(1)
        ).findById(accountId);
        verify(
                accountRepository,
                times(1)
        ).save(accountCaptor.capture());
        verify(
                operationRepository,
                times(1)
        ).save(operationCaptor.capture());

        Account capturedAccount = accountCaptor.getValue();

        assertNotNull(capturedAccount);
        assertEquals(
                accountId,
                capturedAccount.getId()
        );
        assertEquals(
                balanceBeforeOperation - amount,
                account.getBalance()
        );

        Operation capturedOperation = operationCaptor.getValue();

        assertNotNull(capturedOperation);
        assertEquals(
                accountId,
                capturedOperation.getAccountId()
        );
        assertEquals(
                capturedAccount.getBalance(),
                capturedOperation.getBalance()
        );
        assertEquals(
                amount,
                capturedOperation.getAmount()
        );
        assertEquals(
                capturedAccount.getBalance(),
                capturedOperation.getBalance()
        );
    }

    @Nested
    class FailedCases {
        @Test
        void assertThrowsNotFoundException_WhenPerformOperationOnNonExistentAccount() {
            var accountId = UUID.randomUUID();
            Double amount = 5.0;
            when(
                    accountRepository.findById(
                            accountId
                    )
            )
                    .thenReturn(
                            Optional.empty()
                    );

            Assertions.assertThrowsExactly(
                    NotFoundException.class,
                    () -> useCase.handle(accountId, DEPOSIT, amount)
            );

            verify(
                    accountRepository,
                    times(1)
            ).findById(accountId);
        }

        @Test
        void assertThrowsNotPossibleOperation_WhenWithdrawFromAnAccountAnAmountGreaterThanBalance() {
            var accountId = UUID.randomUUID();
            Double amount = 20.0;
            Double balanceBeforeOperation = 10.0;
            Account account = new Account(accountId, balanceBeforeOperation);
            when(
                    accountRepository.findById(
                            accountId
                    )
            )
                    .thenReturn(
                            Optional.of(account)
                    );

            Assertions.assertThrowsExactly(
                    NotPossibleOperation.class,
                    () -> useCase.handle(accountId, WITHDRAWAL, amount)
            );

            verify(
                    accountRepository,
                    times(1)
            ).findById(accountId);
        }

        @Test
        void assertThrowsInvalidDataException_WhenPerformOperationOnAnAmountWithNegativeAmount() {
            var accountId = UUID.randomUUID();
            Double amount = -5.0;
            Double balanceBeforeOperation = 10.0;
            Account account = new Account(accountId, balanceBeforeOperation);
            when(
                    accountRepository.findById(
                            accountId
                    )
            )
                    .thenReturn(
                            Optional.of(account)
                    );

            Assertions.assertThrowsExactly(
                    InvalidDataException.class,
                    () -> useCase.handle(accountId, DEPOSIT, amount)
            );

            verify(
                    accountRepository,
                    times(1)
            ).findById(accountId);
        }
    }
}

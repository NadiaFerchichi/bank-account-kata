package com.bank.account.kata.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bank.account.kata.domain.exception.NotFoundException;
import com.bank.account.kata.domain.model.Page;
import com.bank.account.kata.domain.model.operation.DepositOperation;
import com.bank.account.kata.domain.model.operation.WithdrawalOperation;
import com.bank.account.kata.domain.port.secondary.repository.AccountRepository;
import com.bank.account.kata.domain.port.secondary.repository.OperationRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ListAccountOperationsHistoryUseCaseTest {

    @Mock
    AccountRepository accountRepository;
    @Mock
    OperationRepository operationRepository;

    ListAccountOperationsHistoryUseCase listAccountOperationsHistoryUseCase;

    @BeforeEach
    void setup() {
        listAccountOperationsHistoryUseCase = getListAccountOperationsHistoryUseCase();
    }
    private ListAccountOperationsHistoryUseCase getListAccountOperationsHistoryUseCase() {
        return new ListAccountOperationsHistoryUseCase(
                accountRepository,
                operationRepository
        );
    }

    @Test
    void assertThatListHistoryIsSuccessfullyDone_WhenProvidedAccountIdExists() {
        var accountId = UUID.randomUUID();
        var operations = List.of(
                new DepositOperation(accountId, 20.0, 500.0, OffsetDateTime.now()),
                new WithdrawalOperation(accountId, 10.0, 490.0, OffsetDateTime.now()),
                new DepositOperation(accountId, 30.0, 520.0, OffsetDateTime.now())
        );
        var page = new Page(0,25);
        when(
                accountRepository.existsWithId(
                        accountId
                )
        )
                .thenReturn(
                        true
                );
        when(
                operationRepository.findByAccountId(
                        accountId,
                        page
                )
        )
                .thenReturn(
                        operations
                );

        var retrievedAccountOperationsHistory = listAccountOperationsHistoryUseCase.byAccountId(accountId, page);

        verify(
                accountRepository,
                times(1)
        ).existsWithId(accountId);
        verify(
                operationRepository,
                times(1)
        ).findByAccountId(accountId, page);

        assertNotNull(retrievedAccountOperationsHistory);
        assertEquals(
                operations,
                retrievedAccountOperationsHistory
        );
    }

    @Nested
    class FailedCases {
        @Test
        void assertThrowsNotFoundException_WhenProvidedAccountIdDoesNotExist() {
            var accountId = UUID.randomUUID();
            Double amount = 5.0;
            when(
                    accountRepository.existsWithId(
                            accountId
                    )
            )
                    .thenReturn(
                            false
                    );

            Assertions.assertThrowsExactly(
                    NotFoundException.class,
                    () -> listAccountOperationsHistoryUseCase.byAccountId(accountId, new Page(0,25))
            );

            verify(
                    accountRepository,
                    times(1)
            ).existsWithId(accountId);
        }
    }
}

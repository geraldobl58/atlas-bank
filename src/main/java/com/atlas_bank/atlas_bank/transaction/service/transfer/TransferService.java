package com.atlas_bank.atlas_bank.transaction.service.transfer;

import com.atlas_bank.atlas_bank.account.exception.AccountNotFoundException;
import com.atlas_bank.atlas_bank.account.model.Account;
import com.atlas_bank.atlas_bank.transaction.service.event.TransactionExecutedEvent;
import com.atlas_bank.atlas_bank.transaction.exception.AccountNotActiveException;
import com.atlas_bank.atlas_bank.transaction.exception.InsufficientFundsException;
import com.atlas_bank.atlas_bank.transaction.model.Transaction;
import com.atlas_bank.atlas_bank.account.repository.AccountRepository;
import com.atlas_bank.atlas_bank.transaction.repository.TransactionRepository;
import com.atlas_bank.atlas_bank.transaction.service.factory.TransactionFactory;
import com.atlas_bank.atlas_bank.transaction.service.fee.FeeCalculator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransferService extends TransactionProcessor<TransferContext> implements ITransferService {
    private final AccountRepository accountRepository;
    private final List<FeeCalculator> feeCalculators;
    private final ApplicationEventPublisher eventPublisher;

    public TransferService(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            List<FeeCalculator> feeCalculators,
            ApplicationEventPublisher eventPublisher

    ) {
        super(transactionRepository);
        this.accountRepository = accountRepository;
        this.feeCalculators = feeCalculators;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public Transaction execute(UUID fromId, UUID toId, BigDecimal amount) {
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new AccountNotFoundException(fromId));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new AccountNotFoundException(toId));

        Transaction transaction = process(new TransferContext(from, to, amount));

        eventPublisher.publishEvent(new TransactionExecutedEvent(
                transaction.getId(),
                transaction.getType(),
                transaction.getSourceAccountId(),
                transaction.getTargetAccountId(),
                transaction.getAmount(),
                transaction.getFee()
        ));

        return transaction;
    }

    @Override
    protected void validate(TransferContext context) {
        if (!"ACTIVE".equals(context.sourceAccountId().getStatus())) {
            throw new AccountNotActiveException(context.sourceAccountId().getId(), context.sourceAccountId().getStatus());
        }

        if (!"ACTIVE".equals(context.targetAccountId().getStatus())) {
            throw new AccountNotActiveException(context.targetAccountId().getId(), context.targetAccountId().getStatus());
        }

        if (context.sourceAccountId().getBalance().compareTo(context.amount()) < 0) {
            throw new InsufficientFundsException(context.sourceAccountId().getId(), context.sourceAccountId().getBalance(), context.amount());
        }
    }

    @Override
    protected BigDecimal calculateFee(TransferContext context) {
        return feeCalculators
                .stream()
                .filter(fc -> fc.supports(context.sourceAccountId().getType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No fee calculator available " + context.sourceAccountId().getType()))
                .calculateFee(context.amount());
    }

    @Override
    protected void execute(TransferContext context, BigDecimal fee) {
        context.sourceAccountId().setBalance(context.sourceAccountId().getBalance().subtract(context.amount()).subtract(fee));
        context.targetAccountId().setBalance(context.targetAccountId().getBalance().add(context.amount()));
        accountRepository.save(context.sourceAccountId());
        accountRepository.save(context.targetAccountId());
    }

    @Override
    protected Transaction save(TransferContext context, BigDecimal fee) {
        Transaction transaction = TransactionFactory.createTransfer(context, fee);

        return transactionRepository.save(transaction);
    }
}

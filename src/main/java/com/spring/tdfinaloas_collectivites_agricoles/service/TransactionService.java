package com.spring.tdfinaloas_collectivites_agricoles.service;

import com.spring.tdfinaloas_collectivites_agricoles.dao.CollectivityDao;
import com.spring.tdfinaloas_collectivites_agricoles.dao.TransactionDao;
import com.spring.tdfinaloas_collectivites_agricoles.model.Transaction;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionDao transactionDao;
    private final CollectivityDao collectivityDao;

    public TransactionService(TransactionDao transactionDao, CollectivityDao collectivityDao) {
        this.transactionDao = transactionDao;
        this.collectivityDao = collectivityDao;
    }

    public List<Transaction> findByCollectivityIdAndDateRange(String collectivityId, LocalDate from, LocalDate to) throws SQLException {
        if (collectivityDao.findById(collectivityId).isEmpty()) {
            throw new IllegalArgumentException("Collectivity not found: " + collectivityId);
        }
        if (from == null || to == null) {
            throw new IllegalArgumentException("Parameters 'from' and 'to' are required");
        }
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("'from' date must be before or equal to 'to' date");
        }
        return transactionDao.findByCollectivityIdAndDateRange(collectivityId, from, to);
    }
}
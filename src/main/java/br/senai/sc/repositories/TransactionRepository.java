package br.senai.sc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.senai.sc.models.Account;
import br.senai.sc.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	
	@Query(value = "SELECT * FROM TRANSACTIONS WHERE ACCOUNT_ID = :accountId", nativeQuery = true)
	List<Transaction> findAllByAccountId(@Param("accountId")Long accountId);
}

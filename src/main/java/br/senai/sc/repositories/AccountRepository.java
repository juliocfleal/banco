package br.senai.sc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.senai.sc.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

	@Query(value = "SELECT * FROM ACCOUNTS WHERE PERSON_ID = :personId", nativeQuery = true)
	List<Account> findAllByPersonId(@Param("personId")Long personId);
	

}

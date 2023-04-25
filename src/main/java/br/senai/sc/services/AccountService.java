package br.senai.sc.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.senai.sc.dto.AccountDTO;
import br.senai.sc.dto.AccountInsertDTO;
import br.senai.sc.dto.ValueDTO;
import br.senai.sc.models.Account;
import br.senai.sc.models.Person;
import br.senai.sc.models.Transaction;
import br.senai.sc.repositories.AccountRepository;
import br.senai.sc.repositories.PersonRespository;
import br.senai.sc.repositories.TransactionRepository;
import br.senai.sc.services.exceptions.InvalidArgumentException;
import br.senai.sc.services.exceptions.ResourceNotFoundException;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PersonRespository personRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public AccountDTO save(AccountInsertDTO dto) {
		Account account = new Account();
		Person person = personRepository.findById(dto.getPersonId()).orElseThrow(() -> new  ResourceNotFoundException("Pessoa não cadastrada."));
		try {
			account.setNumber(dto.getNumber());			
			account.setPerson(person);
			account.deposit(0.00);
			account = accountRepository.save(account);
			return new AccountDTO(account);
		}catch(DataIntegrityViolationException e) {
			throw new InvalidArgumentException("Numero de conta ja existente.");
		}
	}
	
	public List<AccountDTO> findAll() {
		List<Account> list = accountRepository.findAll();
		return list.stream().map(x -> new AccountDTO(x)).collect(Collectors.toList());
	}
	
	public AccountDTO findById(Long id) {
		Account account = accountRepository.findById(id).orElseThrow(() -> new  ResourceNotFoundException("Conta não encontrada."));
		return new AccountDTO(account);
	}
	
	public AccountDTO update(AccountDTO dto, Long id) {
		Account account = accountRepository.findById(id).orElseThrow(() -> new  ResourceNotFoundException("Conta não encontrada."));
		account.setNumber(dto.getNumber());
		account = accountRepository.save(account);
		return new AccountDTO(account);
	}
	
	public void delete(Long id) {
		try {
			accountRepository.deleteById(id);			
		}catch (Exception e) {
			throw new ResourceNotFoundException("Pessoa não cadastrada.");
		}
	}
	
	public AccountDTO deposit(ValueDTO dto, Long id) {
		Account account = accountRepository.findById(id).orElseThrow(() -> new  ResourceNotFoundException("Conta não encontrada."));
		account.deposit(dto.getValue());
		account = accountRepository.save(account);
		Transaction transaction = new Transaction(null, dto.getValue(), Instant.now(), account);
		transactionRepository.save(transaction);
		return new AccountDTO(account);
	}

	public AccountDTO cashout(ValueDTO dto, Long id) {
		Account account = accountRepository.findById(id).orElseThrow(() -> new  ResourceNotFoundException("Conta não encontrada."));
		if(account.getBalance() < dto.getValue()) {
			throw new InvalidArgumentException("Valor inferior ao saldo disponivel.");
		}
		account.cashout(dto.getValue());
		account = accountRepository.save(account);
		Transaction transaction = new Transaction(null, -(dto.getValue()), Instant.now(), account);
		transactionRepository.save(transaction);
		return new AccountDTO(account);
	}
	
	public List<AccountDTO> findAllByPersonId(Long id){
		List<Account> list = accountRepository.findAllByPersonId(id);
		List<AccountDTO> listDTO = list.stream().map(x -> new AccountDTO(x)).collect(Collectors.toList());
		return listDTO;
	}
	

}

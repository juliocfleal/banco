package br.senai.sc.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senai.sc.dto.TransactionDTO;
import br.senai.sc.models.Transaction;
import br.senai.sc.repositories.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	public List<TransactionDTO> findAllByAccountId(Long id){
		List<Transaction> list = transactionRepository.findAllByAccountId(id);
		List<TransactionDTO> listDTO = list.stream().map(x -> new TransactionDTO(x)).collect(Collectors.toList());
		return listDTO;
	}
}

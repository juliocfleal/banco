package br.senai.sc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sc.dto.AccountDTO;
import br.senai.sc.dto.TransactionDTO;
import br.senai.sc.services.TransactionService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/transactions")
public class TransactionController {

	@Autowired
	TransactionService transactionService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<List<TransactionDTO>> findAllByAccountId(@PathVariable Long id) {
		List<TransactionDTO> response = transactionService.findAllByAccountId(id);
		return ResponseEntity.ok().body(response);
	}
}

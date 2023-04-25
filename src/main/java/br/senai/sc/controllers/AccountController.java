package br.senai.sc.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.senai.sc.dto.AccountDTO;
import br.senai.sc.dto.AccountInsertDTO;
import br.senai.sc.dto.ValueDTO;
import br.senai.sc.services.AccountService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;


	@PostMapping
	public ResponseEntity<AccountDTO> insert(@RequestBody AccountInsertDTO insertDTO) {
		AccountDTO response = accountService.save(insertDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId())
				.toUri();
		return ResponseEntity.created(uri).body(response);
	}


	@GetMapping
	public ResponseEntity<List<AccountDTO>> findAll() {
		List<AccountDTO> response = accountService.findAll();
		return ResponseEntity.ok().body(response);
	}


	@GetMapping(value = "/{id}")
	public ResponseEntity<AccountDTO> findById(@PathVariable Long id) {
		AccountDTO response = accountService.findById(id);
		return ResponseEntity.ok().body(response);
	}
	

	@PutMapping(value = "/{id}")
	public ResponseEntity<AccountDTO> update(@PathVariable Long id,@Valid @RequestBody AccountDTO dto){
		AccountDTO response = accountService.update(dto, id);
		return ResponseEntity.ok().body(response);
	}
	

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id){
		accountService.delete(id);
		return ResponseEntity.noContent().build();
	}
	

	@PutMapping(value = "/deposit/{id}")
	public ResponseEntity<AccountDTO> deposit(@PathVariable Long id,@Valid @RequestBody ValueDTO dto){
		AccountDTO response = accountService.deposit(dto, id);
		return ResponseEntity.ok().body(response);
	}

	
	@PutMapping(value = "/cashout/{id}")
	public ResponseEntity<AccountDTO> cashout(@PathVariable Long id,@Valid @RequestBody ValueDTO dto){
		AccountDTO response = accountService.cashout(dto, id);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value = "/person/{id}")
	public ResponseEntity<List<AccountDTO>> findAllByPersonId(@PathVariable Long id){
		List<AccountDTO> response = accountService.findAllByPersonId(id);
		return ResponseEntity.ok().body(response);
	}
	
}

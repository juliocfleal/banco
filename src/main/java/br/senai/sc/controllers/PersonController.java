package br.senai.sc.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import br.senai.sc.dto.PersonDTO;
import br.senai.sc.dto.PersonInsertDTO;
import br.senai.sc.services.PersonService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/persons")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@PostMapping
	public ResponseEntity<PersonDTO> insert(@Valid @RequestBody PersonInsertDTO dto){
		PersonDTO response = personService.save(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
		return ResponseEntity.created(uri).body(response);
	}
	

	@GetMapping
	public ResponseEntity<List<PersonDTO>> findAll(){
		List<PersonDTO> response = personService.findAll();
		return ResponseEntity.ok().body(response);
	}
	

	@GetMapping(value = "/{id}")
	public ResponseEntity<PersonDTO> findById(@PathVariable Long id){
	PersonDTO response = personService.findById(id);
	return ResponseEntity.ok().body(response);
	}
	

	@PutMapping(value = "/{id}")
	public ResponseEntity<PersonDTO> update(@PathVariable Long id,@Valid @RequestBody PersonDTO dto){
		PersonDTO response = personService.update(dto, id);
		return ResponseEntity.ok().body(response);
	}
	

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id){
		personService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}

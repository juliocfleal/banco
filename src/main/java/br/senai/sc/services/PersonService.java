package br.senai.sc.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senai.sc.dto.PersonDTO;
import br.senai.sc.dto.PersonInsertDTO;
import br.senai.sc.models.Person;
import br.senai.sc.repositories.PersonRespository;
import br.senai.sc.services.exceptions.ResourceNotFoundException;

@Service
public class PersonService {
	
	@Autowired
	private PersonRespository personRepository;
	
	public PersonDTO save(PersonInsertDTO dto) {
		Person person = new Person();
		person.setName(dto.getName());
		person.setCPF(dto.getCPF());
		person.setAddress(dto.getAddress());
		person = personRepository.save(person);
		return new PersonDTO(person);
	}
	
	public List<PersonDTO> findAll(){
		List<Person> list = personRepository.findAll();
		return list.stream().map(x -> new PersonDTO(x)).collect(Collectors.toList());
	}
	
	public PersonDTO findById(Long id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new  ResourceNotFoundException("Pessoa não cadastrada."));
		return new PersonDTO(person);
	}
	
	public PersonDTO update(PersonDTO dto, Long id){
		Person person = personRepository.findById(id).orElseThrow(() -> new  ResourceNotFoundException("Pessoa não cadastrada."));
		person.setName(dto.getName());
		person.setAddress(dto.getAddress());
		person.setCPF(dto.getCPF());
		person = personRepository.save(person);
		return new PersonDTO(person);
	}
	
	public void delete(Long id) {
		try {
			personRepository.deleteById(id);			
		}catch (Exception e) {
			throw new ResourceNotFoundException("Pessoa não cadastrada.");
		}
	}

}

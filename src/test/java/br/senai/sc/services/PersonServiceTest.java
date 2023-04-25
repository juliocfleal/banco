package br.senai.sc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.senai.sc.dto.PersonDTO;
import br.senai.sc.dto.PersonInsertDTO;
import br.senai.sc.models.Person;
import br.senai.sc.repositories.PersonRespository;
import br.senai.sc.services.exceptions.ResourceNotFoundException;

@WebMvcTest(SpringExtension.class)
public class PersonServiceTest {

	@InjectMocks
	private PersonService service;
	
	@Mock
	private PersonRespository repository;
	
	private Long validId;
	private Long invalidId;
	private Long totalPersons;
	private Person person1 = new Person(1L,"Joao da Silva" ,"18718776535", "Rua Helena Blavatsky, 333", null);
	private Person person2 = new Person(2L,"Maria Helena Alves" ,"12332112312","Rua Harvey Spencer Lewis, 123", null);
	private Person person3 = new Person(null,"Ana Apolinario", "98798798778","Rua Leonardo DaVinci, 777", null);
	private PersonInsertDTO insertDTO = new PersonInsertDTO();
	private List<Person> list = new ArrayList<>();
	private PersonDTO dto = new PersonDTO(person1);

	@BeforeEach
	void setUp() throws Exception{
		validId = 1L;
		invalidId = 9999L;
		totalPersons = 2L;
		list.add(person1);
		list.add(person2);
		insertDTO.setCPF(person1.getCPF());
		insertDTO.setAddress(person1.getAddress());
		
		Mockito.when(repository.findAll()).thenReturn(list);
		Mockito.when(repository.save(Mockito.any())).thenReturn(person1);
		Mockito.when(repository.findById(validId)).thenReturn(Optional.of(person1));
		Mockito.doThrow(ResourceNotFoundException.class).when(repository).deleteById(invalidId);
	}
	
	@Test
	public void saveNewPersonShouldSavePerson() {
		PersonDTO dtoResponse = service.save(insertDTO);
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(person1.getCPF(), dtoResponse.getCPF());
		Assertions.assertEquals(person1.getAddress(), dtoResponse.getAddress());

	}
	
	@Test
	public void findByIdShouldReturnPerson() {
		PersonDTO dto = service.findById(validId);
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(person1.getId(), dto.getId());
		Assertions.assertEquals(person1.getCPF(), dto.getCPF());
		Assertions.assertEquals(person1.getAddress(), dto.getAddress());

	}
	
	@Test
	public void findByIdShouldThrowException() {
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			service.findById(invalidId);			
		});
	}
	
	@Test
	public void updateShouldUpdatePerson() {
		PersonDTO dtoResponse = service.update(dto, validId);
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(person1.getId(), dtoResponse.getId());
		Assertions.assertEquals(person1.getCPF(), dtoResponse.getCPF());
		Assertions.assertEquals(person1.getAddress(), dtoResponse.getAddress());
	}
	
	@Test
	public void findAllShouldReturnAllPersons() {
		List<PersonDTO> list = service.findAll();
		Assertions.assertNotNull(list);
		Assertions.assertEquals(totalPersons, list.size());
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdIsValid() {
		Assertions.assertDoesNotThrow(()->{
			service.delete(validId);
		});
		Mockito.verify(repository, Mockito.times(1)).deleteById(validId);
	}
	
	@Test 
	public void deleteShouldThrowExceptionWhenIdIsInvalid() {
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			service.delete(invalidId);
		});
	}
}


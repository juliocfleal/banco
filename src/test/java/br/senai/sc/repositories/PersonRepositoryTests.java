package br.senai.sc.repositories;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import br.senai.sc.models.Person;
import br.senai.sc.services.exceptions.ResourceNotFoundException;

@DataJpaTest
public class PersonRepositoryTests {
	
	@Autowired
	private PersonRespository repository;

	private Long validId;
	private Long invalidId;
	private Long totalPersons;
	private Person person = new Person();
	
	@BeforeEach
	void setUp() throws Exception{
		validId = 1L;
		invalidId = 9999L;
		totalPersons = 4L;
		person.setCPF("18718776535");
		person.setAddress("Rua Helena Blavatsky, 333");
	}
	
	@Test
	public void insertNewPersonShoudReturnNewPerson() {
		person = repository.save(person);
		Assertions.assertEquals("18718776535", person.getCPF());
		Assertions.assertEquals("Rua Helena Blavatsky, 333", person.getAddress());
	}
	
	@Test
	public void findPersonByIdShouldReturnPerson() {
		Optional<Person> personTest = repository.findById(validId);
		Assertions.assertEquals(validId, personTest.get().getId());
		Assertions.assertEquals("10543789675", personTest.get().getCPF());
	}

	@Test
	public void findByIdShouldReturnEmptyWhenIdIsInvalid() {
		Assertions.assertEquals( repository.findById(invalidId), Optional.empty());
	}
	
	@Test
	public void findAllShouldReturnAllPersons() {
		List<Person> list = repository.findAll();
		Assertions.assertEquals(totalPersons, list.size());
		Assertions.assertEquals("10543789675", list.get(0).getCPF());
	}
	

	@Test
	public void deletePersonShouldThrowExceptionWhenIdIsInvalid() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, ()->{
			repository.deleteById(invalidId);
	});
	}
	
}

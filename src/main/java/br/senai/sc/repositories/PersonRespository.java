package br.senai.sc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sc.models.Person;

public interface PersonRespository extends JpaRepository<Person, Long>{

}

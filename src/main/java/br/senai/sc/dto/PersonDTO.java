package br.senai.sc.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import br.senai.sc.models.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
	
	private Long id;
	
    @NotEmpty(message = "Campo obrigatório!")
	private String name;
	
    @NotEmpty(message = "Campo obrigatório!")
    @Size(min=11, message = "Campo obrigatório!")
    @NumberFormat
	private String CPF;
    
    @NotEmpty(message = "Campo obrigatório!")
	private String address;
    
    public PersonDTO(Person person) {
    	this.name = person.getName();
    	this.id = person.getId();
    	this.CPF = person.getCPF();
    	this.address = person.getAddress();
    }
}

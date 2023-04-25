package br.senai.sc.dto;

import javax.validation.constraints.NotEmpty;

import br.senai.sc.models.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

	
	private Long id;
	
    @NotEmpty(message = "Campo obrigatório!")
	private String number;
    
    private Double balance;
	
	private PersonDTO personDTO = new PersonDTO();
	
	public AccountDTO(Account account) {
		this.id = account.getId();
		this.number = account.getNumber();
		this.balance = account.getBalance();
		personDTO.setName(account.getPerson().getName());
		personDTO.setId(account.getPerson().getId());
		personDTO.setCPF(account.getPerson().getCPF());
		personDTO.setAddress(account.getPerson().getAddress());
	}
}

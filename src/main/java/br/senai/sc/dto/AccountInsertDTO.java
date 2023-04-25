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
public class AccountInsertDTO {

    @NotEmpty(message = "Campo obrigatório!")
	private String number;
	
    @NotEmpty(message = "Campo obrigatório!")
	private Long personId;
	
}

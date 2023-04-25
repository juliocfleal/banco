package br.senai.sc.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.senai.sc.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
    private Long id;

    @NotEmpty(message = "Campo obrigatório!")
    private String name;

    @NotEmpty(message = "Campo obrigatório!")
    @Email(message = "E-mail inválido!")
    @Column(unique = true)
    private String email;
    
    public UserDTO(User user) {
    	this.id = user.getId();
    	this.email = user.getEmail();
    }

}

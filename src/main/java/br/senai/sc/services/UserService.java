package br.senai.sc.services;

import br.senai.sc.dto.EmailDTO;
import br.senai.sc.dto.UserDTO;
import br.senai.sc.models.User;
import br.senai.sc.repositories.UserRepository;
import br.senai.sc.services.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

@Service
public class UserService {
    private static final Logger LOG = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void save() {
        try {
            User user = new User();
            user.setName("Usuário");
            user.setEmail("usuario@sc.senai.br");
            userRepository.save(user);
        } catch (Exception e) {
            LOG.severe(e.getMessage());
        }
    }

    public UserDTO findByEmail(EmailDTO email) {
    	try {
        User user = userRepository.findByEmail(email.getEmail());
        return new UserDTO(user);
    	}catch (Exception e) {
            LOG.severe(e.getMessage());
			throw new ResourceNotFoundException("Este email nao foi encontrado no nosso banco de dados");
		}
        
        
    }
}

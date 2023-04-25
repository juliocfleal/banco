package br.senai.sc.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sc.dto.EmailDTO;
import br.senai.sc.dto.UserDTO;
import br.senai.sc.services.UserService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{email}")
    public ResponseEntity<UserDTO> findByEmail(@Valid @PathVariable EmailDTO email) {
        UserDTO user = userService.findByEmail(email);
        return ResponseEntity.ok().body(user);
    }
}

package br.senai.sc.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.senai.sc.dto.PersonInsertDTO;
import br.senai.sc.models.Person;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerITests {

	private Long validId;
	private Long invalidId;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception{
		validId = 1L;
		invalidId = 9999L;
	}
	
	@Test
	public void createPersonShouldCreateNewPerson() throws Exception{
		PersonInsertDTO person = new PersonInsertDTO("Julio Leal","10543789675", "Rua LeonardoDaVinci, 336");
		String jsonBody = objectMapper.writeValueAsString(person);
		ResultActions result = mockMvc.perform(post("/persons")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.cpf").exists());
		result.andExpect(jsonPath("$.cpf").value("10543789675"));
		result.andExpect(jsonPath("$.address").exists());
		result.andExpect(jsonPath("$.address").value("Rua LeonardoDaVinci, 336"));
	}
	
	
	@Test
	public void findAllPersonsShouldReturnAllPersons() throws Exception{
		ResultActions result = mockMvc.perform(get("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$[0].id").exists());
		result.andExpect(jsonPath("$[0].id").value(1));
		result.andExpect(jsonPath("$[1].cpf").exists());
		result.andExpect(jsonPath("$[1].cpf").value("98798798773"));
		result.andExpect(jsonPath("$[1].address").exists());
		result.andExpect(jsonPath("$[1].address").value("Rua Giordano Bruno, 226"));
	}
	
	@Test
	public void findAllPersonsByIdShouldReturnPersons() throws Exception{
		ResultActions result = mockMvc.perform(get("/persons/{id}", validId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.id").value(1));
		result.andExpect(jsonPath("$.cpf").exists());
		result.andExpect(jsonPath("$.cpf").value("10543789675"));
		result.andExpect(jsonPath("$.address").exists());
		result.andExpect(jsonPath("$.address").value("Rua Monteiro Lobato, 622"));
	}
	
	@Test
	public void updatePersonShouldUpdatePerson() throws Exception{
		Person person = new Person(1L,"Julio Leal" ,"10510501534", "Rua Helena Blavatsky, 333", null);
		String jsonBody = objectMapper.writeValueAsString(person);
		ResultActions result = mockMvc.perform(put("/persons/{id}", validId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.id").value(1));
		result.andExpect(jsonPath("$.cpf").exists());
		result.andExpect(jsonPath("$.cpf").value("10510501534"));
		result.andExpect(jsonPath("$.address").exists());
		result.andExpect(jsonPath("$.address").value("Rua Helena Blavatsky, 333"));
	}
	
	@Test
	public void deletePersonShouldReturnIsOK() throws Exception{
		ResultActions result = mockMvc.perform(delete("/persons/{id}", validId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNoContent());
	}
}

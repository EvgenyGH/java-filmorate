package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class FilmorateApplicationTests {
	@Autowired
	ObjectMapper mapper;
	@Autowired
	MockMvc mockMvc;
	@Test
	void test1PostPutGetUserTests() throws Exception {
		User user = new User();
		user.setEmail("j3000@bk.ru");
		user.setLogin("Login");
		user.setName("Name");
		user.setBirthday(LocalDate.of(1957, 12, 12));

		String body = mapper.writeValueAsString(user);
		mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		user.setEmail("   ");
		mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError());
		user.setEmail(null);
		mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError());
		user.setEmail("j3000bk.ru");
		mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError());
		user.setEmail("j3000@bk.ru");

		user.setLogin(null);
		mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError());
		user.setLogin("   ");
		mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError());
		user.setLogin("");
		mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError());
		user.setLogin("Login");

		user.setName(null);
		mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError());
		user.setName("   ");
		mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError());
		user.setName("");
		mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError());
		user.setName("Name");

		user.setBirthday(LocalDate.now().plusDays(1));
		mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError());
	}

}

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
		user.setId(1);
		user.setEmail("j3000@bk.ru");
		user.setLogin("Login");
		user.setName("Name");
		user.setBirthday(LocalDate.of(1957, 12, 12));

		String body = mapper.writeValueAsString(user);
		mockMvc.perform(post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		user.setId(0);
		assertThrows(ValidationException.class, user::validate);
		user.setId(1);

		user.setEmail("   ");
		assertThrows(ValidationException.class, user::validate);
		user.setEmail(null);
		assertThrows(ValidationException.class, user::validate);
		user.setEmail("j3000bk.ru");
		assertThrows(ValidationException.class, user::validate);
		user.setEmail("j3000@bk.ru");

		user.setLogin(null);
		assertThrows(ValidationException.class, user::validate);
		user.setLogin("   ");
		assertThrows(ValidationException.class, user::validate);
		user.setLogin("");
		assertThrows(ValidationException.class, user::validate);
		user.setLogin("Login");

		user.setName(null);
		assertDoesNotThrow(user::validate);
		user.setName("   ");
		assertDoesNotThrow(user::validate);
		user.setName("");
		assertDoesNotThrow(user::validate);
		user.setName("Name");

		user.setBirthday(LocalDate.now().plusDays(1));
		assertThrows(ValidationException.class, user::validate);
	}

}

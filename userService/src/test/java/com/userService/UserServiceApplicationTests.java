package com.userService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("Application Context Loaded Successfully");
	}

}

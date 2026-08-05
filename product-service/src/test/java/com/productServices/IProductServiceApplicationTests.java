package com.productServices;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class IProductServiceApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("Application Context Loaded Successfully");
	}

}

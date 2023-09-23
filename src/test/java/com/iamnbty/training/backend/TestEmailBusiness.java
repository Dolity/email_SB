package com.iamnbty.training.backend;

import com.iamnbty.training.backend.business.EmailBusiness;
import com.iamnbty.training.backend.entity.Address;
import com.iamnbty.training.backend.entity.Social;
import com.iamnbty.training.backend.entity.User;
import com.iamnbty.training.backend.exception.BaseException;
import com.iamnbty.training.backend.service.AddressService;
import com.iamnbty.training.backend.service.EmailService;
import com.iamnbty.training.backend.service.SocialService;
import com.iamnbty.training.backend.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestEmailBusiness {

	@Autowired
	private EmailBusiness emailBusiness;

	@Order(1)
	@Test
	void testSendActivateEmail()  throws BaseException {
		emailBusiness.sendActivateUserEmail(
				TestData.email,
				TestData.name,
				TestData.token
		);

	}

	interface TestData {

		String email = "jakkit852789@gmail.com";

		String name = "Dolity";

		String  token = "m#@:ASDWDGFLEGLRPPPP";

	}



}

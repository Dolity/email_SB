package com.iamnbty.training.backend;

import com.iamnbty.training.backend.entity.Address;
import com.iamnbty.training.backend.entity.Social;
import com.iamnbty.training.backend.entity.User;
import com.iamnbty.training.backend.exception.BaseException;
import com.iamnbty.training.backend.service.AddressService;
import com.iamnbty.training.backend.service.SocialService;
import com.iamnbty.training.backend.service.UserService;
import com.iamnbty.training.backend.util.SecurityUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestUserService {

	@Autowired
	private UserService userService;

	@Autowired
	private SocialService socialService;

	@Autowired
	private AddressService addressService;

	@Order(1)
	@Test
	void testCreate()  throws BaseException {
		String  token = SecurityUtil.generateToken();
		User user = userService.create(
				TestCreateData.email,
				TestCreateData.password,
				TestCreateData.name,
				token
		);

		//Check not null
		Assertions.assertNotNull(user);
		Assertions.assertNotNull(user.getId());

		//Check equals
		Assertions.assertEquals(TestCreateData.email, user.getEmail());

		boolean isMatched = userService.matchPassword(TestCreateData.password, user.getPassword());
		Assertions.assertTrue(isMatched);

		Assertions.assertEquals(TestCreateData.name, user.getName());
	}

	@Order(2)
	@Test
	void testUpdate() throws BaseException {
		Optional<User> opt = userService.findByEmail(TestCreateData.email);
		Assertions.assertTrue(opt.isPresent());

		 User user = opt.get();

		 User updateUser = userService.updateName(user.getId(), TestUpdateData.name);

		 Assertions.assertNotNull(updateUser);
		 Assertions.assertEquals(TestUpdateData.name, updateUser.getName());


	}

	@Order(3)
	@Test
	void testCreateSocial() throws BaseException {
		Optional<User> opt = userService.findByEmail(TestCreateData.email);
		Assertions.assertTrue(opt.isPresent());

		User user = opt.get();

		Social social = user.getSocial();
		Assertions.assertNull(social);

		 social = socialService.create(user,
				SocialTestCreateData.facebook,
				SocialTestCreateData.line,
				SocialTestCreateData.instagram,
				SocialTestCreateData.tiktok
		);
		Assertions.assertNotNull(social);
		Assertions.assertEquals(SocialTestCreateData.facebook, social.getFacebook());
	}

	@Order(4)
	@Test
	void testCreateAddress() throws BaseException {
		Optional<User> opt = userService.findByEmail(TestCreateData.email);
		Assertions.assertTrue(opt.isPresent());

		User user = opt.get();
		List<Address> addresses = user.getAddresses();
		Assertions.assertTrue(addresses.isEmpty());

		CreateAddress(user, AddressTestCreateData.line1, AddressTestCreateData.line2, AddressTestCreateData.zipcode);
		CreateAddress(user, AddressTestCreateData2.line1, AddressTestCreateData.line2, AddressTestCreateData.zipcode);

	}

	private  void  CreateAddress(User user, String line1, String line2, String zipcode) {
		Address address = addressService.create(user,
				line1,
				line2,
				zipcode
				);
		Assertions.assertNotNull(address);
		Assertions.assertEquals(line1, address.getLine1());
		Assertions.assertEquals(line2, address.getLine2());
		Assertions.assertEquals(zipcode, address.getZipcode());
	}

	@Order(5)
	@Test
	void testDelete() {
		Optional<User> opt = userService.findByEmail(TestCreateData.email);
		Assertions.assertTrue(opt.isPresent());

		User user = opt.get();

		//Check Social
		Social social = user.getSocial();
		Assertions.assertNotNull(social);
		Assertions.assertEquals(SocialTestCreateData.facebook, social.getFacebook());

		// Check Address
		List<Address> addresses = user.getAddresses();
		Assertions.assertFalse(addresses.isEmpty());
		Assertions.assertEquals(2, addresses.size());

		userService.deleteById(user.getId());

		Optional<User> optDelete = userService.findByEmail(TestCreateData.email);
		Assertions.assertTrue(optDelete.isEmpty());

	}

	interface TestCreateData {
		String email = "test2@gg.com";
		String password = "abc1234";
		String name = "Java spring boot";
	}

	interface  SocialTestCreateData {
		String facebook = "Jakin";
		String line = "JakinLI";
		String instagram = "JakinIG";
		String tiktok = "JakinTT";
	}

	interface AddressTestCreateData {
		String line1 = "69/4";
		String line2 = "21/1";
		String zipcode = "1122";
	}

	interface AddressTestCreateData2 {
		String line1 = "70/5";
		String line2 = "22/2";
		String zipcode = "2233";
	}

	interface TestUpdateData {
		String name = "JavaScript";
	}

}

package com.example.userCRUD;

import com.example.userCRUD.models.User;
import com.example.userCRUD.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestExecution;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
class UserCrudApplicationTests {

	@Autowired
	UserRepository repository;

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	MockMvc mockMvc;

	@BeforeTestExecution
	void generateUser(){
		repository.deleteAll();

		repository.save(new User("Beta", "beta@gmail.com", "passwordbeta", "alamat beta"));
		repository.save(new User("Charlie", "charlie@gmail.com", "pass charlie", "alamat charlie"));
		repository.save(new User("Delta", "delta@gmail.com", "pass delta", "alamat delta"));
		repository.save(new User("Epsilon", "epsilon@gmail.com", "pass epsilon", "alamat epsilon"));
		repository.save(new User("Fanta", "fanta@gmail.com", "pass fanta", "alamat fanta"));
		repository.save(new User("Giga", "giga@gmail.com", "pass giga", "alamat giga"));
		repository.save(new User("Helios", "helios@gmail.com", "pass helios", "alamat helios"));
		repository.save(new User("Insania", "insania@gmail.com", "pass insania", "alamat insania"));
		repository.save(new User("Jack", "jack@gmail.com", "pass jack", "alamat jack"));
		repository.save(new User("Korg", "korg@gmail.com", "pass korg", "alamat korg"));
		repository.save(new User("Leo", "leo@gmail.com", "pass leo", "alamat leo"));
	}

	@AfterTestExecution
	void deleteDatabaseUser(){
		repository.deleteAll();
	}

//	@Test
//	void getAll() throws Exception{
//		mockMvc.perform(MockMvcRequestBuilders.get("user/all")).andDo(MockMvcResultHandlers.print());
//	}
}

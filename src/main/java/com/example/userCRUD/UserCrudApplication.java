package com.example.userCRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserCrudApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserCrudApplication.class, args);
  }

  //    @Bean
  //    CommandLineRunner initDatabase(UserRepository repository) {
  //
  //      return args -> {
  //        			System.out.println(repository.save(new User("Budi", "budi@gmail.com", "passwordBudi", "alamat budi")));
  //        			System.out.println(repository.save(new User("Charlie", "charlie@gmail.com", "pass charlie", "alamat charlie")));
  //        			System.out.println(repository.save(new User("Delta", "delta@gmail.com", "pass delta", "alamat delta")));
  //        			System.out.println(repository.save(new User("Epsilon", "epsilon@gmail.com", "pass epsilon", "alamat epsilon")));
  //        			System.out.println(repository.save(new User("Fanta", "fanta@gmail.com", "pass fanta", "alamat fanta")));
  //        			System.out.println(repository.save(new User("Giga", "giga@gmail.com", "pass giga", "alamat giga")));
  //        			System.out.println(repository.save(new User("Helios", "helios@gmail.com", "pass helios", "alamat helios")));
  //        			System.out.println(repository.save(new User("Insania", "insania@gmail.com", "pass insania", "alamat insania")));
  //      };
  //    }

}

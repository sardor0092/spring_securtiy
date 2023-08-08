package uz.securty.securty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.securty.securty.entity.Lavozim;
import uz.securty.securty.entity.User;
import uz.securty.securty.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class SecurtyApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(SecurtyApplication.class, args);
	}

	@Autowired
	UserRepository userRepository;

			@Autowired
			PasswordEncoder encoder;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Optional<User> us = userRepository.findByLogin("admin123");

		if(!us.isPresent()){
			User   u= new User();
			u.setIsm("Sardor");
			u.setFamiliya("Xaydarov");
			u.setLogin("admin123");
			u.setParol(encoder.encode("admin123"));
			u.setActive(true);
//			u.setLavozims(Set.o);
			userRepository.save(u);

		}







	}
}

package com.example.sisdi_users.bootstrapping;


import com.example.sisdi_users.usermanagement.model.AuthorityRole;
import com.example.sisdi_users.usermanagement.model.Gender;
import com.example.sisdi_users.usermanagement.model.UserJPA;
import com.example.sisdi_users.usermanagement.repositories.UserDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
public class UserBootstrapper implements CommandLineRunner {

	@Value("${server.port}")
	private int serverPort;

	private final PasswordEncoder encoder;

	private final UserDBRepository userDBRepository;

	@Override
	public void run(String... args) throws Exception {
		if (this.serverPort == 8083) {
			if (userDBRepository.findByUsername("river@mail.com").isEmpty()) {
				//marketing director
				UserJPA u2 = UserJPA.newUser(encoder.encode("myPass13"),"river@mail.com",LocalDateTime.of(2000,3,20,13,23),"River","123456789","912345678",Gender.MALE,AuthorityRole.MARKETING_DIRECTOR);
				userDBRepository.save(u2);
			}
			if (userDBRepository.findByUsername("mariana@mail.com").isEmpty()) {
				//admin
				UserJPA u1 = UserJPA.newUser(encoder.encode("myPass14"),"mariana@mail.com",LocalDateTime.of(2000,3,20,13,23),"Mariana","123456789","912345678",Gender.FEMALE,AuthorityRole.ADMIN);
				userDBRepository.save(u1);
			}
		}
	}
}

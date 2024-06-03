package com.example.sisdi_users.usermanagement.service;

import com.example.sisdi_users.usermanagement.model.AuthorityRole;
import com.example.sisdi_users.usermanagement.model.UserJPA;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.sisdi_users.usermanagement.api.proto.UserRequests.AuthRequest;
import com.example.sisdi_users.usermanagement.api.proto.UserRequests.CreateSubscriptionRequest;

@Service
@RequiredArgsConstructor
public class UserService {



	private final PasswordEncoder encoder;


	private final UserRepository userRepository;
	public UserJPA getByUsername(String username, boolean internal) throws Exception {
		if (!internal) {
			return userRepository.findByUsername(username, false);
		} else {
			return userRepository.findByUsername(username, true);
		}
	}

	public UserJPA create(UserJPA jpa, CreateSubscriptionRequest request) throws Exception {
		jpa.setPassword(encoder.encode(jpa.getPassword()));


		if (jpa.getRole().equals(AuthorityRole.SUBSCRIBER)) {
			return userRepository.create(jpa,request);
		}

		return userRepository.create(jpa, null);
	}

	public String login(AuthRequest request, boolean internal) throws Exception{
		if (!internal) {
			return userRepository.login(request, false);
		} else {
			return userRepository.login(request, true);
		}
	}
}

package com.example.sisdi_users.usermanagement.service;

import com.example.sisdi_users.auth.api.AuthRequest;
import com.example.sisdi_users.usermanagement.api.CreateSubscriberRequest;
import com.example.sisdi_users.usermanagement.api.CreateUserRequest;
import com.example.sisdi_users.usermanagement.api.UserDTOMapper;
import com.example.sisdi_users.usermanagement.model.AuthorityRole;
import com.example.sisdi_users.usermanagement.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {



	private final PasswordEncoder encoder;


	private final UserDTOMapper mapper;

	private final UserRepository userRepository;
	public User getByUsername(String username, boolean internal) throws Exception {
		if (!internal) {
			return userRepository.findByUsername(username, false);
		} else {
			return userRepository.findByUsername(username, true);
		}
	}

	public User create(CreateUserRequest resource) throws Exception {
		resource.setPassword(encoder.encode(resource.getPassword()));
		User user = mapper.create(resource);

		if (user.getRole().equals(AuthorityRole.SUBSCRIBER)) {
			CreateSubscriberRequest request = new CreateSubscriberRequest(resource.getUsername(),resource.getPlan(),resource.getFeeType(),resource.getPaymentMethod(),resource.getInitialDate());
			return userRepository.create(user,request);
		}

		return userRepository.create(user, null);
	}

	public String login(AuthRequest request, boolean internal) throws Exception{
		if (!internal) {
			return userRepository.login(request, false);
		} else {
			return userRepository.login(request, true);
		}
	}
}

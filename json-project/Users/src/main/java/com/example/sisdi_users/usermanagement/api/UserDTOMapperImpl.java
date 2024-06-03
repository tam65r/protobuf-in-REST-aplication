package com.example.sisdi_users.usermanagement.api;


import com.example.sisdi_users.usermanagement.model.Gender;
import com.example.sisdi_users.usermanagement.model.User;
import com.example.sisdi_users.utils.Utils;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Generated(
		value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class UserDTOMapperImpl extends UserDTOMapper {
	@Override
	public UserDTO toUserView(User user) {
		if (user == null) {
			return null;
		}

		UserDTO subDTO = new UserDTO();
		subDTO.setUsername(user.getUsername());
		subDTO.setName(user.getName());
		subDTO.setBirthday(user.getBirthday().toString());
		subDTO.setSex(user.getSex().toString());
		subDTO.setPhoneNumber(user.getPhoneNumber());
		subDTO.setCitizenCardNumber(user.getCitizenCardNumber());

		return subDTO;
	}

	@Override
	public Iterable<UserDTO> toUserView(Iterable<User> subscribers) {
		if ( subscribers == null ) {
			return null;
		}

		ArrayList<UserDTO> iterable = new ArrayList<UserDTO>();
		for ( User sub : subscribers ) {
			iterable.add(toUserView(sub));
		}

		return iterable;
	}

	@Override
	public User create(CreateUserRequest resource) {
		if (resource == null) {
			return null;
		}
		String password = resource.getPassword();
		String name = resource.getName();
		String username = resource.getUsername();
		String phoneNumber = resource.getPhoneNumber();
		String birthday = resource.getBirthday();
		String sex = resource.getSex();
		String citizerCardNumber = resource.getCitizenCardNumber();
		String role = resource.getRole();


		LocalDateTime date = Utils.parseString(resource.getBirthday());


		User user = User.newUser(password,username,date,name,citizerCardNumber,phoneNumber,Gender.fromString(sex), role);

		return user;
	}
}

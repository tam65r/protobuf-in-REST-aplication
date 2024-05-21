package com.example.sisdi_users.usermanagement.api;


import com.example.sisdi_users.usermanagement.model.Gender;
import com.example.sisdi_users.usermanagement.model.UserJPA;
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
	public UserDTO toUserView(UserJPA userJPA) {
		if (userJPA == null) {
			return null;
		}

		UserDTO subDTO = new UserDTO();
		subDTO.setUsername(userJPA.getUsername());
		subDTO.setName(userJPA.getName());
		subDTO.setBirthday(userJPA.getBirthday().toString());
		subDTO.setSex(userJPA.getSex().toString());
		subDTO.setPhoneNumber(userJPA.getPhoneNumber());
		subDTO.setCitizenCardNumber(userJPA.getCitizenCardNumber());

		return subDTO;
	}

	@Override
	public Iterable<UserDTO> toUserView(Iterable<UserJPA> subscribers) {
		if ( subscribers == null ) {
			return null;
		}

		ArrayList<UserDTO> iterable = new ArrayList<UserDTO>();
		for ( UserJPA sub : subscribers ) {
			iterable.add(toUserView(sub));
		}

		return iterable;
	}

	@Override
	public UserJPA create(CreateUserRequest resource) {
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


		UserJPA userJPA = UserJPA.newUser(password,username,date,name,citizerCardNumber,phoneNumber,Gender.fromString(sex), role);

		return userJPA;
	}
}

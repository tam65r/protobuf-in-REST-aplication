package com.example.sisdi_users.usermanagement.api;

import com.example.sisdi_users.usermanagement.model.UserJPA;

public abstract class UserDTOMapper {

	public abstract UserDTO toUserView (UserJPA userJPA);

	public abstract Iterable<UserDTO> toUserView(Iterable<UserJPA> subscribers);

	public abstract UserJPA create(CreateUserRequest resource);
}

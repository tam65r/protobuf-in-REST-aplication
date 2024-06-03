package com.example.sisdi_users.usermanagement.api;

import com.example.sisdi_users.usermanagement.model.User;

public abstract class UserDTOMapper {

	public abstract UserDTO toUserView (User user);

	public abstract Iterable<UserDTO> toUserView(Iterable<User> subscribers);

	public abstract User create(CreateUserRequest resource);
}

package br.com.becb.api.users.service;

import br.com.becb.api.users.api.users.shared.UserDto;

public interface UserService {

	UserDto createUser(UserDto userDetails);
}

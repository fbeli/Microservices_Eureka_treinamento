package br.com.becb.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.becb.api.users.api.users.shared.UserDto;

public interface UserService extends UserDetailsService {

	UserDto createUser(UserDto userDetails);
	UserDto getUserDetailsByEmail(String email);
	
	
	
}

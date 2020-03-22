package br.com.becb.api.users.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.becb.api.users.api.users.shared.UserDto;
import br.com.becb.api.users.data.UserEntity;
import br.com.becb.api.users.data.UserRepository;


@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) 
	{
		this.userRepository = userRepository;
	}

	@Override
	public UserDto createUser(UserDto userDetails) {
	
		//por segurança não se usa o id gerado pelo Banco, mas usa-se o gerado aleatoriamente.
		userDetails.setUserId(UUID.randomUUID().toString());
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		
		userEntity.setEncryptedPassword("encryptedPassword");
		userRepository.save(userEntity);
		
		return null;
	}

}

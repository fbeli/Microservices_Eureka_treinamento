package br.com.becb.api.users.service;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.becb.api.users.api.users.shared.UserDto;
import br.com.becb.api.users.data.UserEntity;
import br.com.becb.api.users.data.UserRepository;


@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder) 
	{
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder= bCryptPasswordEncoder;
	}

	@Override
	public UserDto createUser(UserDto userDetails) {
	
		//por segurança não se usa o id gerado pelo Banco, mas usa-se o gerado aleatoriamente.
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		
		//userEntity.setEncryptedPassword("encryptedPassword");
		userRepository.save(userEntity);
		
		return modelMapper.map(userEntity,UserDto.class);
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username);
		
		if(null == userEntity) throw new UsernameNotFoundException(username);
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), 
				true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if(null == userEntity) throw new UsernameNotFoundException(email);
		
		return new ModelMapper().map(userEntity, UserDto.class);

	}

}

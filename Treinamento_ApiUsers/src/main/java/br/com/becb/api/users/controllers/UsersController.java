package br.com.becb.api.users.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.becb.api.users.api.model.CreateUserRequestModel;
import br.com.becb.api.users.api.model.CreateUserResponseModel;
import br.com.becb.api.users.api.users.shared.UserDto;
import br.com.becb.api.users.service.UserService;

@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private Environment env;
	
	@Autowired
	UserService userService;
		
	@GetMapping("/status/check")
	public String status() {		
		System.out.println("IP de Origem: "+env.getProperty("gateway.ip"));
		return "working on port:-> "+env.getProperty("local.server.port") + 
				" <p>with Token: "+env.getProperty("token.secret")+
				" <p>with mensagem: "+env.getProperty("mensagem.pra.vc");
		 
			
		
		
		
	}
	@GetMapping("/status/env")
	public String env() {		
		return env.toString();
	}
	
	@PostMapping
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		UserDto createdUser = userService.createUser(userDto);
		
		CreateUserResponseModel body = modelMapper.map(createdUser,CreateUserResponseModel.class);
		
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}
}

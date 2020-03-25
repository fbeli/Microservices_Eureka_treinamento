package br.com.becb.api.users.api.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.becb.api.users.api.model.LoginRequestModel;
import br.com.becb.api.users.api.users.shared.UserDto;
import br.com.becb.api.users.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	

	private UserService userService;
	private Environment env;
	
	
	public AuthenticationFilter(UserService userservice, Environment env, AuthenticationManager authManager) {
		super();
		this.userService = userservice;
		this.env = env;
		super.setAuthenticationManager(authManager);
		
		
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest req, 
			HttpServletResponse res,
			FilterChain chain,
			Authentication auth) throws IOException, ServletException{
				
		String userName = ((User) auth.getPrincipal()).getUsername();
		UserDto userDetails = userService.getUserDetailsByEmail(userName);
		
		String token = Jwts.builder()
				.setSubject(userDetails.getUserId())
				//.setSubject("email:"+userDetails.getEmail())
				.setExpiration(new Date(System.currentTimeMillis() +
						Long.parseLong(env.getProperty("token.expiration"))))
				.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
				.compact();
		System.out.println("Token: "+token);
				
		res.addHeader("token", token);
		res.addHeader("userId", userDetails.getUserId());
		
		}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, 
			HttpServletResponse response) throws AuthenticationException {

		try {
			LoginRequestModel credencials = new ObjectMapper().readValue(request.getInputStream(),
					LoginRequestModel.class);
			return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					credencials.getEmail(), credencials.getPassword(), new ArrayList<>()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}

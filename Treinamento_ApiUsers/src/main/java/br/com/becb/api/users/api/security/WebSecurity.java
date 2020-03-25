package br.com.becb.api.users.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.becb.api.users.service.UserService;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private  Environment env; 
	
	@Autowired	
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public WebSecurity(Environment enviroment,UserService userService,
	
	BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.env = enviroment;
		this.userService = userService;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
		
	}
	
	protected void configure(HttpSecurity http) throws Exception{
		
		http.csrf().disable();
		
		//liberado para todos:
		//http.authorizeRequests().antMatchers("/users/**").permitAll();
		
		//pelo ip - lendo a propriedade do arquivo de propriedades 
		//configurando somente vindo do zuul (local)
		//http.authorizeRequests().antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"))
		
		http.authorizeRequests().antMatchers("/tokens").access(
	            "hasIpAddress('10.0.0.0/16') or hasIpAddress('127.0.0.1/32')")
		//or "hasIpAddress(env.getProperty('gateway.ip')")
		.and().addFilter(getAuthenticationFilter());
		
		http.headers().frameOptions().disable();
	
	}
	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = 
				new AuthenticationFilter(userService, env,authenticationManager());
		
		//alterando a url de login, default é /login e alteramos para /users/login
		authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
		return authenticationFilter;
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

}

package br.com.becb.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TreinamentoApiUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreinamentoApiUsersApplication.class, args);
	}

}

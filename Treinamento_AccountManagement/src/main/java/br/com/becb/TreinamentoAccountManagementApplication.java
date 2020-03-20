package br.com.becb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TreinamentoAccountManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreinamentoAccountManagementApplication.class, args);
	}

}

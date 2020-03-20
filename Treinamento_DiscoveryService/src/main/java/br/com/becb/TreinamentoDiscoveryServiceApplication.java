package br.com.becb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TreinamentoDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreinamentoDiscoveryServiceApplication.class, args);
	}

}

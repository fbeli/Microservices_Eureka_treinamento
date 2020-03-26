package br.com.becb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class TreinamentoConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreinamentoConfigServerApplication.class, args);
	}

}

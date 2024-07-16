package net.bounceme.chronos.clientesapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ClientesApiApplication implements CommandLineRunner {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ClientesApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String password = "MedinaAzahara2468@";
		
		String passwordBcrypt = passwordEncoder.encode(password);
		log.info("Password: {}", passwordBcrypt);
	}
}

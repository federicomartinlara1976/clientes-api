package net.bounceme.chronos.clientesapi.config.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import lombok.SneakyThrows;

@SuppressWarnings("deprecation")
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	@SneakyThrows
	public void configure(HttpSecurity http) {
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/clientes/", "/clientes/page/**", "/clientes/uploads/img/**", "/images/**", "/actuator/info").permitAll()
//			.antMatchers(HttpMethod.GET, "/clientes/{id}").hasAnyRole("USER", "ADMIN")
//			.antMatchers(HttpMethod.POST, "/clientes/uploads").hasAnyRole("USER", "ADMIN")
//			.antMatchers(HttpMethod.POST, "/clientes/").hasRole("ADMIN")
//			.antMatchers("/clientes/**").hasRole("ADMIN")
			.anyRequest().authenticated();
	}
}

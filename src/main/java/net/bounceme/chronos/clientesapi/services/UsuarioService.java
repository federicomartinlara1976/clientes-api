package net.bounceme.chronos.clientesapi.services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.clientesapi.models.dao.UsuarioDao;
import net.bounceme.chronos.clientesapi.models.entity.Usuario;

@Service
@Slf4j
public class UsuarioService implements UserDetailsService, IUsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	@Transactional(readOnly = true)
	@SneakyThrows(UsernameNotFoundException.class)
	public UserDetails loadUserByUsername(String username) {
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if (Objects.isNull(usuario)) {
			log.error("Error en el login: no existe el usuario {} en el sistema", username);
			throw new UsernameNotFoundException("Error en el login: no existe el usuario " + username + " en el sistema");
		}

		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> log.info("Role: {}", authority.getAuthority()))
				.collect(Collectors.toList());

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
				authorities);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsername(String username) {
		return usuarioDao.findByUsername(username);
	}

}

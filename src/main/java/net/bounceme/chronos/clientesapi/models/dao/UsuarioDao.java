package net.bounceme.chronos.clientesapi.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.bounceme.chronos.clientesapi.models.entity.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {

	Usuario findByUsername(String username);
	
	@Query("select u from Usuario u where u.username = ?1")
	Usuario findByUsername2(String username);
}

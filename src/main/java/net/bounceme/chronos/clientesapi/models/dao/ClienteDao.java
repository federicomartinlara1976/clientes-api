package net.bounceme.chronos.clientesapi.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.clientesapi.aspects.BeforeSave;
import net.bounceme.chronos.clientesapi.models.entity.Cliente;


/**
 * @author federico
 *
 */
@Repository
public interface ClienteDao extends JpaRepository<Cliente, Long> {
	
	@BeforeSave
	<S extends Cliente> S save(S entity);
	
	@BeforeSave
	<S extends Cliente> S saveAndFlush(S entity);
}

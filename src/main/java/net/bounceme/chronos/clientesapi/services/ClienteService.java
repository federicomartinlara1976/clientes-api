package net.bounceme.chronos.clientesapi.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.bounceme.chronos.clientesapi.models.entity.Cliente;
import net.bounceme.chronos.clientesapi.models.entity.Region;

public interface ClienteService {
	
	List<Cliente> findAll();
	
	Page<Cliente> findAll(Pageable pageable);
	
	Cliente save(Cliente cliente);
	
	void delete(Long id);
	
	Cliente findById(Long id);
	
	List<Region> findAllRegiones();

	Cliente update(Cliente clienteActual, Cliente cliente);
}

package net.bounceme.chronos.clientesapi.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bounceme.chronos.clientesapi.models.dao.ClienteDao;
import net.bounceme.chronos.clientesapi.models.dao.RegionDao;
import net.bounceme.chronos.clientesapi.models.entity.Cliente;
import net.bounceme.chronos.clientesapi.models.entity.Region;
import net.bounceme.chronos.clientesapi.services.ClienteService;

/**
 * @author federico
 *
 */
@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private RegionDao regionDao;

	/**
	 *
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllRegiones() {
		return regionDao.findAllRegiones();
	}

	@Override
	@Transactional
	public Cliente update(Cliente clienteActual, Cliente cliente) {
		
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setEmail(cliente.getEmail());
		clienteActual.setCreateAt(cliente.getCreateAt());
		clienteActual.setRegion(cliente.getRegion());
		
		return clienteDao.save(clienteActual);
	}
}

package net.bounceme.chronos.clientesapi.support.beforesavestrategy.impl;

import org.springframework.stereotype.Service;

import net.bounceme.chronos.clientesapi.models.entity.Cliente;
import net.bounceme.chronos.clientesapi.support.beforesavestrategy.ProcessEntityStrategy;

@Service
public class ProcessClienteStrategy implements ProcessEntityStrategy {

	@Override
	public Object processObject(Object o) {
		Cliente cliente = (Cliente) o;
		
		return cliente;
	}

}

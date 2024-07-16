package net.bounceme.chronos.clientesapi.services;

import net.bounceme.chronos.clientesapi.models.entity.Usuario;

public interface IUsuarioService {

	Usuario findByUsername(String username);
}

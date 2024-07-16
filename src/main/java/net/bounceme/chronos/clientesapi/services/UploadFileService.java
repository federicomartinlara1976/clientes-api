package net.bounceme.chronos.clientesapi.services;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {

	Resource cargar(String nombreFoto);
	
	String copiar(MultipartFile archivo);
	
	Boolean eliminar(String nombreFoto);
	
	Path getPath(String nombreFoto);
}

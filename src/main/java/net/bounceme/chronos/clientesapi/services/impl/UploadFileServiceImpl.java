package net.bounceme.chronos.clientesapi.services.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.clientesapi.services.UploadFileService;

@Service
@Slf4j
public class UploadFileServiceImpl implements UploadFileService {
	
	@Value("${application.data}")
	private String dataDir;

	@Override
	@SneakyThrows
	public Resource cargar(String nombreFoto) {
		Path rutaArchivo = getPath(nombreFoto);
		Resource resource = new UrlResource(rutaArchivo.toUri());
		
		if (!resource.exists() && !resource.isReadable()) {
			rutaArchivo = getPath("not_user_icon.png");
			resource = new UrlResource(rutaArchivo.toUri());
			log.error("No se pudo cargar la imagen: {}", nombreFoto);
		}
		
		return resource;
	}

	@Override
	@SneakyThrows
	public String copiar(MultipartFile archivo) {
		String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
		Path rutaArchivo = getPath(nombreArchivo);
		
		Files.copy(archivo.getInputStream(), rutaArchivo);
		
		return nombreArchivo;
	}

	@Override
	public Boolean eliminar(String nombreFoto) {
		if (StringUtils.isNotBlank(nombreFoto)) {
			Path rutaFotoAnterior = getPath(nombreFoto);
			File archivoFotoAnterior = rutaFotoAnterior.toFile();
			if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
				archivoFotoAnterior.delete();
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	@Override
	public Path getPath(String nombreFoto) {
		return Paths.get(dataDir).resolve(nombreFoto).toAbsolutePath();
	}

}

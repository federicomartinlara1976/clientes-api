package net.bounceme.chronos.clientesapi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.clientesapi.models.entity.Cliente;
import net.bounceme.chronos.clientesapi.models.entity.Region;
import net.bounceme.chronos.clientesapi.services.ClienteService;
import net.bounceme.chronos.clientesapi.services.UploadFileService;

/**
 * @author federico
 *
 */
@RestController
@RequestMapping("/clientes")
@Slf4j
public class ClienteRestController {
	
	

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private UploadFileService uploadFileService;

	@GetMapping("/")
	public List<Cliente> index() {
		return clienteService.findAll();
	}
	
	@GetMapping("/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page) {
		return clienteService.findAll(PageRequest.of(page, 4));
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		Cliente cliente = clienteService.findById(id);

		if (Objects.isNull(cliente)) {
			response.put("mensaje", String.format("El cliente con ID %d no existe", id));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/")
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente) {
		Map<String, Object> response = new HashMap<>();

		cliente = clienteService.save(cliente);

		log.info("Creado: {}", cliente.toString());
		response.put("mensaje", "El cliente ha sido creado con éxito");
		response.put("cliente", cliente);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, @PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		Cliente clienteActual = clienteService.findById(id);
		if (Objects.isNull(clienteActual)) {
			response.put("mensaje", String.format("El cliente con ID %d no existe", id));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		clienteActual = clienteService.update(clienteActual, cliente);

		response.put("mensaje", "El cliente ha sido actualizado con éxito");
		response.put("cliente", clienteActual);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		Cliente cliente = clienteService.findById(id);
		
		String nombreFotoAnterior = cliente.getFoto();
		uploadFileService.eliminar(nombreFotoAnterior);

		clienteService.delete(id);

		response.put("mensaje", "El cliente ha sido borrado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping("/upload")
	@SneakyThrows
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		
		Cliente cliente = clienteService.findById(id);
		
		if (!archivo.isEmpty()) {
			String nombreArchivo = uploadFileService.copiar(archivo);
			
			String nombreFotoAnterior = cliente.getFoto();
			uploadFileService.eliminar(nombreFotoAnterior);
			
			cliente.setFoto(nombreArchivo);
			clienteService.save(cliente);
			
			log.info("Se ha subido correctamente la imagen {}", nombreArchivo);
			response.put("cliente", cliente);
			response.put("mensaje", "Se ha subido correctamente la imagen " + nombreArchivo);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/uploads/img/{nombreFoto:.+}")
	@SneakyThrows
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
		Resource resource = uploadFileService.cargar(nombreFoto);
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
		return new ResponseEntity<Resource>(resource, cabecera, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/regiones")
	public List<Region> listarRegiones() {
		return clienteService.findAllRegiones();
	}
}

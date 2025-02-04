package net.bounceme.chronos.clientesapi.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="regiones")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Region implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5771931947022897126L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;

}

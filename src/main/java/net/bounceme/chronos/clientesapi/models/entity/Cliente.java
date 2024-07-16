package net.bounceme.chronos.clientesapi.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author federico
 *
 */
@Entity
@Table(name="clientes")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3121305617405095497L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "no puede estar vacío")
	@Size(min=4, max=12, message = "El tamaño tiene que estar entre 4 y 12")
	@Column(nullable = false)
	@EqualsAndHashCode.Exclude
	private String nombre;
	
	@NotEmpty(message = "no puede estar vacío")
	@Column
	@EqualsAndHashCode.Exclude
	private String apellido;
	
	@NotEmpty(message = "no puede estar vacío")
	@Email(message = "no es una dirección válida")
	@Column(nullable = false, unique = true)
	@EqualsAndHashCode.Exclude
	private String email;
	
	@Column(name="created_at")
	@Temporal(TemporalType.DATE)
	@EqualsAndHashCode.Exclude
	private Date createAt;
	
	@Column
	@EqualsAndHashCode.Exclude
	private String foto;
	
	@NotNull(message = "La región no puede ser vacía")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Region region;
}

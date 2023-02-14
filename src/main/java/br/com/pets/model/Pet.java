package br.com.pets.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="pet")
public class Pet {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message="obrigatório")
	@Size(min=0, max=100, message="máximo 100 caracteres")
	@Column(nullable= false, length = 100)
	private String nome;
	
	@ManyToOne
	@NotNull(message="obrigatório")
	@JoinColumn(nullable= false, name = "especie_id")
	private Especie especie;
	
	@NotBlank(message="obrigatório")
	@Size(min=0, max=100, message="máximo 100 caracteres")
	@Column(nullable= false, length = 100)
	private String raca;
	
	@NotNull(message="obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(nullable= false, length = 1)
	private Sexo sexo;
	
	@DecimalMin(value="0.0", message="valor mínimo 0.0")
	@Digits(integer=3, fraction=1, message="inválido (exemplo válido: 999.9)")
	@Column
	private BigDecimal peso;
	
	@NotBlank(message="obrigatório")
	@Size(min=0, max=100, message="máximo 100 caracteres")
	@Column(nullable= false, length = 100)
	private String dono;
	
	private String telDono;
	
	@DateTimeFormat(iso=ISO.DATE)
	@Column(name = "ultima_vacina")
	private LocalDate ultimaVacina;
	
	@DateTimeFormat(iso=ISO.DATE)
	@Column(name = "ultimo_anti_pulga")
	private LocalDate ultimoAntiPulga;
}

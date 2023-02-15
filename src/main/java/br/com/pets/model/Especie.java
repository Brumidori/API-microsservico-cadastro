package br.com.pets.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@Entity(name="especie")
public class Especie {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message="Nome é obrigatório")
	@Size(min=0, max=100, message="máximo 100 caracteres")
	@Column(unique= false, nullable= false, length = 100)
	private String nome;
	
	@NotNull(message="Cor é obrigatória")
	@Enumerated(EnumType.STRING)
	@Column(nullable= false, length = 20)
	private Cor cor;

}

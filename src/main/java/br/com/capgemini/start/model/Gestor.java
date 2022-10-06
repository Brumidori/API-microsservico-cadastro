package br.com.capgemini.start.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name="gestor")
public class Gestor extends Usuario {
	private static final long serialVersionUID = 1L;
	
	@Column(unique=true, nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private CorProjeto cor;
	
	@Column(nullable= false, name = "data_cadastro")
	private LocalDate dataCadastro;
}

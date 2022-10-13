package br.com.capgemini.start.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name="coach")
public class Coach extends Usuario {
	private static final long serialVersionUID = 1L;
	
	@Column(nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private Atuacao atuacao;
	
	@Column(nullable= false, name = "data_cadastro")
	private LocalDate dataCadastro;
	
	@JoinColumn(name = "id_gestor")
	@ManyToOne
	private Gestor gestor;
	
	@JoinColumn(name = "id_squad")
	@ManyToOne
	private Squad squad;
}

package br.com.capgemini.start.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name="m_start")
public class Start extends Usuario {
	private static final long serialVersionUID = 1L;
	
	@Column(nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private Atuacao atuacao;
	
	@Column(nullable= false, name = "data_cadastro")
	private LocalDate dataCadastro;
	
	@Column(nullable= false)
	private Boolean billable;
	
	@Column(name = "data_billable")
	private LocalDate dataBillable;
	
	@JoinColumn(name = "entrevista_negocio")
	@OneToOne
	private EntrevistaNegocio entrevistaNegocio;
	
	@JoinColumn(name = "entrevista_tecnica")
	@OneToOne
	private EntrevistaTecnica entrevistaTecnica;
	
	@JoinColumn(name = "id_coach")
	@ManyToOne
	private Coach coach;
	
	@JoinColumn(name = "id_gestor")
	@ManyToOne
	private Gestor gestor;
	
	@JoinColumn(nullable= false, name = "id_turma")
	@ManyToOne
	private Turma turma;
}

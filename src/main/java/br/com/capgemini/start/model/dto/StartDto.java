package br.com.capgemini.start.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

import br.com.capgemini.start.model.Atuacao;
import br.com.capgemini.start.model.CorProjeto;
import br.com.capgemini.start.model.Farol;
import br.com.capgemini.start.model.Permissao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StartDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String email;
	private String nome;
	private Permissao permissao;
	private Atuacao atuacao;
	private CorProjeto cor;
	private LocalDate dataCadastro;
	private Boolean billable;
	private LocalDate dataBillable;
	
	private Farol farolEntrevistaNegocio;
	private String parecerEntrevistaNegocio;
	private boolean fezEntrevistaNegocio;
	
	private Farol farolEntrevistaTecnica;
	private String parecerEntrevistaTecnica;
	private boolean fezEntrevistaTecnica;

	private Farol farolUltimaAvaliacao;
	private String parecerUltimaAvaliacao;
	private LocalDate dataUltimaAvaliacao;
	private boolean fezAvaliacao;
	private boolean fezAvaliacaoNoDia;
	
	private CoachDto coach;
	private GestorDto gestor;
	private TurmaDto turma;
}

package br.com.capgemini.start.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import br.com.capgemini.start.model.Atuacao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StartRelatorioDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String email;
	private String nome;
	
	private Atuacao atuacao;
	private LocalDate dataCadastro;
	
	private Boolean billable;
	private LocalDate dataBillable;
	
	private EntrevistaNegocioDto entrevistaNegocio;
	private EntrevistaTecnicaDto entrevistaTecnica;
	private List<AvaliacaoDto> avaliacoes;
	
	private CoachDto coach;
	private GestorDto gestor;
	private TurmaDto turma;
}

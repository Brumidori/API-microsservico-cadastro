package br.com.capgemini.start.model.form;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.capgemini.start.model.Farol;
import br.com.capgemini.start.validation.Validacao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EntrevistaTecnicaForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = Validacao.NOT_NULL)
	@Size(max=200, message = Validacao.SIZE_MAX_200)
	private String nomeStart;
	
	@NotBlank(message = Validacao.NOT_NULL)
	@Size(max=200, message = Validacao.SIZE_MAX_200)
	private String nomeCoachEntrevista;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Long idStart;
	
	@Column(nullable= false, length = 500)
	private String parecer;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Farol p01;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Farol p02;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Farol p03;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Farol p04;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Farol p05;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Farol p06;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Farol p07;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Farol p08;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Farol p09;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Farol p10;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Long idCoachEntrevista;
	
	private Long idAgendamento;
}

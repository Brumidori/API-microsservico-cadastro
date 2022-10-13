package br.com.capgemini.start.model.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.capgemini.start.validation.Validacao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SquadForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotBlank(message = Validacao.NOT_NULL)
	@Size(max=200, message = Validacao.SIZE_MAX_200)
	private String nome;
	
	@NotBlank(message = Validacao.NOT_NULL)
	@Size(max=4, message = Validacao.SIZE_MAX_4)
	private String nomeAbreviado;
	
	@NotBlank(message = Validacao.NOT_NULL)
	@Size(max=200, message = Validacao.SIZE_MAX_200)
	private String funcionalidade;
	
	@Size(max=500, message = Validacao.SIZE_MAX_500)
	private String descricao;
}

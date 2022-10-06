package br.com.capgemini.start.model.form;

import java.io.Serializable;

import javax.validation.constraints.Size;

import br.com.capgemini.start.model.Atuacao;
import br.com.capgemini.start.validation.Validacao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ListaStartForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Size(max=200, message = Validacao.SIZE_MAX_200)
	private String nome;
	
	@Size(max=200, message = Validacao.SIZE_MAX_200)
	private String emailUsuario;
	
	private Atuacao atuacao;
	
	@Size(max=200, message = Validacao.SIZE_MAX_200)
	private String nomeCoach;
	
	@Size(max=200, message = Validacao.SIZE_MAX_200)
	private String nomeGestor;
	
	@Size(max=200, message = Validacao.SIZE_MAX_200)
	private String nomeTurma;
}

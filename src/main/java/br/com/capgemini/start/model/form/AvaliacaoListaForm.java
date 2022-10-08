package br.com.capgemini.start.model.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.capgemini.start.model.Atuacao;
import br.com.capgemini.start.model.Farol;
import br.com.capgemini.start.validation.Validacao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AvaliacaoListaForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private String emailUsuario;
	private Atuacao atuacao;
	private String nomeCoach;
	private String nomeGestor;
	private String nomeTurma;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Farol farol;
	
	@NotBlank(message = Validacao.NOT_NULL)
	@Size(max=500, message = Validacao.SIZE_MAX_500)
	private String parecer;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Long idStart;

	public ListaStartForm newListaStartForm() {
		ListaStartForm form = new ListaStartForm();
		form.setNome(nome);
		form.setEmailUsuario(emailUsuario);
		form.setAtuacao(atuacao);
		form.setNomeCoach(nomeCoach);
		form.setNomeGestor(nomeGestor);
		form.setNomeTurma(nomeTurma);		
		
		return form;
	}
}

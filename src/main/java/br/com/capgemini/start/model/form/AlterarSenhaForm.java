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
public class AlterarSenhaForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = Validacao.NOT_NULL)
	@Size(max=200, message = Validacao.SIZE_MAX_200)
	private String senha;
	
	@NotBlank(message = Validacao.NOT_NULL)
	@Size(max=200, message = Validacao.SIZE_MAX_200)
	private String senhaConfirma;
}

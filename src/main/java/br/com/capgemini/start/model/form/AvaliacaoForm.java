package br.com.capgemini.start.model.form;

import java.io.Serializable;
import java.time.LocalDate;

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
public class AvaliacaoForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = Validacao.NOT_NULL)
	private Long id;
	
	@NotNull(message = Validacao.NOT_NULL)
	private LocalDate data;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Farol farol;
	
	@NotBlank(message = Validacao.NOT_NULL)
	@Size(max=500, message = Validacao.SIZE_MAX_500)
	private String parecer;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Long idStart;
}

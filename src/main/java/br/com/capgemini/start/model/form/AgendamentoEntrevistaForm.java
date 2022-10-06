package br.com.capgemini.start.model.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import br.com.capgemini.start.validation.Validacao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AgendamentoEntrevistaForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = Validacao.NOT_NULL)
	private Long idStart;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Long idCoachEntrevista;
	
	private Long idAgendamento;
}

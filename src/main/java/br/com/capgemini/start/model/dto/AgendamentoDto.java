package br.com.capgemini.start.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.capgemini.start.util.Utilits;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AgendamentoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String link;
	private String linkExcluir;
	private LocalDateTime dataHora;
	private StartDto start;
	private CoachDto coach;
	
	public String mensagemDataHora() {
		return Utilits.mensagemDataHora(dataHora);
	}
}

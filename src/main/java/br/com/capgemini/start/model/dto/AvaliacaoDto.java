package br.com.capgemini.start.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

import br.com.capgemini.start.model.Farol;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AvaliacaoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private LocalDate data;
	private Farol farol;
	private String parecer;
	
	private Long idStart;
	private String nomeStart;
}

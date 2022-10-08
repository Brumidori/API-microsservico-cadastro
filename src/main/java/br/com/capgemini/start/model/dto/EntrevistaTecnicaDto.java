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
public class EntrevistaTecnicaDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private LocalDate data;
	private String parecer;
	private Farol p01;
	private Farol p02;
	private Farol p03;
	private Farol p04;
	private Farol p05;
	private Farol p06;
	private Farol p07;
	private Farol p08;
	private Farol p09;
	private Farol p10;
	private CoachDto coachEntrevista;
}

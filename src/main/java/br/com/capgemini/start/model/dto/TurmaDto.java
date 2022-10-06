package br.com.capgemini.start.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TurmaDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
}

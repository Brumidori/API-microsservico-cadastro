package br.com.capgemini.start.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Atuacao {
	ADMINISTRATIVO("Administrativo"),
	BACK_END("Back-end"),
	FRONT_END("Front-end"),
	FULL_STACK("Full-stack"),
	MOBILE("Mobile"),
	QA("QA");
	
	private final String imprime;
}

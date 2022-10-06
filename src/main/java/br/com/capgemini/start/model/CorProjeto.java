package br.com.capgemini.start.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CorProjeto {

	PRETO("Preto", "#FFFFFF", "#000000"),
	
	ROSA("Rosa", "#F7CBAC", "#000000"),
	AZUL_PASTEL("Azul Pastel", "#B4C6E7", "#000000"),
	VERDE_PASTEL("Verde Pastel", "#C5E0B3", "#000000"),
	PEROLA("Pérola", "#FEE599", "#000000"),
	CIANO("Ciano", "#0099FF", "#000000"),
	PINK("Pink", "#FF66FF", "#000000"),

	VERDE_ESCURO("Verde Escuro", "#375623", "#FFFFFF"),
	MARROM("Marrom", "#7F6000", "#FFFFFF"),
	CINZA("Cinza", "#7F7F7F", "#FFFFFF"),
	ROXO("Roxo", "#7030A0", "#FFFFFF");
	
	private final String imprime;
	private final String corFundo;
	private final String corFonte;
	
	public static CorProjeto[] valores() {
		return new CorProjeto[] {
				ROSA,
				AZUL_PASTEL,
				VERDE_PASTEL,
				PEROLA,
				CIANO,
				PINK,

				VERDE_ESCURO,
				MARROM,
				CINZA,
				ROXO
				};
	}
}

package br.com.pets.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Cor {
	AZUL_CLARO ("#ADD8E6"),
	AZUL_ACO_CLARO ("#B0C4DE"),
	AGUA_MARINHA ("#7FFFD4"),
	VERDE_CLARO ("#90EE90"),
	CAQUI_ESCURO ("#BDB76B"),
	NAVAJO_BRANCO ("#FFDEAD"),
	TRIGO ("#F5DEB3"),
	VIOLETA ("#EE82EE"),
	AMEIXA ("#DDA0DD"),
	ROSA ("#FFC0CB"),
	SALMAO ("#FA8072"),
	LARANJA ("#FFA500"),
	CAQUI ("#F0E68C"),
	CARDO ("#D8BFD8"),
	TURQUESA_PALIDO ("#E0FFFF");
	
	private String hexa;
}

package br.com.capgemini.start.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Farol {
	BRANCO  ("não avaliado", "#FFFFFF", "#000000", 0),
	
	VERMELHO("abaixo do esperado", "#FF0000", "#000000", 1),
	AMARELO ("dentro do esperado", "#FFFF00", "#000000", 2),
	VERDE   ("excede o esperado", "#00B050", "#000000", 3),
	AZUL    ("excede muito o esperado", "#0070C0", "#000000", 4);
	
	private final String mensagem;
	private final String corFundo;
	private final String corFonte;
	private final int valor;
	
	public static Farol[] valores() {
		return new Farol[] {
				VERMELHO,
				AMARELO,
				VERDE,
				AZUL
				};
	}
	
	public static int total(Farol ... farol) {
		int valor = 0;
		for (Farol f : farol) {
			if(f != null) {
				valor += f.valor;
			}
		}
		
		return valor;
	}
	
	public static Farol media(Farol ... farol) {
		double valor = 0;
		int n = 0;
		for (Farol f : farol) {
			if(f != null) {
				valor += f.valor;
				n++;
			}
		}
		
		if(n == 0) {
			return BRANCO;
		}
		
		valor /= n;
		int media = (int) Math.round(valor);
		
		switch (media) {
			case 0: return BRANCO;
			case 1: return VERMELHO;
			case 2: return AMARELO;
			case 3: return VERDE;
			case 4: return AZUL;

			default: return null;
		}
	}
}

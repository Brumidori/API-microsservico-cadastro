package br.com.capgemini.start.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FarolTest {

	@Test
	void cobertura() {
		Farol n = null;
		
		Assertions.assertEquals(Farol.VERMELHO, Farol.media(n));
		Assertions.assertEquals(Farol.VERMELHO, Farol.media(Farol.VERMELHO));
		Assertions.assertEquals(Farol.AMARELO, Farol.media(Farol.VERMELHO, Farol.VERMELHO, Farol.VERDE));
		Assertions.assertEquals(Farol.AMARELO, Farol.media(Farol.VERMELHO, Farol.VERDE));
		Assertions.assertEquals(Farol.AMARELO, Farol.media(Farol.VERMELHO, Farol.VERDE, Farol.VERDE));
		Assertions.assertEquals(Farol.VERDE, Farol.media(Farol.VERMELHO, Farol.VERDE, Farol.VERDE, Farol.VERDE));
		Assertions.assertEquals(Farol.VERDE, Farol.media(Farol.VERMELHO, Farol.VERDE, Farol.VERDE, Farol.VERDE, null));
	}
}

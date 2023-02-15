package br.com.pets.exception;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PetException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final String erro;

	public PetException(String ... erro) {
		this.erro = Arrays.asList(erro).stream().map(e -> e).collect(Collectors.joining(", "));
	}
}

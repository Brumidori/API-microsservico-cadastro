package br.com.capgemini.start.exception;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public class ErroInternoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final List<String> erros;

	public ErroInternoException(String ... erro) {
		this.erros = Arrays.asList(erro);
	}
}

package br.com.capgemini.start.exception;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ViewException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final String erro;

	public ViewException(String ... erro) {
		this.erro = Arrays.asList(erro).stream().map(e -> e).collect(Collectors.joining(", "));
	}
}

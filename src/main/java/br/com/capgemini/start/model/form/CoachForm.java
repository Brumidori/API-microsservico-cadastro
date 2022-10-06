package br.com.capgemini.start.model.form;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.capgemini.start.model.Atuacao;
import br.com.capgemini.start.validation.Validacao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CoachForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotBlank(message = Validacao.NOT_NULL)
	@Size(max=200, message = Validacao.SIZE_MAX_200)
	private String email;
	
	@NotBlank(message = Validacao.NOT_NULL)
	@Size(max=200, message = Validacao.SIZE_MAX_200)
	private String nome;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Boolean adm;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Atuacao atuacao;
	
	@NotNull(message = Validacao.NOT_NULL)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataCadastro;
	
	@NotNull(message = Validacao.NOT_NULL)
	private Long idGestor;
}

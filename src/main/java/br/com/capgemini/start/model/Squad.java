package br.com.capgemini.start.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name="squad")
public class Squad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true, nullable= false, length = 200)
	private String nome;
	
	@Column(unique=true, nullable= false, length = 4, name="nome_abreviado")
	private String nomeAbreviado;
	
	@Column(nullable= false, length = 200)
	private String funcionalidade;
	
	@Column(length = 500)
	private String descricao;
}

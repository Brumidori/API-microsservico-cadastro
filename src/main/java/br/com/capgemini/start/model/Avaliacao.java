package br.com.capgemini.start.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity(name="avaliacao")
public class Avaliacao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable= false)
	private LocalDate data;
	
	@Column(nullable= false, length = 10)
	@Enumerated(EnumType.STRING)
	private Farol farol;
	
	@Column(nullable= false, length = 500)
	private String parecer;
	
	@Column(nullable= false, name = "id_start")
	private Long idStart;
}

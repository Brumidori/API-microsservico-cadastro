package br.com.capgemini.start.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name="entrevista_negocio")
public class EntrevistaNegocio implements Serializable {
	private static final long serialVersionUID = 1L;

	// por simplicidade mesmo is do start
	@Id
	private Long id;
	
	@Column(nullable= false)
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate data;
	
	@Column(nullable= false, length = 500)
	private String parecer;
	
	@Column(nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private Farol p01;
	
	@Column(nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private Farol p02;
	
	@Column(nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private Farol p03;
	
	@Column(nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private Farol p04;
	
	@Column(nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private Farol p05;
	
	@Column(nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private Farol p06;
	
	@Column(nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private Farol p07;
	
	@Column(nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private Farol p08;
	
	@Column(nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private Farol p09;
	
	@Column(nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private Farol p10;
	
	@Column(nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private Farol p11;
	
	@Column(nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private Farol p12;
	
	@JoinColumn(nullable= false,name = "id_coach_entrevista")
	@ManyToOne
	private Coach coachEntrevista;
	
	public Farol media() {
		return Farol.media(p01, p02, p03, p04, p05, p06, p07, p08, p09, p10, p11, p12);
	}
}

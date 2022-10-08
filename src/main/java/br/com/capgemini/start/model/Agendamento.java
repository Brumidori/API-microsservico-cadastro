package br.com.capgemini.start.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name="agendamento")
public class Agendamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable= false, length = 200)
	private String nome;
	
	@Column(nullable= false, length = 200)
	private String link;
	
	@Column(nullable= false, length = 200, name = "link_excluir")
	private String linkExcluir;
	
	@Column(nullable= false, name = "data_hora")
	private LocalDateTime dataHora;
	
	@JoinColumn(nullable= false, name = "id_coach")
	@ManyToOne
	private Coach coach;
	
	@JoinColumn(nullable= false, name = "id_start")
	@ManyToOne
	private Start start;
}

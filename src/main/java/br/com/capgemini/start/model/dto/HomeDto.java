package br.com.capgemini.start.model.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HomeDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<StartDto> starts;
	private List<StartDto> startsDosCoachs;
	private List<CoachDto> coachs;
	
	private List<AgendamentoDto> agendamentos;
	private List<AgendamentoDto> agendamentosDosCoachs;
	private List<AgendamentoDto> agendamentosDeTodos;
}

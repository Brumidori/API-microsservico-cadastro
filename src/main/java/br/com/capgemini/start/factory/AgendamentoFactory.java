package br.com.capgemini.start.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.capgemini.start.model.Agendamento;
import br.com.capgemini.start.model.dto.AgendamentoDto;
import br.com.capgemini.start.model.dto.CoachDto;
import br.com.capgemini.start.model.dto.StartDto;

@Component
public class AgendamentoFactory {

	@Autowired
	private ModelMapper mapper;
	
	public List<AgendamentoDto> listDto(List<Agendamento> agendamentos) {
		return agendamentos.stream().map(this::dto).collect(Collectors.toList());
	}
	
	public AgendamentoDto dto(Agendamento agendamento) {
		AgendamentoDto dto =  mapper.map(agendamento, AgendamentoDto.class);
		
		dto.setStart(mapper.map(agendamento.getStart(), StartDto.class));
		CoachDto coach = mapper.map(agendamento.getCoach(), CoachDto.class);
		coach.setCor(agendamento.getCoach().getGestor().getCor());
		dto.setCoach(coach);
		
		return dto;
	}
}

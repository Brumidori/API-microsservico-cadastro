package br.com.capgemini.start.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.capgemini.start.model.Coach;
import br.com.capgemini.start.model.dto.CoachDto;
import br.com.capgemini.start.model.dto.GestorDto;
import br.com.capgemini.start.model.dto.SquadDto;

@Component
public class CoachFactory {
	
	@Autowired
	private ModelMapper mapper;
	
	public List<CoachDto> listDto(List<Coach> coachs) {
		return coachs.stream().map(this::dto).collect(Collectors.toList());
	}
	
	public CoachDto dto(Coach coach) {
		CoachDto dto =  mapper.map(coach, CoachDto.class);
		
		dto.setGestor(mapper.map(coach.getGestor(), GestorDto.class));
		dto.setCor(coach.getGestor().getCor());
		if(coach.getSquad() != null) {
			dto.setSquad(mapper.map(coach.getSquad(), SquadDto.class));
		}
		
		return dto;
	}
}

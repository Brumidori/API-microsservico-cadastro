package br.com.capgemini.start.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.model.Squad;
import br.com.capgemini.start.model.dto.SquadDto;

@Service
public class SquadFactory {

	@Autowired
	private ModelMapper mapper;
	
	public List<SquadDto> listDto(List<Squad> squads) {
		return squads.stream().map(this::dto).collect(Collectors.toList());
	}
	
	public SquadDto dto(Squad squad) {
		return mapper.map(squad, SquadDto.class);
	}
}

package br.com.capgemini.start.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.capgemini.start.model.Gestor;
import br.com.capgemini.start.model.dto.GestorDto;

@Component
public class GestorFactory {

	@Autowired
	private ModelMapper mapper;
	
	public List<GestorDto> listDto(List<Gestor> gestores) {
		return gestores.stream().map(this::dto).collect(Collectors.toList());
	}
	
	public GestorDto dto(Gestor gestor) {
		return  mapper.map(gestor, GestorDto.class);
	}
}

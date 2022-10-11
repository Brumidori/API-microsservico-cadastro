package br.com.capgemini.start.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.model.Turma;
import br.com.capgemini.start.model.dto.TurmaDto;

@Service
public class TurmaFactory {

	@Autowired
	private ModelMapper mapper;
	
	public List<TurmaDto> listDto(List<Turma> turmas) {
		return turmas.stream().map(this::dto).collect(Collectors.toList());
	}
	
	public TurmaDto dto(Turma turma) {
		return mapper.map(turma, TurmaDto.class);
	}
}

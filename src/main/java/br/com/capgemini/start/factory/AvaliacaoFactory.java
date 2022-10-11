package br.com.capgemini.start.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.capgemini.start.model.Avaliacao;
import br.com.capgemini.start.model.dto.AvaliacaoDto;

@Component
public class AvaliacaoFactory {

	@Autowired
	private ModelMapper mapper;
	
	public List<AvaliacaoDto> listDto(List<Avaliacao> avaliacoes) {
		return avaliacoes.stream().map(this::dto).collect(Collectors.toList());
	}
	
	public AvaliacaoDto dto(Avaliacao avaliacao) {
		return mapper.map(avaliacao, AvaliacaoDto.class);
	}
}

package br.com.capgemini.start.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.factory.SquadFactory;
import br.com.capgemini.start.model.Squad;
import br.com.capgemini.start.model.dto.SquadDto;
import br.com.capgemini.start.model.form.SquadForm;
import br.com.capgemini.start.repository.SquadRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SquadService {

	@Autowired
	private SquadRepository repository;
	
	@Autowired
	private SquadFactory factory;

	@Autowired
	private ModelMapper mapper;
	
	public SquadForm form(Long id) {
		Squad squad = repository.findById(id).orElseThrow(()-> new ErroInternoException("Squad não encontrada na geração do Squad Form"));
		
		return mapper.map(squad, SquadForm.class);
	}
	
	public void salvar(SquadForm form) {
		Squad squad = mapper.map(form, Squad.class);
		
		log.info("salvar entity={}", squad);
		
		repository.save(squad);
	}
	
	public void excluir(Long id) {
		repository.deleteById(id);
	}
	
	public List<SquadDto> listar() {
		return factory.listDto(repository.findAll(Sort.by(Sort.Direction.ASC, "nome")));
	}
}

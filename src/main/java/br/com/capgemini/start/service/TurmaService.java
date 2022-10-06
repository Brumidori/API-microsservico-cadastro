package br.com.capgemini.start.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.model.Turma;
import br.com.capgemini.start.model.dto.TurmaDto;
import br.com.capgemini.start.model.form.TurmaForm;
import br.com.capgemini.start.repository.TurmaRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TurmaService {

	@Autowired
	private TurmaRepository repository;

	@Autowired
	private ModelMapper mapper;
	
	public TurmaForm form(Long id) {
		Turma turma = repository.findById(id).orElseThrow(()-> new ErroInternoException("Turma não encontrada na geração do Turma Form"));
		
		return mapper.map(turma, TurmaForm.class);
	}
	
	public void salvar(TurmaForm form) {
		Turma turma = mapper.map(form, Turma.class);
		
		log.info("salvar entity={}", turma);
		
		repository.save(turma);
	}
	
	public void excluir(Long id) {
		repository.deleteById(id);
	}
	
	public List<TurmaDto> listar() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "nome")).stream().map(this::turmaDto).collect(Collectors.toList());
	}
	
	private TurmaDto turmaDto(Turma turma) {
		return mapper.map(turma, TurmaDto.class);
	}
}

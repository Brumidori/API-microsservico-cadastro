package br.com.capgemini.start.service;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.factory.AvaliacaoFactory;
import br.com.capgemini.start.model.Avaliacao;
import br.com.capgemini.start.model.Start;
import br.com.capgemini.start.model.dto.AvaliacaoDto;
import br.com.capgemini.start.model.form.AvaliacaoForm;
import br.com.capgemini.start.model.form.AvaliacaoListaForm;
import br.com.capgemini.start.repository.AvaliacaoRepository;
import br.com.capgemini.start.repository.StartRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AvaliacaoService {

	@Autowired
	private AvaliacaoRepository repository;
	
	@Autowired
	private AvaliacaoFactory factory;
	
	@Autowired
	private StartRepository startRepository;

	@Autowired
	private ModelMapper mapper;
	
	public AvaliacaoForm form(Long id) {
		Avaliacao avaliacao = repository.findById(id).orElseThrow(()-> new ErroInternoException("Avaliação não encontrada na geração do Avaliação Form"));
		
		return mapper.map(avaliacao, AvaliacaoForm.class);
	}
	
	public void salvar(AvaliacaoForm form) {
		Avaliacao avaliacao = mapper.map(form, Avaliacao.class);
		
		log.info("salvar entity={}", avaliacao);
		
		avaliacao = repository.save(avaliacao);
		
		Start start = startRepository.findById(form.getIdStart()).orElseThrow(()-> new ErroInternoException("Start não encontrado ao salvar uma Avaliação"));
		start.setUltimaAvaliacao(avaliacao);
		
		startRepository.save(start);
	}
	
	public void salvar(AvaliacaoListaForm form) {
		Avaliacao avaliacao = mapper.map(form, Avaliacao.class);
		avaliacao.setData(LocalDate.now());
		
		log.info("salvar entity={}", avaliacao);
		
		avaliacao = repository.save(avaliacao);
		
		Start start = startRepository.findById(form.getIdStart()).orElseThrow(()-> new ErroInternoException("Start não encontrado ao salvar uma Avaliação"));
		start.setUltimaAvaliacao(avaliacao);
		
		startRepository.save(start);
	}
	
	public List<AvaliacaoDto> listar() {
		return factory.listDto(repository.findAll(Sort.by(Sort.Direction.ASC, "idStart")));
	}
}

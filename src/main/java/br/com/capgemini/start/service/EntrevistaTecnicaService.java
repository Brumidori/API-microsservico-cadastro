package br.com.capgemini.start.service;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.model.EntrevistaTecnica;
import br.com.capgemini.start.model.Start;
import br.com.capgemini.start.model.form.AgendamentoEntrevistaForm;
import br.com.capgemini.start.model.form.EntrevistaTecnicaForm;
import br.com.capgemini.start.repository.AgendamentoRepository;
import br.com.capgemini.start.repository.CoachRepository;
import br.com.capgemini.start.repository.EntrevistaTecnicaRepository;
import br.com.capgemini.start.repository.StartRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class EntrevistaTecnicaService {

	@Autowired
	private EntrevistaTecnicaRepository repository;
	
	@Autowired
	private AgendamentoRepository agendamentoRepository;

	@Autowired
	private StartRepository startRepository;
	
	@Autowired
	private CoachRepository coachRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public EntrevistaTecnicaForm form(Long idStart) {
		Start start = startRepository.findById(idStart).orElseThrow(()-> new ErroInternoException("Start não encontrado na geração do Entrevista Tecnica Form"));
		EntrevistaTecnica entrevista = start.getEntrevistaTecnica();
		
		EntrevistaTecnicaForm form =  mapper.map(entrevista, EntrevistaTecnicaForm.class);
		form.setNomeStart(start.getNome());
		form.setIdStart(start.getId());
		form.setNomeCoachEntrevista(entrevista.getCoachEntrevista().getNome());
		form.setIdCoachEntrevista(entrevista.getCoachEntrevista().getId());
		
		return form;
	}

	public EntrevistaTecnicaForm entrevistaForm(AgendamentoEntrevistaForm aacForm) {
		EntrevistaTecnicaForm form = new EntrevistaTecnicaForm();
		form.setNomeStart(startRepository.findById(aacForm.getIdStart()).orElseThrow(()-> new ErroInternoException("Start não encontrado na geração do Entrevista Tecnica Form")).getNome());
		form.setNomeCoachEntrevista(coachRepository.findById(aacForm.getIdCoachEntrevista()).orElseThrow(()-> new ErroInternoException("Coach não encontrado na geração do Entrevista Tecnica Form")).getNome());
		form.setIdStart(aacForm.getIdStart());
		form.setIdCoachEntrevista(aacForm.getIdCoachEntrevista());
		form.setIdAgendamento(aacForm.getIdAgendamento());
		
		return form;
	}
	
	public void salvar(EntrevistaTecnicaForm form) {
		EntrevistaTecnica entrevista = mapper.map(form, EntrevistaTecnica.class);
		entrevista.setId(form.getIdStart());
		
		entrevista.setCoachEntrevista(coachRepository.findById(form.getIdCoachEntrevista()).orElseThrow(()-> new ErroInternoException("Coach da Entrevista não encontrado ao salvar uma Entrevista Tecnica")));
		entrevista.setData(LocalDate.now());
		
		log.info("salvar entity={}", entrevista);
		
		entrevista = repository.save(entrevista);
		
		Start start = startRepository.findById(form.getIdStart()).orElseThrow(()-> new ErroInternoException("Start não encontrado ao salvar uma Entrevista Tecnica"));
		start.setEntrevistaTecnica(entrevista);
		startRepository.save(start);
		
		if(form.getIdAgendamento() != null && agendamentoRepository.existsById(form.getIdAgendamento())) {
			agendamentoRepository.deleteById(form.getIdAgendamento());
		}
	}
}

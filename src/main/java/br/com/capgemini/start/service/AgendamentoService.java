package br.com.capgemini.start.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.factory.AgendamentoFactory;
import br.com.capgemini.start.factory.UsuarioLogadoFactory;
import br.com.capgemini.start.model.Agendamento;
import br.com.capgemini.start.model.Permissao;
import br.com.capgemini.start.model.dto.AgendamentoDto;
import br.com.capgemini.start.model.dto.UsuarioDto;
import br.com.capgemini.start.model.form.AgendamentoEntrevistaForm;
import br.com.capgemini.start.repository.AgendamentoRepository;
import br.com.capgemini.start.repository.CoachRepository;
import br.com.capgemini.start.repository.StartRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AgendamentoService {

	private static final Sort SORT = Sort.by(Sort.Direction.ASC, "coach.nome");
	
	@Autowired
	private AgendamentoRepository repository;
	
	@Autowired
	private AgendamentoFactory factory;
	
	@Autowired
	private StartRepository startRepository;
	
	@Autowired
	private CoachRepository coachRepository;
	
	@Autowired
	private UsuarioLogadoFactory usuarioLogadoFactory;
	
	public void salvarNegocio(AgendamentoEntrevistaForm form) {
		Agendamento agendamento = new Agendamento();
		agendamento.setNome("Entrevista de Negócio");
		agendamento.setLink("xx");
		agendamento.setLinkExcluir("xx");
		agendamento.setDataHora(LocalDateTime.now());
		agendamento.setStart(startRepository.findById(form.getIdStart()).orElseThrow(()-> new ErroInternoException("Start não encontrado ao salvar um Agendamento Entrevista Form")));
		agendamento.setCoach(coachRepository.findById(form.getIdCoachEntrevista()).orElseThrow(()-> new ErroInternoException("Coach não encontrado ao salvar um Agendamento Entrevista Form")));
		
		log.info("salvar entity={}", agendamento);
		
		agendamento = repository.save(agendamento);
		
		agendamento.setLink("/entrevista/negocio/agendamento?idStart=" +form.getIdStart()+ "&idCoachEntrevista=" +form.getIdCoachEntrevista() + "&idAgendamento=" +agendamento.getId());
		agendamento.setLinkExcluir("/agendamento/excluir?id=" +agendamento.getId());
		repository.save(agendamento);
	}
	
	public void salvarTecnica(AgendamentoEntrevistaForm form) {
		Agendamento agendamento = new Agendamento();
		agendamento.setNome("Entrevista de Negócio");
		agendamento.setLink("xx");
		agendamento.setLinkExcluir("xx");
		agendamento.setDataHora(LocalDateTime.now());
		agendamento.setStart(startRepository.findById(form.getIdStart()).orElseThrow(()-> new ErroInternoException("Start não encontrado ao salvar um Agendamento")));
		agendamento.setCoach(coachRepository.findById(form.getIdCoachEntrevista()).orElseThrow(()-> new ErroInternoException("Coach não encontrado ao salvar um Agendamento")));
		
		log.info("salvar entity={}", agendamento);
		
		agendamento = repository.save(agendamento);
		
		agendamento.setLink("/entrevista/tecnica/agendamento?idStart=" +form.getIdStart()+ "&idCoachEntrevista=" +form.getIdCoachEntrevista() + "&idAgendamento=" +agendamento.getId());
		agendamento.setLinkExcluir("/agendamento/excluir?id=" +agendamento.getId());
		repository.save(agendamento);
	}

	public void excluir(Long id) {
		repository.deleteById(id);
	}
	
	public List<AgendamentoDto> listarDoUsuarioLogadoTodos() {
		UsuarioDto usuarioLogado = usuarioLogadoFactory.usuarioLogado().orElseThrow(()-> new ErroInternoException("Usuario logado não encontrado"));
		
		List<Agendamento> agendamentos;
		if(Permissao.ADM.equals(usuarioLogado.getPermissao())) {
			agendamentos = repository.findAll(SORT);
		} else {
			switch (usuarioLogado.getTipo()) {
				case GESTOR: agendamentos = repository.findByCoach_gestor_id(usuarioLogado.getId(), SORT);
				break;
		
				case COACH: agendamentos = repository.findByCoach_id(usuarioLogado.getId(), SORT);
				break;
				
				case START:
				default: agendamentos = new ArrayList<>();
			}		
		}
		
		return factory.listDto(agendamentos);
	}
	
	public List<AgendamentoDto> listarDoUsuarioLogadoProjeto() {
		UsuarioDto usuarioLogado = usuarioLogadoFactory.usuarioLogado().orElseThrow(()-> new ErroInternoException("Usuario logado não encontrado"));
		
		List<Agendamento> agendamentos;
		switch (usuarioLogado.getTipo()) {
			case GESTOR: agendamentos = repository.findByCoach_gestor_id(usuarioLogado.getId(), SORT);
			break;
	
			case COACH: agendamentos = repository.findByCoach_id(usuarioLogado.getId(), SORT);
			break;
			
			case START:
			default: agendamentos = new ArrayList<>();
		}
		
		return factory.listDto(agendamentos);
	}
}

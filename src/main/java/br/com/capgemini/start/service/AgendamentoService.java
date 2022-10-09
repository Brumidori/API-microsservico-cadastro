package br.com.capgemini.start.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.factory.UsuarioLogadoFactory;
import br.com.capgemini.start.model.Agendamento;
import br.com.capgemini.start.model.Coach;
import br.com.capgemini.start.model.Gestor;
import br.com.capgemini.start.model.Permissao;
import br.com.capgemini.start.model.Usuario;
import br.com.capgemini.start.model.dto.AgendamentoDto;
import br.com.capgemini.start.model.dto.CoachDto;
import br.com.capgemini.start.model.dto.StartDto;
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
	private StartRepository startRepository;
	
	@Autowired
	private CoachRepository coachRepository;

	@Autowired
	private ModelMapper mapper;
	
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
		Usuario usuarioLogado = usuarioLogadoFactory.usuarioLogado().orElseThrow(()-> new ErroInternoException("Usuario logado não encontrado"));
		
		List<Agendamento> agendamentos;
		if(Permissao.ADM.equals(usuarioLogado.getPermissao())) {
			agendamentos = repository.findAll(SORT);
		} else if (usuarioLogado instanceof Gestor) {
			agendamentos = repository.findByCoach_gestor_id(usuarioLogado.getId(), SORT);
		} else if (usuarioLogado instanceof Coach) {
			agendamentos = repository.findByCoach_id(usuarioLogado.getId(), SORT);
		} else {
			agendamentos = new ArrayList<>();
		}
		
		return agendamentos.stream().map(this::agendamentoDto).collect(Collectors.toList());
	}
	
	public List<AgendamentoDto> listarDoUsuarioLogadoProjeto() {
		Usuario usuarioLogado = usuarioLogadoFactory.usuarioLogado().orElseThrow(()-> new ErroInternoException("Usuario logado não encontrado"));
		
		List<Agendamento> agendamentos;
		if (usuarioLogado instanceof Gestor) {
			agendamentos = repository.findByCoach_gestor_id(usuarioLogado.getId(), SORT);
		} else if (usuarioLogado instanceof Coach) {
			agendamentos = repository.findByCoach_id(usuarioLogado.getId(), SORT);
		} else {
			agendamentos = new ArrayList<>();
		}
		
		return agendamentos.stream().map(this::agendamentoDto).collect(Collectors.toList());
	}
	
	private AgendamentoDto agendamentoDto(Agendamento agendamento) {
		AgendamentoDto dto =  mapper.map(agendamento, AgendamentoDto.class);
		
		dto.setStart(mapper.map(agendamento.getStart(), StartDto.class));
		CoachDto coach = mapper.map(agendamento.getCoach(), CoachDto.class);
		coach.setCor(agendamento.getCoach().getGestor().getCor());
		dto.setCoach(coach);
		
		return dto;
	}
}

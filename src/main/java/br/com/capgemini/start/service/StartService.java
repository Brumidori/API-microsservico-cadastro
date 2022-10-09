package br.com.capgemini.start.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.model.Avaliacao;
import br.com.capgemini.start.model.Farol;
import br.com.capgemini.start.model.Permissao;
import br.com.capgemini.start.model.Start;
import br.com.capgemini.start.model.dto.AvaliacaoDto;
import br.com.capgemini.start.model.dto.CoachDto;
import br.com.capgemini.start.model.dto.EntrevistaNegocioDto;
import br.com.capgemini.start.model.dto.EntrevistaTecnicaDto;
import br.com.capgemini.start.model.dto.GestorDto;
import br.com.capgemini.start.model.dto.StartDto;
import br.com.capgemini.start.model.dto.StartRelatorioDto;
import br.com.capgemini.start.model.dto.TurmaDto;
import br.com.capgemini.start.model.form.ListaStartForm;
import br.com.capgemini.start.model.form.StartForm;
import br.com.capgemini.start.repository.AvaliacaoRepository;
import br.com.capgemini.start.repository.CoachRepository;
import br.com.capgemini.start.repository.EntrevistaNegocioRepository;
import br.com.capgemini.start.repository.EntrevistaTecnicaRepository;
import br.com.capgemini.start.repository.GestorRepository;
import br.com.capgemini.start.repository.StartRepository;
import br.com.capgemini.start.repository.TurmaRepository;
import br.com.capgemini.start.specification.StartSpecification;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class StartService {

	private static final Sort SORT = Sort.by(Sort.Direction.ASC, "turma.nome", "gestor.nome", "nome");
	
	@Autowired
	private StartRepository repository;
	
	@Autowired
	private EntrevistaNegocioRepository entrevistaNegocioRepository;
	
	@Autowired
	private EntrevistaTecnicaRepository entrevistaTecnicaRepository;
	
	@Autowired
	private CoachRepository coachRepository;
	
	@Autowired
	private GestorRepository gestorRepository;
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public StartForm form(Long id) {
		Start start = repository.findById(id).orElseThrow(()-> new ErroInternoException("Start não encontrado na geração do Start Form"));
		
		StartForm form =  mapper.map(start, StartForm.class);
		
		if(start.getEntrevistaNegocio() != null) {
			form.setIdEntrevistaNegocio(start.getEntrevistaNegocio().getId());
		}
		if(start.getEntrevistaTecnica() != null) {
			form.setIdEntrevistaTecnica(start.getEntrevistaTecnica().getId());
		}
		if(start.getCoach() != null) {
			form.setIdCoach(start.getCoach().getId());
		}
		form.setIdGestor(start.getGestor().getId());
		form.setIdTurma(start.getTurma().getId());
		
		return form;
	}
	
	public void salvar(StartForm form) {
		Start start = mapper.map(form, Start.class);
		
		start.setPermissao(Permissao.START);
		if(form.isBillable()) {
			if(form.getDataBillable() == null) {
				start.setDataBillable(LocalDate.now());
			}
		} else {
			start.setDataBillable(null);
		}
		
		if(form.getIdEntrevistaNegocio() != null) {
			start.setEntrevistaNegocio(entrevistaNegocioRepository.findById(form.getIdEntrevistaNegocio()).orElseThrow(()-> new ErroInternoException("Entrevista Negocio não encontrada ao salvar um Start")));
		} 
		
		if(form.getIdEntrevistaTecnica() != null) {
			start.setEntrevistaTecnica(entrevistaTecnicaRepository.findById(form.getIdEntrevistaTecnica()).orElseThrow(()-> new ErroInternoException("Entrevista Tecnica não encontrada ao salvar um Start")));
		} 
		
		if(form.getIdCoach() != null) {
			start.setCoach(coachRepository.findById(form.getIdCoach()).orElseThrow(()-> new ErroInternoException("Coach não encontrado ao salvar um Start")));
		} 
		start.setGestor(gestorRepository.findById(form.getIdGestor()).orElseThrow(()-> new ErroInternoException("Gestor não encontrado ao salvar um Start")));
		start.setTurma(turmaRepository.findById(form.getIdTurma()).orElseThrow(()-> new ErroInternoException("Turma não encontrada ao salvar um Start")));
		
		log.info("salvar entity={}", start);
		
		start.setPassword(new BCryptPasswordEncoder().encode("1234"));
		
		repository.save(start);
	}
	
	public String billable(Long id) {
		Start start = repository.findById(id).orElseThrow(()-> new ErroInternoException("Start não encontrado ao torna-lo billable"));
		
		start.setBillable(true);
		start.setDataBillable(LocalDate.now());
		
		repository.save(start);
		
		return start.getNome();
	}
	
	public void excluir(Long id) {
		repository.deleteById(id);
	}
	
	public List<StartDto> listar() {
		return repository.findAll(SORT).stream().map(this::startDto).collect(Collectors.toList());
	}
	
	public List<StartDto> listar(ListaStartForm form) {
		Optional<Specification<Start>> optional = new StartSpecification()
				.nome(form.getNome())
				.emailUsuario(form.getEmailUsuario())
				.atuacao(form.getAtuacao())
				.nomeCoach(form.getNomeCoach())
				.nomeGestor(form.getNomeGestor())
				.nomeTurma(form.getNomeTurma())
				.specification();
		
		List<Start> contatos = optional.isPresent() ? repository.findAll(optional.get(), SORT) : repository.findAll(SORT);
		
		return contatos.stream().map(this::startDto).collect(Collectors.toList());
	}
	
	private StartDto startDto(Start start) {
		StartDto dto =  mapper.map(start, StartDto.class);
		
		if(start.getEntrevistaNegocio() != null) {
			dto.setFarolEntrevistaNegocio(start.getEntrevistaNegocio().media());
			dto.setParecerEntrevistaNegocio(start.getEntrevistaNegocio().getParecer());
			dto.setFezEntrevistaNegocio(true);
		} else {
			dto.setFarolEntrevistaNegocio(Farol.BRANCO);
		}
		
		if(start.getEntrevistaTecnica() != null) {
			dto.setFarolEntrevistaTecnica(start.getEntrevistaTecnica().media());
			dto.setParecerEntrevistaTecnica(start.getEntrevistaTecnica().getParecer());
			dto.setFezEntrevistaTecnica(true);
		} else {
			dto.setFarolEntrevistaTecnica(Farol.BRANCO);
		}	
		
		if(start.getUltimaAvaliacao() != null) {
			dto.setFarolUltimaAvaliacao(start.getUltimaAvaliacao().getFarol());
			dto.setParecerUltimaAvaliacao(start.getUltimaAvaliacao().getParecer());
			dto.setDataUltimaAvaliacao(start.getUltimaAvaliacao().getData());
			dto.setFezAvaliacao(true);
			dto.setFezAvaliacaoNoDia(LocalDate.now().equals(start.getUltimaAvaliacao().getData()));
		} else {
			dto.setFarolUltimaAvaliacao(Farol.BRANCO);
		}
		
		if(start.getCoach() != null) {
			dto.setCoach(mapper.map(start.getCoach(), CoachDto.class));
		} 
		
		GestorDto gestor = mapper.map(start.getGestor(), GestorDto.class);
		dto.setGestor(gestor);
		
		dto.setCor(gestor.getCor());
		
		dto.setTurma(mapper.map(start.getTurma(), TurmaDto.class));
		
		return dto;
	}
	
	public StartRelatorioDto relatorio(Long id) {
		Start start = repository.findById(id).orElseThrow(()-> new ErroInternoException("Start não encontrado na geração do Start Relatório"));
		
		StartRelatorioDto dto =  mapper.map(start, StartRelatorioDto.class);
		
		List<Avaliacao> avaliacoes = avaliacaoRepository.findByIdStart(id, Sort.by(Sort.Direction.ASC, "data"));
		dto.setAvaliacoes(mapper.map(avaliacoes, new TypeToken<List<AvaliacaoDto>>() {}.getType()));
		
		if(start.getEntrevistaNegocio() != null) {
			dto.setEntrevistaNegocio(mapper.map(start.getEntrevistaNegocio(), EntrevistaNegocioDto.class));
		}
		if(start.getEntrevistaTecnica() != null) {
			dto.setEntrevistaTecnica(mapper.map(start.getEntrevistaTecnica(), EntrevistaTecnicaDto.class));
		}
		if(start.getCoach() != null) {
			dto.setCoach(mapper.map(start.getCoach(), CoachDto.class));
		}
		dto.setGestor(mapper.map(start.getGestor(), GestorDto.class));
		dto.setTurma(mapper.map(start.getTurma(), TurmaDto.class));
		
		return dto;
	}
}

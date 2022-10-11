package br.com.capgemini.start.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.factory.StartFactory;
import br.com.capgemini.start.model.Permissao;
import br.com.capgemini.start.model.Start;
import br.com.capgemini.start.model.dto.StartDto;
import br.com.capgemini.start.model.dto.StartRelatorioDto;
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
	private StartFactory factory;
	
	@Autowired
	private EntrevistaNegocioRepository entrevistaNegocioRepository;
	
	@Autowired
	private EntrevistaTecnicaRepository entrevistaTecnicaRepository;
	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	private CoachRepository coachRepository;
	
	@Autowired
	private GestorRepository gestorRepository;
	
	@Autowired
	private TurmaRepository turmaRepository;
	
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
		
		if(form.getIdUltimaAvaliacao() != null) {
			start.setUltimaAvaliacao(avaliacaoRepository.findById(form.getIdUltimaAvaliacao()).orElseThrow(()-> new ErroInternoException("Ultima Avaliacao não encontrada ao salvar um Start")));
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
		return factory.listDto(repository.findAll(SORT));
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
		
		List<Start> starts = optional.isPresent() ? repository.findAll(optional.get(), SORT) : repository.findAll(SORT);
		
		return factory.listDto(starts);
	}
	
	public StartRelatorioDto relatorio(Long id) {
		return factory.relatorioDto(repository.findById(id).orElseThrow(()-> new ErroInternoException("Start não encontrado na geração do Start Relatório")));
	}
}

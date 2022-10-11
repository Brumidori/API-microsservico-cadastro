package br.com.capgemini.start.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.factory.AgendamentoFactory;
import br.com.capgemini.start.factory.CoachFactory;
import br.com.capgemini.start.factory.StartFactory;
import br.com.capgemini.start.factory.UsuarioLogadoFactory;
import br.com.capgemini.start.model.Permissao;
import br.com.capgemini.start.model.dto.HomeDto;
import br.com.capgemini.start.model.dto.UsuarioDto;
import br.com.capgemini.start.repository.AgendamentoRepository;
import br.com.capgemini.start.repository.CoachRepository;
import br.com.capgemini.start.repository.StartRepository;

@Service
public class HomeService {

	@Autowired
	private AgendamentoRepository agendamentoRepository;

	@Autowired
	private StartRepository startRepository;
	
	@Autowired
	private CoachRepository coachsRepository;
	
	@Autowired
	private AgendamentoFactory agendamentoFactory;
	
	@Autowired
	private StartFactory startFactory;
	
	@Autowired
	private CoachFactory coachsFactory;
	
	@Autowired
	private UsuarioLogadoFactory usuarioLogadoFactory;
	
	public HomeDto home() {
		UsuarioDto usuarioLogado = usuarioLogadoFactory.usuarioLogado().orElseThrow(()-> new ErroInternoException("Usuario logado não encontrado"));
		
		HomeDto dto = new HomeDto();
		switch (usuarioLogado.getTipo()) {
			case GESTOR: {
				Long idGestor = usuarioLogado.getId();
				
				dto.setStartsDosCoachs(startFactory.listDto(startRepository.findAllByGestor_id(idGestor, Sort.by(Sort.Direction.ASC, "coach.nome", "nome"))));
				dto.setCoachs(coachsFactory.listDto(coachsRepository.findByGestor_id(idGestor, Sort.by(Sort.Direction.ASC, "nome"))));
				dto.setAgendamentosDosCoachs(agendamentoFactory.listDto(agendamentoRepository.findByCoach_gestor_id(usuarioLogado.getId(), Sort.by(Sort.Direction.ASC, "coach.nome", "dataHora"))));
			} break;
	
			case COACH: {
				Long idCoach = usuarioLogado.getId();
				
				dto.setStarts(startFactory.listDto(startRepository.findAllByCoach_id(idCoach, Sort.by(Sort.Direction.ASC, "nome"))));
				dto.setAgendamentos(agendamentoFactory.listDto(agendamentoRepository.findByCoach_id(idCoach, Sort.by(Sort.Direction.ASC, "dataHora"))));
			} break;
			
			case START:{
				//nada
			} break;
			
			default:
		}
		
		if(Permissao.ADM.equals(usuarioLogado.getPermissao())) {
			dto.setAgendamentosDeTodos(agendamentoFactory.listDto(agendamentoRepository.findAll(Sort.by(Sort.Direction.ASC, "dataHora"))));
		}
		
		return dto;
	}
}

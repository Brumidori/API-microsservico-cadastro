package br.com.capgemini.start.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.factory.CoachFactory;
import br.com.capgemini.start.model.Coach;
import br.com.capgemini.start.model.Gestor;
import br.com.capgemini.start.model.Permissao;
import br.com.capgemini.start.model.dto.CoachDto;
import br.com.capgemini.start.model.form.CoachForm;
import br.com.capgemini.start.repository.CoachRepository;
import br.com.capgemini.start.repository.GestorRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CoachService {
	
	@Autowired
	private CoachRepository repository;
	
	@Autowired
	private CoachFactory factory;
	
	@Autowired
	private GestorRepository gestorRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public CoachForm form(Long id) {
		Coach coach = repository.findById(id).orElseThrow(()-> new ErroInternoException("Coach não encontrado na geração do Coach Form"));
		
		CoachForm form =  mapper.map(coach, CoachForm.class);
		form.setIdGestor(coach.getGestor().getId());
		form.setAdm(coach.getPermissao().isAdm());
		if(coach.getSquad() != null) {
			form.setIdSquad(coach.getSquad().getId());
		}
		
		return form;
	}
	
	public void salvar(CoachForm form) {
		Coach coach = mapper.map(form, Coach.class);
		coach.setPermissao(form.isAdm() ? Permissao.ADM : Permissao.COACH);
		
		Gestor gestor = gestorRepository.findById(form.getIdGestor()).orElseThrow(()-> new ErroInternoException("Gestor não encontrado ao salvar um Coach"));
		coach.setGestor(gestor);
		
		log.info("salvar entity={}", coach);
		
		coach.setPassword(usuarioService.senha(form.getId(), form.isReiniciarSenha()));
		
		repository.save(coach);
	}
	
	public void excluir(Long id) {
		repository.deleteById(id);
	}
	
	public List<CoachDto> listar() {
		return factory.listDto(repository.findAll(Sort.by(Sort.Direction.ASC, "gestor.nome", "nome")));
	}
	
	public List<CoachDto> listarGestor(Long idGestor) {
		return factory.listDto(repository.findByGestor_id(idGestor, Sort.by(Sort.Direction.ASC, "nome")));
	}
}

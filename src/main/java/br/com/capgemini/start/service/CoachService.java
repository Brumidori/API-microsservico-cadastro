package br.com.capgemini.start.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.model.Coach;
import br.com.capgemini.start.model.Gestor;
import br.com.capgemini.start.model.Permissao;
import br.com.capgemini.start.model.dto.CoachDto;
import br.com.capgemini.start.model.dto.GestorDto;
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
	private GestorRepository gestorRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public CoachForm form(Long id) {
		Coach coach = repository.findById(id).orElseThrow(()-> new ErroInternoException("Coach não encontrado na geração do Coach Form"));
		
		CoachForm form =  mapper.map(coach, CoachForm.class);
		form.setIdGestor(coach.getGestor().getId());
		form.setAdm(coach.getPermissao().isAdm());
		
		return form;
	}
	
	public void salvar(CoachForm form) {
		Coach coach = mapper.map(form, Coach.class);
		coach.setPermissao(Boolean.TRUE.equals(form.getAdm()) ? Permissao.ADM : Permissao.COACH);
		
		Gestor gestor = gestorRepository.findById(form.getIdGestor()).orElseThrow(()-> new ErroInternoException("Gestor não encontrado ao salvar um Coach"));
		coach.setGestor(gestor);
		
		log.info("salvar entity={}", coach);
		
		coach.setPassword(new BCryptPasswordEncoder().encode("1234"));
		
		repository.save(coach);
	}
	
	public void excluir(Long id) {
		repository.deleteById(id);
	}
	
	public List<CoachDto> listar() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "gestor.nome", "nome")).stream().map(this::coachDto).collect(Collectors.toList());
	}
	
	public List<CoachDto> listarGestor(Long idGestor) {
		return repository.findByGestor_id(idGestor, Sort.by(Sort.Direction.ASC, "nome")).stream().map(this::coachDto).collect(Collectors.toList());
	}
	
	private CoachDto coachDto(Coach coach) {
		CoachDto dto =  mapper.map(coach, CoachDto.class);
		
		dto.setGestor(mapper.map(coach.getGestor(), GestorDto.class));
		dto.setCor(coach.getGestor().getCor());
		
		return dto;
	}
}

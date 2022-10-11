package br.com.capgemini.start.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.factory.GestorFactory;
import br.com.capgemini.start.model.Gestor;
import br.com.capgemini.start.model.Permissao;
import br.com.capgemini.start.model.dto.GestorDto;
import br.com.capgemini.start.model.form.GestorForm;
import br.com.capgemini.start.repository.GestorRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class GestorService {

	@Autowired
	private GestorRepository repository;

	@Autowired
	private GestorFactory factory;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public GestorForm form(Long id) {
		Gestor gestor = repository.findById(id).orElseThrow(()-> new ErroInternoException("Gestor não encontrado na geração do Gestor Form"));
		
		GestorForm form = mapper.map(gestor, GestorForm.class);
		form.setAdm(gestor.getPermissao().isAdm());
		
		return form;
	}
	
	public void salvar(GestorForm form) {
		Gestor gestor = mapper.map(form, Gestor.class);
		gestor.setPermissao(form.isAdm() ? Permissao.ADM : Permissao.GESTOR);
		
		log.info("salvar entity={}", gestor);
		
		gestor.setPassword(usuarioService.senha(form.getId(), form.isReiniciarSenha()));
		
		repository.save(gestor);
	}
	
	public void excluir(Long id) {
		repository.deleteById(id);
	}
	
	public List<GestorDto> listar() {
		return factory.listDto(repository.findAll(Sort.by(Sort.Direction.ASC, "nome")));
	}
}

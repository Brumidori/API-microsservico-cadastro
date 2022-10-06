package br.com.capgemini.start.specification;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import br.com.capgemini.start.model.Atuacao;
import br.com.capgemini.start.model.Start;

//https://www.baeldung.com/spring-data-criteria-queries
public class StartSpecification implements SpecificationTemplate <Start>{

	private List<Specification<Start>> specifications = new ArrayList<>();
	
	@Override
	public List<Specification<Start>> getSpecifications() {
		return specifications;
	}
	
	public StartSpecification nome(String nome) {
		if(StringUtils.isNotBlank(nome)) {
			specifications.add((root, criteriaQuery, criteriaBuilder) -> 
				criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome + "%"));
		}
			
		return this;
	}
	
	public StartSpecification emailUsuario(String emailUsuario) {
		if(StringUtils.isNotBlank(emailUsuario)) {
			specifications.add((root, criteriaQuery, criteriaBuilder) -> 
				criteriaBuilder.equal(root.get("usuario").get("email"), emailUsuario));
		}
			
		return this;
	}
	
	public StartSpecification atuacao(Atuacao atuacao) {
		if(atuacao != null) {
			specifications.add((root, criteriaQuery, criteriaBuilder) -> 
				criteriaBuilder.equal(root.get("atuacao"), atuacao));
		}
			
		return this;
	}
	
	public StartSpecification nomeCoach(String nomeCoach) {
		if(StringUtils.isNotBlank(nomeCoach)) {
			specifications.add((root, criteriaQuery, criteriaBuilder) -> 
				criteriaBuilder.like(criteriaBuilder.lower(root.get("coach").get("nome")), "%" + nomeCoach + "%"));
		}
			
		return this;
	}
	
	public StartSpecification nomeGestor(String nomeGestor) {
		if(StringUtils.isNotBlank(nomeGestor)) {
			specifications.add((root, criteriaQuery, criteriaBuilder) -> 
				criteriaBuilder.like(criteriaBuilder.lower(root.get("gestor").get("nome")), "%" + nomeGestor + "%"));
		}
			
		return this;
	}
	
	public StartSpecification nomeTurma(String nomeTurma) {
		if(StringUtils.isNotBlank(nomeTurma)) {
			specifications.add((root, criteriaQuery, criteriaBuilder) -> 
				criteriaBuilder.equal(root.get("turma").get("nome"), nomeTurma));
		}
			
		return this;
	}
}

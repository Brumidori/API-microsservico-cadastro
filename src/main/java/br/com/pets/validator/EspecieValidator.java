package br.com.pets.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import br.com.pets.exception.PetException;
import br.com.pets.model.Especie;
import br.com.pets.repository.EspecieRepository;
import br.com.pets.repository.PetRepository;

@Component
public class EspecieValidator {

	@Autowired
	private EspecieRepository repository;
	@Autowired
	private PetRepository petRepository;

	public void salvar(Especie model, BindingResult result) {

		if (model.getId() == null && model.getNome() != null && repository.existsByNome(model.getNome())) {
			result.rejectValue("nome", "erro de banco de dados", new String[] { "nome" }, "nome já cadastrado");
		} else if (model.getId() != null && model.getNome() != null
				&& repository.existsByIdNotAndNome(model.getId(), model.getNome())) {
			result.rejectValue("nome", "erro de banco de dados", new String[] { "nome" }, "nome já cadastrado");
		}
	}

	public void excluir(Integer id) {
		if (petRepository.existsByEspecie_id(id)) {
			throw new PetException("Erro ao excluir Espécie, Espécie associada a um Pet");
		}
	}
}

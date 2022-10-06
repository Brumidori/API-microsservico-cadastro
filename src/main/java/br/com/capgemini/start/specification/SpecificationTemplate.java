package br.com.capgemini.start.specification;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationTemplate <M>{
	public abstract List<Specification<M>> getSpecifications();
	
	public default Optional<Specification<M>> specification(){
		List<Specification<M>> specifications = getSpecifications();
		
		if(specifications.isEmpty()) {
			return Optional.empty();
		}
		
		Specification<M> specification = null;
		for (Specification<M> s : specifications) {
			if(specification == null) {
				specification = s;
			} else specification = specification.and(s);
		}
		
		return Optional.of(Specification.where(specification));
	}
}

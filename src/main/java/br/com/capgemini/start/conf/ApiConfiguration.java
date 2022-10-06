package br.com.capgemini.start.conf;

import javax.servlet.Filter;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.capgemini.start.model.Coach;
import br.com.capgemini.start.model.Gestor;
import br.com.capgemini.start.model.Start;
import br.com.capgemini.start.model.dto.CoachDto;
import br.com.capgemini.start.model.dto.GestorDto;
import br.com.capgemini.start.model.dto.StartDto;
import br.com.capgemini.start.model.form.CoachForm;
import br.com.capgemini.start.model.form.GestorForm;
import br.com.capgemini.start.model.form.StartForm;


@Configuration
public class ApiConfiguration {
	
	@Bean
    public FilterRegistrationBean<Filter> registrarFiltroCors() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>(new CorsFilter());
        registration.setOrder(0);
        return registration;
    }
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper =  new ModelMapper();
		mapper.getConfiguration().setAmbiguityIgnored(true);
		
		{
			TypeMap<Gestor, GestorDto> propertyDto = mapper.createTypeMap(Gestor.class, GestorDto.class);
			propertyDto.addMappings(m -> m.map(Gestor::getUsername, GestorDto::setEmail));
			
			TypeMap<GestorForm, Gestor> propertyForm = mapper.createTypeMap(GestorForm.class, Gestor.class);
			propertyForm.addMappings(m -> m.map(GestorForm::getEmail, Gestor::setUsername));
			
			TypeMap<Gestor, GestorForm> propertyForm2 = mapper.createTypeMap(Gestor.class, GestorForm.class);
			propertyForm2.addMappings(m -> m.map(Gestor::getUsername, GestorForm::setEmail));
		}
		
		{
			TypeMap<Coach, CoachDto> propertyDto = mapper.createTypeMap(Coach.class, CoachDto.class);
			propertyDto.addMappings(m -> m.map(Coach::getUsername, CoachDto::setEmail));
			
			TypeMap<CoachForm, Coach> propertyForm = mapper.createTypeMap(CoachForm.class, Coach.class);
			propertyForm.addMappings(m -> m.map(CoachForm::getEmail, Coach::setUsername));
			
			TypeMap<Coach, CoachForm> propertyForm2 = mapper.createTypeMap(Coach.class, CoachForm.class);
			propertyForm2.addMappings(m -> m.map(Coach::getUsername, CoachForm::setEmail));
		}
		
		{
			TypeMap<Start, StartDto> propertyDto = mapper.createTypeMap(Start.class, StartDto.class);
			propertyDto.addMappings(m -> m.map(Start::getUsername, StartDto::setEmail));
			
			TypeMap<StartForm, Start> propertyForm = mapper.createTypeMap(StartForm.class, Start.class);
			propertyForm.addMappings(m -> m.map(StartForm::getEmail, Start::setUsername));
			
			TypeMap<Start, StartForm> propertyForm2 = mapper.createTypeMap(Start.class, StartForm.class);
			propertyForm2.addMappings(m -> m.map(Start::getUsername, StartForm::setEmail));
		}
		
		return mapper;
	}
}

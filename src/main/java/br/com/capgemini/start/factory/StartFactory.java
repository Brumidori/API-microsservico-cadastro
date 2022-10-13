package br.com.capgemini.start.factory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import br.com.capgemini.start.model.Avaliacao;
import br.com.capgemini.start.model.Farol;
import br.com.capgemini.start.model.Start;
import br.com.capgemini.start.model.dto.AvaliacaoDto;
import br.com.capgemini.start.model.dto.CoachDto;
import br.com.capgemini.start.model.dto.EntrevistaNegocioDto;
import br.com.capgemini.start.model.dto.EntrevistaTecnicaDto;
import br.com.capgemini.start.model.dto.GestorDto;
import br.com.capgemini.start.model.dto.SquadDto;
import br.com.capgemini.start.model.dto.StartDto;
import br.com.capgemini.start.model.dto.StartRelatorioDto;
import br.com.capgemini.start.model.dto.TurmaDto;
import br.com.capgemini.start.repository.AvaliacaoRepository;

@Component
public class StartFactory {
	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public List<StartDto> listDto(List<Start> starts) {
		return starts.stream().map(this::dto).collect(Collectors.toList());
	}
	
	public StartDto dto(Start start) {
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
		if(start.getSquad() != null) {
			dto.setSquad(mapper.map(start.getSquad(), SquadDto.class));
		}
		
		return dto;
	}
	
	public StartRelatorioDto relatorioDto(Start start) {
		StartRelatorioDto dto =  mapper.map(start, StartRelatorioDto.class);
		
		List<Avaliacao> avaliacoes = avaliacaoRepository.findByIdStart(start.getId(), Sort.by(Sort.Direction.ASC, "data"));
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
		dto.setSquad(mapper.map(start.getSquad(), SquadDto.class));
		
		return dto;
	}
}

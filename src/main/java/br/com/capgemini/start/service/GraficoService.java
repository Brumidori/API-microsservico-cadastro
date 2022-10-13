package br.com.capgemini.start.service;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.model.Avaliacao;
import br.com.capgemini.start.model.Start;
import br.com.capgemini.start.repository.AvaliacaoRepository;
import br.com.capgemini.start.repository.StartRepository;
import lombok.Getter;

@Service
public class GraficoService {

	@Autowired
	private StartRepository startRepository;
	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	public byte[] startAvaliacoes(Long idStart)  {
		try (ByteArrayOutputStream imagem = new ByteArrayOutputStream()){
			List<Avaliacao> avaliacoes = avaliacaoRepository.findByIdStart(idStart, Sort.by(Sort.Direction.ASC, "data"));
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
			
			DefaultCategoryDataset ds = new DefaultCategoryDataset();
			for (Avaliacao avaliacao : avaliacoes) {
				ds.addValue(avaliacao.getFarol().getValor(), "avaliação", avaliacao.getData().format(formatter));
			}
			
			JFreeChart grafico = ChartFactory.createLineChart("", null, null, ds, PlotOrientation.VERTICAL, true, true, false);
			
			// aparencia
			grafico.setBackgroundPaint( new Color(255,255,0,0));
			
			CategoryItemRenderer renderer = grafico.getCategoryPlot().getRenderer();
			renderer.setSeriesPaint(0, Color.blue.brighter());
			renderer.setSeriesVisible(0, true);
			renderer.setSeriesVisibleInLegend(0, false); // remove legenda
			
			//leganda
			{
				TextTitle title = grafico.getTitle();
	            Font font = title.getFont();
	            title.setFont(font.deriveFont(15.0F));
			}
			
			//leganda
			{
				LegendTitle legend = grafico.getLegend();
				Font font = legend.getItemFont().deriveFont(10.0f);
				legend.setItemFont(font);
			}
			
			//leganda
			{
				CategoryPlot categoryPlot = grafico.getCategoryPlot();
				CategoryAxis axis = categoryPlot.getDomainAxis();
				//ValueAxis axis = categoryPlot.getRangeAxis();
				Font font = axis.getLabelFont();
				axis.setTickLabelFont(font.deriveFont(6.0F));
			}
            
			ChartUtilities.writeChartAsPNG(imagem, grafico, 700, 350);
			

			return imagem.toByteArray();
		} catch (Exception e) {
			throw new ErroInternoException("Erro ao gerar grafico (" + e.getMessage() + ")");
		}
	}
	
	@Getter
	private class Contador {
		private int v = 0;
		
		public void incramenta() {
			v++;
		}
	}
	
	public byte[] startPizzaFaroisTurma(Long idTurma)  {
		try (ByteArrayOutputStream imagem = new ByteArrayOutputStream()){
			List<Start> starts = startRepository.findAllByTurma_id(idTurma);
			
			LinkedHashMap<String, Contador> contadores = new LinkedHashMap<>();
			contadores.put("branco", new Contador());
			contadores.put("vermelho", new Contador());
			contadores.put("amarelo", new Contador());
			contadores.put("verde", new Contador());
			contadores.put("azul", new Contador());
			contadores.put("billable", new Contador());
			
			
			for (Start start : starts) {
				if(Boolean.TRUE.equals(start.getBillable())) {
					contadores.get("billable").incramenta();
				} else if(start.getUltimaAvaliacao() == null) {
					contadores.get("branco").incramenta();
				} else {
					switch (start.getUltimaAvaliacao().getFarol()) {
						case VERMELHO: contadores.get("vermelho").incramenta();
						break;
	
						case AMARELO: contadores.get("amarelo").incramenta();
						break;
							
						case VERDE: contadores.get("verde").incramenta();
						break;
							
						case AZUL: contadores.get("azul").incramenta();
						break;
						
						default:
					}
				}
			}
			
			DefaultPieDataset  ds = new DefaultPieDataset ();
			for (Entry<String, Contador> entry : contadores.entrySet()) {
				if(entry.getValue().getV() > 0) {
					ds.setValue(entry.getKey(), entry.getValue().getV());  
				}
			}
			
			JFreeChart grafico = ChartFactory.createPieChart(      
			         "Faróis",   // titulo
			         ds,          
			         true,      // legenda
			         true, 
			         false);			
			
			PiePlot plot = (PiePlot) grafico.getPlot();
			
			for (Entry<String, Contador> entry : contadores.entrySet()) {
				if(entry.getValue().getV() > 0) {
					switch (entry.getKey()) {
						case "branco": plot.setSectionPaint("branco", Color.white);
						break;
					
						case "vermelho": plot.setSectionPaint("vermelho", Color.red);
						break;
	
						case "amarelo": plot.setSectionPaint("amarelo", Color.yellow);
						break;
							
						case "verde": plot.setSectionPaint("verde", Color.green);
						break;
							
						case "azul": plot.setSectionPaint("azul", Color.cyan);
						break;
						
						case "billable": plot.setSectionPaint("billable", Color.blue);
						break;
						
						default:
					}
				}
			}
			
			PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{1} - {2}", new DecimalFormat("0"), new DecimalFormat("0%"));
			plot.setLabelGenerator(gen);
					
			plot.setSimpleLabels(true);
            
			ChartUtilities.writeChartAsPNG(imagem, grafico, 600, 500);
			

			return imagem.toByteArray();
		} catch (Exception e) {
			throw new ErroInternoException("Erro ao gerar grafico (" + e.getMessage() + ")");
		}
	}
	
	
}

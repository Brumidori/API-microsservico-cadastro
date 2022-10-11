package br.com.capgemini.start.service;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.model.Avaliacao;
import br.com.capgemini.start.repository.AvaliacaoRepository;

@Service
public class GraficoService {

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
}

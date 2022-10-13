package br.com.capgemini.start;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class Classe {
	private JFrame janela = new JFrame("Janela Figura");
	private Figura figura = new Figura();
	class Figura extends JPanel {
		private static final long serialVersionUID = 1L;
		private String arquivo = "";
		
		public void setArquivo(String arquivo) {
			this.arquivo = arquivo;
		}
		
		@Override
		public void paintComponent(Graphics g) {
			Image normal = new ImageIcon(arquivo).getImage();

			g.drawImage(normal, 0, 0, this);
		}
	}
	
	private JButton botao = new JButton("Gera Grafico");

	public void go() {
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setSize(590, 500);
		janela.setVisible(true);
		janela.getContentPane().setLayout(null);

		botao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gera();
				figura.setArquivo("grafico.png");
				figura.repaint();
			}
		});
		botao.setBounds(10, 10, 550, 20);
		janela.add(botao);

		figura.setBounds(10, 40, 550, 400);
		janela.add(figura);
	}
	
	public void gera() {
		DefaultPieDataset  ds = new DefaultPieDataset ();
		ds.setValue( "IPhone 5s" , new Double( 20 ) );  
		ds.setValue( "SamSung Grand" , new Double( 20 ) );   
		ds.setValue( "MotoG" , new Double( 40 ) );    
		ds.setValue( "Nokia Lumia" , new Double( 10 ) );  

		JFreeChart grafico = ChartFactory.createPieChart(      
		         "Mobile Sales",   // chart title 
		         ds,          // data    
		         true,             // include legend   
		         true, 
		         false);
		
		PiePlot plot = (PiePlot) grafico.getPlot();
		plot.setSectionPaint("IPhone 5s", Color.green);
		plot.setSectionPaint("SamSung Grand", Color.red);
		plot.setSectionPaint("MotoG", Color.blue);
		plot.setSectionPaint("Nokia Lumia", Color.orange);
		plot.setSimpleLabels(true);
		 
		OutputStream arquivo;
		try {
			arquivo = new FileOutputStream("grafico.png");
			ChartUtilities.writeChartAsPNG(arquivo, grafico, 550, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
			Classe tela = new Classe();
			tela.go();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

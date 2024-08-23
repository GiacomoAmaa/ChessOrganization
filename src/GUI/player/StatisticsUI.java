package GUI.player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import GUI.api.UserInterface;
import board.Board;

public class  StatisticsUI implements UserInterface {
	
	private static final Random RANDOM = new Random();
	private final Dimension formSize = new Dimension(624, 85);
	private final JPanel stats = new JPanel(new GridLayout(0, 2));
	private final JPanel graphs = new JPanel();
	private final JPanel chessBoard = new JPanel(new GridLayout(0, Board.BOARD_DIM));

	
    private static final TreeMap<Double, Color> percentageColorMap = new TreeMap<Double, Color>( 
    		Map.of (1.0, new Color(0, 0, 139), // Blu Scuro
    				2.0, new Color(0, 0, 255), // Blu 
    				3.0, new Color(255, 255, 0), // Giallo
    				4.0, new Color(255, 165, 0), // Arancione
    				Double.MAX_VALUE, new Color(139, 0, 0) // Rosso
    				));

    private final JComboBox<String> searchType =
    		new JComboBox<>(new String[] {"HeatMap", "Elo"});
    
    private ChartPanel eloChart ;

    private List<JButton> squares = new LinkedList<>();
    private final JLabel name = new JLabel("Name:"),
    surname = new JLabel("Surname:"), gamesPlayed = new JLabel("Games Played:"),
    wlRatio = new JLabel("Win/Loss ratio:"), wlWhiteRatio = new JLabel("White pieces W/L:"),
    wlBlackRatio = new JLabel("Black pieces W/L:");
    
    public StatisticsUI(int playerId) {
    	this.stats.add(name);
    	this.stats.add(surname);
    	this.stats.add(gamesPlayed);
    	this.stats.add(wlRatio);
    	this.stats.add(wlWhiteRatio);
    	this.stats.add(wlBlackRatio);
    	this.stats.add(searchType);
    	this.graphs.add(chessBoard);
    	setup();
    }
    
    private void setup() {
    	this.stats.setPreferredSize(formSize);
    	initHeatMap();
    	initCharts();
        searchType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String type = (String) searchType.getSelectedItem();
              	switch(type) {
            	case "Elo":
            		graphs.removeAll();
            		graphs.add(eloChart);
            		graphs.revalidate();
            		graphs.repaint();
            		break;
            	default:
            		graphs.removeAll();
            		graphs.add(chessBoard);
            		graphs.revalidate();
            		graphs.repaint();
            		break;
            	}

            }
        });
        
    }

    private void initCharts() {
        //TODO import series from database
    	this.eloChart = new ChartPanel(ChartFactory.createXYLineChart("Elo", "Score", "Date", createDataset()));
    	this.eloChart.setPreferredSize(new Dimension(624,512));
    }

    private void initHeatMap() {
    	// TODO rimuovere codice per test
    	List<Double> tryheat = new ArrayList<>(64);

        for (int i = 0; i < Board.NUM_TILES; i++) {
            tryheat.add(StatisticsUI.RANDOM.nextDouble() * 6.0);
        }
    	//TODO get info from db
        for (int i = 0; i < Board.NUM_TILES; i++) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(78, 64));
            button.setBackground(percentageColorMap.ceilingEntry(tryheat.get(i)).getValue());
            this.chessBoard.add(button);
            this.squares.add(button);
        }
    }

    private XYDataset createDataset() {
        XYSeries s1 = new XYSeries("Elo");
        s1.add(0, 0);
        s1.add(1, 2);
        s1.add(2, 5);
        s1.add(3, 10);
        s1.add(4, 7);
        s1.add(5, 8);
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s1);
        return dataset;
    }

	@Override
	public JPanel getUpperPanel() {
		return this.stats;
	}

	@Override
	public JPanel getLowerPanel() {
		return this.graphs;
	}

}

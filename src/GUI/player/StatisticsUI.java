package GUI.player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import GUI.api.UserInterface;
import board.Board;
import data.Player;
import model.DBModel;

public class  StatisticsUI implements UserInterface {

	private final Dimension formSize = new Dimension(624, 85);
	private final JPanel stats = new JPanel(new GridLayout(0, 2));
	private final JPanel graphs = new JPanel();
	private final JPanel chessBoard = new JPanel(new GridLayout(0, Board.BOARD_DIM));

	
    private static final TreeMap<Double, Color> percentageColorMap = new TreeMap<Double, Color>( 
    		Map.of (0.01, new Color(0, 0, 139), // Blu Scuro
    				0.02, new Color(0, 0, 255), // Blu 
    				0.03, new Color(255, 255, 0), // Giallo
    				0.04, new Color(255, 165, 0), // Arancione
    				Double.MAX_VALUE, new Color(139, 0, 0) // Rosso
    				));

    private final JComboBox<String> searchType =
    		new JComboBox<>(new String[] {"HeatMap", "Elo"});
    
    private ChartPanel eloChart ;

    private List<JButton> squares = new LinkedList<>();
    private final JLabel name = new JLabel("Name:"),
    surname = new JLabel("Surname:"), elo = new JLabel("Elo:"),
    favOp = new JLabel("Favorite opening:"), favDef = new JLabel("Favorite Defence:"),
    rival = new JLabel("Rival:");
    
    public StatisticsUI(final int playerId) {
        var data = Player.DAO.getStats(DBModel.getConnection(), playerId);
        name.setText(name.getText() + " " + data.get("name"));
        surname.setText(surname.getText() + " " + data.get("lastname"));
        elo.setText(elo.getText() + " " + data.get("elo"));
        favOp.setText(favOp.getText() + " " + data.get("favOp"));
        favDef.setText(favDef.getText() + " " + data.get("favDef"));
        rival.setText(rival.getText() + " " + data.get("rival"));
    	this.stats.add(name);
    	this.stats.add(surname);
    	this.stats.add(elo);
    	this.stats.add(favOp);
    	this.stats.add(favDef);
    	this.stats.add(rival);
    	this.stats.add(searchType);
    	this.graphs.add(chessBoard);
    	setup(playerId, Integer.valueOf(data.get("elo")));
    }
    
    private void setup(final int playerId, final int elo) {
    	this.stats.setPreferredSize(formSize);
    	initHeatMap(playerId, elo);
    	initCharts(playerId, elo);
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

    private void initCharts(final int playerId, final int elo) {
        TimeSeriesCollection dataset = createDataset(playerId, elo);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Elo Trend", "Date", "Score", dataset,
                true, // shows legends
                true, // Use tooltips
                false // doesnt generate URLs
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));
        this.eloChart = new ChartPanel(chart);
        this.eloChart.setPreferredSize(new Dimension(624,512));
    }

    private void initHeatMap(final int playerId, final int elo) {
    	List<Double> heatmap = Player.DAO.getHeatMap(DBModel.getConnection(), playerId);
        for (int i = 0; i < Board.NUM_TILES; i++) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(78, 64));
            button.setBackground(percentageColorMap.ceilingEntry(heatmap.get(i)).getValue());
            this.chessBoard.add(button);
            this.squares.add(button);
        }
    }

    private TimeSeriesCollection createDataset(final int playerId, final int elo) {
        var data = Player.DAO.getTrend(DBModel.getConnection(), playerId, elo);
        TimeSeries series = new TimeSeries("Elo");
        
        data.forEach(x -> {
            LocalDate localDate = x.getY().toLocalDate();
        	series.add(new Day(localDate.getDayOfMonth(), localDate.getMonthValue(),
        			localDate.getYear()), x.getX());
        });

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
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

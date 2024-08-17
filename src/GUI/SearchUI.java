package GUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import GUI.api.UserInterface;
import GUI.player.StatisticsUI;

public class SearchUI implements UserInterface {
	
	public final JPanel panel = new JPanel(new GridLayout(0, 5));
	private final Table table = new Table("");

    private final JComboBox<String> searchType =
    		new JComboBox<>(new String[] {"Players", "Games"});
    
    private static final String[] players = {"Name:   ", "Surname:   "};
    private static final String[] games = {"White:   ", "Black:   "};
    
    private final JLabel label1 = new JLabel(players[0], SwingConstants.RIGHT),
    		label2 = new JLabel(players[1], SwingConstants.RIGHT);

    private final JTextField firstName = new JTextField(),
    		secondName = new JTextField();
	private final JDateChooser firstDate = new JDateChooser(),
			secondDate = new JDateChooser(); 
    
    private final JButton searchButton = new JButton("Search");
    
    public SearchUI() {
        this.panel.add(searchType);
        this.panel.add(label1);
        this.panel.add(firstName);
        this.panel.add(label2);
        this.panel.add(secondName);
        this.panel.add(new JLabel("Since:   ", SwingConstants.RIGHT));
        this.panel.add(firstDate);
        this.panel.add(new JLabel("To:   ", SwingConstants.RIGHT));	
        this.panel.add(secondDate);
        this.panel.add(searchButton);
    	setupForm();
    }

    private void setupForm() {
    	final Date today = new Date();
    	this.secondDate.setMaxSelectableDate(today);
    	this.secondDate.setDate(today);
    	// TODO SearchUI.firstDate.setMinSelectableDate(today); da recuperare dal database
    	this.firstDate.getDateEditor().getUiComponent().setFocusable(false);       
    	this.secondDate.getDateEditor().getUiComponent().setFocusable(false);
    	this.firstDate.setEnabled(false);       
    	this.secondDate.setEnabled(false);
        
        searchType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String type = (String) searchType.getSelectedItem();
                if(type.equals("Players")){
                	label1.setText(players[0]);
                	label2.setText(players[1]);
                	firstDate.setEnabled(false);       
                	secondDate.setEnabled(false);
                    table.setTableModel(type);
                } else {
                	label1.setText(games[0]);
                	label2.setText(games[1]);
                	firstDate.setEnabled(true);       
                	secondDate.setEnabled(true);
                    table.setTableModel(type);
                }
            }
        });
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(searchType.getSelectedItem().equals("Players")){
                    Object[][] data = {
                            {"Anatoly","Karpov", "2850", "10000", "80%"},
                            {"Gary","Kasparov", "2860", "10000", "81%"}
                        };
                    table.clearTable();
                    table.addRows(data, false);
                	//TODO chiama interfaccia con database e passa risultato query a table
                } else {
                    Object[][] data = {
                            {"Player A","Player B", "Player A", "2023-05-20", "Torneo 1"},
                            {"Player C","Player D", "Draw", "2023-06-10", "Torneo 2"},
                            {"Player E","Player F", "Player F", "2023-07-15", "Torneo 3"},
                        };
                    table.clearTable();
                    table.addRows(data, false);
                	//TODO chiama interfaccia con database e passa risultato query a table
                }
            }
        });
        
    	this.table.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int index = table.getSelectedRowIndex();
            	if (index != -1) {
                	UserInterface ui = table.getSearchType().equals("Players") ? new StatisticsUI() : new BoardGUI();

                	table.getLowerPanel().removeAll();
                	table.getLowerPanel().add(ui.getLowerPanel());
                	table.getLowerPanel().revalidate();
                	table.getLowerPanel().repaint();
                	panel.removeAll();
                	panel.setLayout(new FlowLayout());
                	panel.add(ui.getUpperPanel());
                	panel.revalidate();
                	panel.repaint();

            	} else {
            		return;
            	}

            }
        });
    }

	@Override
	public JPanel getUpperPanel() {
		return this.panel;
	}

	@Override
	public JPanel getLowerPanel() {
		return this.table.getLowerPanel();
	}
}

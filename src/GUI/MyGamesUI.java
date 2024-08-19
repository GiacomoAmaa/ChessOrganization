package GUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import GUI.api.UserInterface;
import GUI.player.StatisticsUI;
import GUI.referee.RegisterGameUI;
import util.UserType;

public class MyGamesUI implements UserInterface {
	private final JPanel panel = new JPanel(new GridLayout(0, 4));
	private final Table table = new Table("Games");
    
    private final JLabel label1 = new JLabel("Black:   ", SwingConstants.CENTER),
    		label2 = new JLabel("White:   ", SwingConstants.CENTER);

    private final JTextField firstName = new JTextField(),
    		secondName = new JTextField();
	private final JDateChooser firstDate = new JDateChooser(); 
    
    private final JButton searchButton = new JButton("Search");
    
    public MyGamesUI(UserType user) {
        this.panel.add(label1);
        this.panel.add(firstName);
        this.panel.add(label2);
        this.panel.add(secondName);
        this.panel.add(new JLabel("Since:   ", SwingConstants.CENTER));
        this.panel.add(firstDate);
        this.panel.add(new JLabel(""));
        this.panel.add(searchButton);
    	setupForm(user);
    }

    private void setupForm(UserType user) {
    	final Date today = new Date();
    	this.firstDate.setMaxSelectableDate(today);
    	this.firstDate.setDate(today);
    	// TODO SearchUI.firstDate.setMinSelectableDate(today); da recuperare dal database     
    	this.firstDate.getDateEditor().getUiComponent().setFocusable(false);
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[][] data = {
                        {"Player A","Player B", "Player A", "2023-05-20", "Torneo 1"},
                        {"Player C","Player D", "Draw", "2023-06-10", "Torneo 2"},
                        {"Player E","Player F", "Player F", "2023-07-15", "Torneo 3"},
                    };
                table.clearTable();
                table.addRows(data, false);
            }
        });

    	this.table.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int index = table.getSelectedRowIndex();
            	if (index != -1) {
            		UserInterface ui ;
            		if (user.equals(UserType.PLAYER)) {
            			ui = table.getSearchType().equals("Players") ? new StatisticsUI() : new BoardGUI();
            		} else {
            			ui = new RegisterGameUI(0);
            			//TODO ritornare l'id effettivo del coso selezionato 
            		}
                	panel.removeAll();
                	panel.setLayout(new FlowLayout());
                	panel.add(ui.getUpperPanel());
                	table.getLowerPanel().removeAll();
                	table.getLowerPanel().add(ui.getLowerPanel());
                	table.getLowerPanel().revalidate();
                	table.getLowerPanel().repaint();
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


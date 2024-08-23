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
import data.Game;
import model.DBModel;
import util.UserType;

public class MyGamesUI implements UserInterface {
	private final JPanel panel = new JPanel(new GridLayout(0, 4));
	private final Table table = new Table("Games");
    
    private final JLabel label1 = new JLabel("Opponent Name:   ", SwingConstants.RIGHT),
    		label2 = new JLabel("Opponent Surname:   ", SwingConstants.RIGHT);

    private final JTextField firstName = new JTextField(),
    		secondName = new JTextField();
	private final JDateChooser firstDate = new JDateChooser(); 
    
    private final JButton searchButton = new JButton("Search");
    
    public MyGamesUI(final UserType user, final int playerId) {
        this.panel.add(label1);
        this.panel.add(firstName);
        this.panel.add(label2);
        this.panel.add(secondName);
        this.panel.add(new JLabel("Played Before:   ", SwingConstants.RIGHT));
        this.panel.add(firstDate);
        this.panel.add(new JLabel(""));
        this.panel.add(searchButton);
    	setupForm(user, playerId);
    }

    private void setupForm(final UserType user, final int playerId) {
    	final Date today = new Date();
    	this.firstDate.setMaxSelectableDate(today);
    	this.firstDate.setDate(today);   
    	this.firstDate.getDateEditor().getUiComponent().setFocusable(false);
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.clearTable();
                var list = data.Game.DAO.getGameVsOpponent(DBModel.getConnection(), playerId ,
                		firstName.getText(), firstName.getText(), firstDate.getDate());
                table.addRows(list, true);
            }
        });

    	this.table.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int index = table.getSelectedRowIndex();
            	if (index != -1) {
            		UserInterface ui ;
            		if (user.equals(UserType.PLAYER)) {
            			ui = table.getSearchType().equals("Players")
            					? new StatisticsUI(table.getSelectedRowIndex())
            							: new BoardGUI(Game.DAO.getGameMoves(DBModel.getConnection(), table.getSelectedRowIndex()));
            		} else {
            			ui = new RegisterGameUI(table.getSelectedRowIndex());
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


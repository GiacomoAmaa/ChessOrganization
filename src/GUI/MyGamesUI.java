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
    
    private final JLabel label1, label2;

    private final JTextField firstName = new JTextField(),
    		secondName = new JTextField();
	private final JDateChooser firstDate = new JDateChooser(); 
    
    private final JButton searchButton = new JButton("Search");
    
    public MyGamesUI(final UserType user, final int userId) {
    	this.label1 = new JLabel(user.equals(UserType.PLAYER)
    			? "Opponent Name:   " : "White Player:   ", SwingConstants.RIGHT);
    	this.label2 = new JLabel(user.equals(UserType.PLAYER)
    			? "Opponent Surname:   " : "Black Player:   ", SwingConstants.RIGHT);
        this.panel.add(label1);
        this.panel.add(firstName);
        this.panel.add(label2);
        this.panel.add(secondName);
        this.panel.add(new JLabel("Played Before:   ", SwingConstants.RIGHT));
        this.panel.add(firstDate);
        this.panel.add(new JLabel(""));
        this.panel.add(searchButton);
    	setupForm(user, userId);
    }

    private void setupForm(final UserType user, final int userId) {
    	final Date today = new Date();
    	this.firstDate.setMaxSelectableDate(today);
    	this.firstDate.setDate(today);   
    	this.firstDate.getDateEditor().getUiComponent().setFocusable(false);
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.clearTable();
        		if (user.equals(UserType.PLAYER)) {
                    var list = data.Game.DAO.getGameVsOpponent(DBModel.getConnection(), userId,
                    		firstName.getText(), firstName.getText(), firstDate.getDate());
                    table.addRows(list, true);
        		} else {
        			var list = data.Game.DAO.getGameforReferee(DBModel.getConnection(),
        					userId, firstName.getText(), secondName.getText(), firstDate.getDate());
                    table.addRows(list, true);
        		}

            }
        });

    	this.table.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int index = table.getSelectedRowIndex();
            	if (index != -1) {
            		UserInterface ui;
            		if (user.equals(UserType.PLAYER)) {
            			ui = table.getSearchType().equals("Players")
            					? new StatisticsUI(table.getSelectedRowId())
            							: new BoardGUI(Game.DAO.getGameMoves(DBModel.getConnection(), table.getSelectedRowId()));
            		} else {
            		    ui = new RegisterGameUI(table.getSelectedRowId());
            			
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


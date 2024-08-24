package GUI.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import GUI.Table;
import GUI.api.UserInterface;
import data.Player;
import model.DBModel;

public class LeaderBoardsUI implements UserInterface {
	
	public final JPanel upanel = new JPanel();
	public final JPanel lpanel = new JPanel();

    private final JComboBox<String> searchType =
    		new JComboBox<>(new String[] {"Best players", "Most active", "Best climbers"});
    
    private final Table table = new Table("Best players");
	

	public LeaderBoardsUI() {
		this.upanel.add(searchType);
		this.lpanel.add(table.getLowerPanel());
		setup();
	}

	private void setup() {
        searchType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String type = (String) searchType.getSelectedItem();

              	switch(type) {
            	case "Most active":
            		table.setTableModel(type);
                    table.clearTable();
                    table.addRows(Player.DAO.mostActive(DBModel.getConnection()), true);
            		break;
            	case "Best climbers":
            		table.setTableModel(type);
                    table.clearTable();
                    table.addRows(Player.DAO.bestClimber(DBModel.getConnection()), true);
            		break;
            	default:
            		table.setTableModel(type);
                    table.clearTable();
                    table.addRows(Player.DAO.highestRated(DBModel.getConnection()), true);
            		break;
            	}
            }
        });
        
    	this.table.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int index = table.getSelectedRowIndex();
            	if (index != -1) {
                	UserInterface ui = new StatisticsUI(table.getSelectedRowId());

                	lpanel.removeAll();
                	lpanel.add(ui.getLowerPanel());
                	lpanel.revalidate();
                	lpanel.repaint();
                	upanel.removeAll();
                	upanel.add(ui.getUpperPanel());
                	upanel.revalidate();
                	upanel.repaint();

            	} else {
            		return;
            	}

            }
        });
    }

	@Override
	public JPanel getUpperPanel() {
		return this.upanel;
	}

	@Override
	public JPanel getLowerPanel() {
		return this.lpanel;
	}

}

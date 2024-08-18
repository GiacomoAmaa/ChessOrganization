package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableColumnModel;

public class Table {
	private static final String[] GAMES = {"Select", "White Player", "Black Player", "Winner",
			"Date", "Tournament"};
	private static final String[] PLAYERS = {"Select", "Name", "Surname", "Elo",
			"Games Played", "Win Rate"};
	private static final String[] ANNOUNCES = {"Select", "Name", "Location", "Date", "Capacity"};
    private static final JButton confirm = new JButton("Confirm");
    private static final JButton subscribe = new JButton("Subscribe");
    
	private final JPanel panel =  new JPanel(new BorderLayout()),
			buttons = new JPanel();
	private List<String> ids = new ArrayList<>();
	private String[] columns;
	private String searchType;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public Table(String type) {
    	switch(type) {
    	case "Games":
    		setTableModel(type);
    		break;
    	case "Announces":
    		setTableModel(type);
    		break;
    	case "Tournaments":
    		setTableModel(type);
    		break;
    	default:
    		setTableModel("Players");
    		break;
    	}
    	
    }


	public void setTableModel(String searchType) {
    	this.searchType = searchType;
    	/*if(!searchType.equals("Announces"))
    		this.columns = searchType.equals("Players") ? PLAYERS : GAMES;
    	else {
    		this.columns = ANNOUNCES;
    	}*/
    	this.columns = searchType.equals("Announces") || searchType.equals("Tournaments") ? ANNOUNCES :
    		searchType.equals("Players") ? PLAYERS : GAMES;
    		

        // Create the table model
        tableModel = new DefaultTableModel(columns,0) {

			private static final long serialVersionUID = 1L;

			@Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        // Create the table
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add listener to ensure only one checkbox is selected at a time
        table.getModel().addTableModelListener(e -> {
            if (e.getColumn() == 0) {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if ((Boolean) tableModel.getValueAt(i, 0) && i != e.getFirstRow()) {
                        tableModel.setValueAt(false, i, 0);
                    }
                }
            }
        });

        TableColumnModel columnModel = table.getColumnModel();
        // Adjust column widths
        for (int i = 0; i < columns.length; i++) {
        	if (i == 0) {
        		columnModel.getColumn(i).setPreferredWidth(50);
        	} else {
        		var width = searchType.equals("Announces")||
                		searchType.equals("Tournaments") ? 150 : 100;
                table.getColumnModel().getColumn(i).setPreferredWidth(width);
        	}
        }
        
        this.resetPanel();
    }
	
	/**
	 * Lets the user add custom buttons, an added button cannot be get through
	 * using the metho getButton, since it's a custom component.
	 * @param btn the button to be added
	 */
	public void addButton(JButton btn) {
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttons.add(btn);
	}
 
	private void resetPanel() {
        this.panel.removeAll();
        this.panel.add(table.getTableHeader(), BorderLayout.NORTH);
        this.panel.add(table, BorderLayout.CENTER);
        if(searchType.equals("Announces") ||
        		searchType.equals("Tournaments")) {
        	if (searchType.equals("Announces")) {
        		subscribe.setAlignmentX(Component.CENTER_ALIGNMENT);
        		buttons.add(subscribe);
        	}
        	this.panel.add(buttons, BorderLayout.SOUTH);
        } else if (searchType.equals("Players") || searchType.equals("Players")){
        	this.panel.add(confirm, BorderLayout.SOUTH);
        }
    }

    // Method to get the index of the selected row
    public int getSelectedRowIndex() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((Boolean) tableModel.getValueAt(i, 0)) {
                return i;
            }
        }
        return -1; // Return -1 if no row is selected
    }

    // Method to get the index of the selected row
    public String getSearchType() {
        return this.searchType;
    }
    
    public JButton getButton() {
    	if(searchType.equals("Announces"))
    		return subscribe;
    	return confirm;
    }
       
    // Method to add multiple rows to the table
    public void addRows(Object[][] data, boolean useId) {
    	ids.clear();
    	for (Object[] row : data) {
        	List<Object> tmp = new ArrayList<>();
        	List<Object> tmpRow = new ArrayList<>(Arrays.asList(row));
        	if(useId) {
        		ids.add(tmpRow.get(0).toString());
        		tmpRow.remove(0);
        	}
        	tmp.add(false);
        	tmp.addAll(tmpRow);
        	tableModel.addRow(tmp.toArray());
        }
    }

    // Method to remove all rows from the table
    public void clearTable() {
        tableModel.setRowCount(0);
    }

	public JPanel getLowerPanel() {
		return this.panel;
	}
    
}

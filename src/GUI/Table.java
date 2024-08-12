package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableColumnModel;

public class Table {
	private static final String[] GAMES = {"Select", "White Player", "Black Player", "Winner",
			"Date", "Tournament"};
	private static final String[] PLAYERS = {"Select", "Name", "Surname", "Elo",
			"Games Played", "Win Rate"};
	private static final String[] ANNOUNCES = {"Select", "Name", "Location", "Capacity"};
    private static final JButton confirm = new JButton("Confirm");
    private static final JButton subscribe = new JButton("Subscribe");
    
	private final JPanel panel = new JPanel(new BorderLayout());
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
    	default:
    		setTableModel("Players");
    		break;
    	}
    	
    	confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int index = getSelectedRowIndex();
            	if (index != -1) {
                	String id  = ids.get(index);
                	if(searchType.equals("Players")) {
                		System.out.println(id);
                		// TODO use id and show player stats
                	} else {
                		// TODO use id and show game
                		System.out.println(id);
                	}
            	} else {
            		return;
            	}

            }
        });
    }
    
	public void setTableModel(String searchType) {
    	this.searchType = searchType;
    	if(!searchType.equals("Announces"))
    		this.columns = searchType.equals("Players") ? PLAYERS : GAMES;
    	else {
    		this.columns = ANNOUNCES;
    	}

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
        		var width = searchType.equals("Announces") ? 180 : 100;
                table.getColumnModel().getColumn(i).setPreferredWidth(width);
        	}
        }
        
        this.resetPanel();
    }

	private void resetPanel() {
        this.panel.removeAll();
        this.panel.add(table.getTableHeader(), BorderLayout.NORTH);
        this.panel.add(table, BorderLayout.CENTER);
        if(searchType.equals("Announces")) {
        	this.panel.add(subscribe, BorderLayout.SOUTH);
        } else {
        	this.panel.add(confirm, BorderLayout.SOUTH);
        }
    }

    // Method to get the index of the selected row
    private int getSelectedRowIndex() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((Boolean) tableModel.getValueAt(i, 0)) {
                return i;
            }
        }
        return -1; // Return -1 if no row is selected
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
        	List<Object> tmpRow = new ArrayList<>(List.of(row));
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

	public JPanel getPanel() {
		return panel;
	}
    
}

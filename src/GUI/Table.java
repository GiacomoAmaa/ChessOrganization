package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

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
	private String searchType;
    private JTable table;
    private DefaultTableModel tableModel;
    private static UpdateAgent agent;

    public Table() {
    	setTableModel("Players");
    	
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
    
    public Table(Function<Integer, Boolean> isSubscribed, Function<Integer, Boolean> subscription,
    		List<List<String>> data, List<String> keys) {
    	agent = new UpdateAgent(isSubscribed, keys, data, () -> {
    		return getSelectedRowIndex();
    	});
    	agent.start();
    	subscribe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = getSelectedRowIndex();
				if(index != -1) {
					if(subscription.apply(Integer.valueOf(keys.get(index)))) {
						JOptionPane.showMessageDialog(null, "You succesfully subscribed to the announce");
					}
					JOptionPane.showMessageDialog(null, "An error occurred, subscription failed",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
    		
    	});
    	setPlainDataModel();
    	
    }
    
    private void setPlainDataModel() {
		String[] columns = ANNOUNCES;
		tableModel = new DefaultTableModel(columns, 0) {

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
                table.getColumnModel().getColumn(i).setPreferredWidth(100);
        	}
        }
        
        setUpPanel();
	}

	private void setUpPanel() {
		this.panel.removeAll();
        this.panel.add(table, BorderLayout.CENTER);;
	}

	public void setTableModel(String searchType) {
    	this.searchType = searchType;
    	String[] columns = searchType.equals("Players") ? PLAYERS : GAMES;

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
                table.getColumnModel().getColumn(i).setPreferredWidth(100);
        	}
        }
        
        this.resetPanel();
    }

	private void resetPanel() {
        this.panel.removeAll();
        this.panel.add(table.getTableHeader(), BorderLayout.NORTH);
        this.panel.add(table, BorderLayout.CENTER);
        this.panel.add(confirm, BorderLayout.SOUTH);
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
       
    // Method to add multiple rows to the table
    public void addRows(Object[][] data) {
    	ids.clear();
    	// TODO aggiungere in lista id elementi
        for (Object[] row : data) {
        	ids.add((String) row[1]); // temporary for testing
        	tableModel.addRow(row);
        }
    }

    // Method to remove all rows from the table
    public void clearTable() {
        tableModel.setRowCount(0);
    }

	public JPanel getPanel() {
		return panel;
	}
	
	private static class UpdateAgent extends Thread {
		
		private static Function<Integer, Boolean> check;
		private static List<String> keys;
		private static List<List<String>> data;
		private static boolean flag;
		private static Supplier<Integer> getSelectedRowIndex;
		
		public UpdateAgent(Function<Integer, Boolean> check, List<String> keys,
			List<List<String>> data, Supplier<Integer> getSelectedRowIndex) {
			UpdateAgent.check = check;
			UpdateAgent.keys = keys;
			UpdateAgent.data = data;
			UpdateAgent.getSelectedRowIndex = getSelectedRowIndex;
		}
		
		public void run() {
			while(UpdateAgent.flag) {
				try {
					var index = getSelectedRowIndex.get();
					if(index != -1) {
						int subs = Integer.valueOf(data.get(index).get(3).split("/")[0]),
								max = Integer.valueOf(data.get(index).get(3).split("/")[1]);
						if(check.apply(Integer.valueOf(keys.get(index))) &&
								subs < max) {
							subscribe.setEnabled(true);
						} else {
							subscribe.setEnabled(false);
						}
					}
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					flag = false;
				}
			}
		}
	}
    
}

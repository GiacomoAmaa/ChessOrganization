package GUI;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableColumnModel;

public class GamesTable {
	private static final String[] COLUMNS = {"Select", "White Player", "Black Player", "Winner",
			"Date", "Tournament"};
	private final JPanel scrollPane = new JPanel(new BorderLayout());
    private JTable table;
    private DefaultTableModel tableModel;

    public GamesTable() {
        Object[][] data = {
                {false, "Player A","Player B", "Player A", "2023-05-20", "Torneo 1"},
                {false, "Player C","Player D", "Draw", "2023-06-10", "Torneo 2"},
                {false, "Player E","Player F", "Player F", "2023-07-15", "Torneo 3"},
            };
        // Create the table model
        tableModel = new DefaultTableModel(data,COLUMNS) {

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
        for (int i = 0; i < COLUMNS.length; i++) {
        	if (i == 0) {
        		columnModel.getColumn(i).setPreferredWidth(50);
        	} else {
                table.getColumnModel().getColumn(i).setPreferredWidth(100);
        	}
        }
        scrollPane.add(table.getTableHeader(), BorderLayout.NORTH);
        scrollPane.add(table, BorderLayout.CENTER);
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

	public JPanel getPanel() {
		return scrollPane;
	}
    
    
}


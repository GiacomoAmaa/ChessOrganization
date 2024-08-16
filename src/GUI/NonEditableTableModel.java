package GUI;

import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;

	public NonEditableTableModel(Object[][] data, String[] columnNames) {
	    super(data, columnNames);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
	    return false;
	}
}

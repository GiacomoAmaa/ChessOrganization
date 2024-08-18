package GUI.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GUI.Table;
import GUI.api.UserInterface;

public class TournamentUI implements UserInterface {
	
	private final JPanel upper = new JPanel(),
			lower = new JPanel();
	private Supplier<List<List<String>>> announces;
	private Consumer<Map<String, Object>> createTourn;
	private Consumer<Integer> deleteAnn;
	private final JButton create = new JButton("Create Tournament"),
			delete = new JButton("Delete announce");
	
	public TournamentUI(Supplier<List<List<String>>> announces, Consumer<Map<String, Object>> create,
			Consumer<Integer> delete) {
		this.announces = announces;
		this.createTourn = create;
		this.deleteAnn = delete;
		var data = announces.get();
		var table = new Table("Tournaments");
		var keys = new ArrayList<Integer>();
		if(data.size() != 0) {
			var dataArray = new Object[data.size()][data.get(0).size()];
				data.stream().forEach(e -> {
					e.stream().forEach(elem -> {
						if(e.indexOf(elem) != 4) {
							dataArray[data.indexOf(e)][e.indexOf(elem)] = elem;
						} else {
							keys.add(Integer.valueOf(elem));
						}
				});
			});
			table.addRows(dataArray, false);
		}
		setUp(data, table, keys, create, delete);
		table.addButton(this.create);
		table.addButton(this.delete);
		upper.add(table.getLowerPanel());
		upper.repaint();
	}

	private void setUp(List<List<String>> data, Table table, ArrayList<Integer> keys, Consumer<Map<String, Object>> create, Consumer<Integer> delete) {
		this.create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				var index = table.getSelectedRowIndex();
				if (index != -1) {
					// create should also initialize games and participants.
				}
				JOptionPane.showMessageDialog(null, "No index has been selected.",
						"No index selected", JOptionPane.WARNING_MESSAGE);
			}
		});
		this.delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				var index = table.getSelectedRowIndex();
				if (index != -1) {
					delete.accept(keys.get(index));
					reload();
					JOptionPane.showMessageDialog(null, "You succesfully deleted this announce");
					return;
				}
				JOptionPane.showMessageDialog(null, "No index has been selected.",
						"No index selected", JOptionPane.WARNING_MESSAGE);
			}
			
		});
		
	}
	
	private void reload() {
		upper.removeAll();
		upper.revalidate();
		var data = announces.get();
		var table = new Table("Tournaments");
		var keys = new ArrayList<Integer>();
		if(data.size() != 0) {
			var dataArray = new Object[data.size()][data.get(0).size()];
				data.stream().forEach(e -> {
					e.stream().forEach(elem -> {
						if(e.indexOf(elem) != 4) {
							dataArray[data.indexOf(e)][e.indexOf(elem)] = elem;
						} else {
							keys.add(Integer.valueOf(elem));
						}
				});
			});
			table.addRows(dataArray, false);
		}
		setUp(data, table, keys, createTourn, deleteAnn);
		upper.add(table.getLowerPanel());
		upper.repaint();
	}

	@Override
	public JPanel getUpperPanel() {
		return upper;
	}

	@Override
	public JPanel getLowerPanel() {
		return lower;
	}

}

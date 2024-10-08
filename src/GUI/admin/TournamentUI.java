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
	private Supplier<List<List<Object>>> announces;
	private Consumer<Map<String, Object>> createTourn;
	private Consumer<Integer> deleteAnn;
	private final JButton create = new JButton("Create Tournament"),
			delete = new JButton("Delete announce");
	
	public TournamentUI(Supplier<List<List<Object>>> announces, Consumer<Map<String, Object>> create,
			Consumer<Integer> delete) {
		this.announces = announces;
		this.createTourn = create;
		this.deleteAnn = delete;
		var data = announces.get();
		var table = new Table("Tournaments");
		var keys = new ArrayList<Integer>();
		if(data.size() != 0) {
			var dataArray = new ArrayList<List<Object>>();
				data.stream().forEach(e -> {
					var tmp = new ArrayList<Object>();
					e.stream().forEach(elem -> {
						if(e.indexOf(elem) == 4) {
							keys.add(Integer.valueOf(elem.toString()));
						} else {
							tmp.add(elem);
						}
					});
					dataArray.add(tmp);
				});
			table.addRows(dataArray, false);
		}
		setUp(data, table, keys, create, delete);
		table.addButton(this.create);
		table.addButton(this.delete);
		upper.add(table.getLowerPanel());
		upper.repaint();
	}

	private void setUp(List<List<Object>> data, Table table, ArrayList<Integer> keys, Consumer<Map<String, Object>> create, Consumer<Integer> delete) {
		this.create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				var index = table.getSelectedRowIndex();
				if (index != -1) {
					var row = data.get(index);
					Map<String, Object> map = Map.of("address", row.get(1), "name", row.get(0),
							"numSubs", Integer.valueOf(row.get(3).toString().split("/")[0]),
							"idannounce", keys.get(index));
					create.accept(map);
					JOptionPane.showMessageDialog(null, "The tournament has been succesfully created.");
					return;
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
            var dataArray = new ArrayList<List<Object>>();
                data.stream().forEach(e -> {
                    var tmp = new ArrayList<Object>();
                    e.stream().forEach(elem -> {
                        if(e.indexOf(elem) == 4) {
                            keys.add(Integer.valueOf(elem.toString()));
                        } else {
                            tmp.add(elem);
                        }
                    });
                    dataArray.add(tmp);
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

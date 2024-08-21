package GUI.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GUI.Table;
import GUI.api.UserInterface;

public class TournamentsUI implements UserInterface{
	
	private final JPanel upper = new JPanel(),
			lower = new JPanel();
	private final JButton unsub = new JButton("Unsubscribe");
	
	public TournamentsUI(Supplier<List<List<Object>>> handler, Function<Integer, Boolean> isSub,
			Function<Integer, Boolean> subscribe, Consumer<Integer> unsubscribe) {
		var data = handler.get();
		var table = new Table("Announces");
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
		var btn = table.getButton();
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRowIndex();
				if (index != -1) {
					var subs = Integer.valueOf(data.get(index).get(3).toString().split("/")[0]);
					var max = Integer.valueOf(data.get(index).get(3).toString().split("/")[1]);
					if(!isSub.apply(keys.get(index)) && 
							subs < max) {
						if (subscribe.apply(keys.get(index))) {
							JOptionPane.showMessageDialog(null, "You succesfully subscribed!");
							return;
						}
					} else {
						JOptionPane.showMessageDialog(null, "The announce is already full, or you're"
								+ " already subscribed", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				JOptionPane.showMessageDialog(null, "No index has been selected.",
						"No index selected", JOptionPane.WARNING_MESSAGE);
			}
			
		});
		setUp(table, keys, unsubscribe, isSub);
		table.addButton(unsub);
		upper.add(table.getLowerPanel());
		upper.repaint();
	}

	private void setUp(Table table, List<Integer> keys, Consumer<Integer> unsubscribe,
			Function<Integer, Boolean> isSub) {
		unsub.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				var index = table.getSelectedRowIndex();
				if(index >= 0 ) {
					if(isSub.apply(keys.get(index))) {
						unsubscribe.accept(keys.get(index));
						JOptionPane.showMessageDialog(null, "You succesfully unsubscribed");
						return;
					}
					JOptionPane.showMessageDialog(null, "You are not subscribed to this announce",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(null, "No index has been selected.",
						"No index selected", JOptionPane.WARNING_MESSAGE);
			}
			
		});
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

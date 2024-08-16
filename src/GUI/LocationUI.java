package GUI;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import data.Location;

public class LocationUI implements UserInterface {
	
	private final JPanel north = new JPanel(),
			center = new JPanel();
	
	private final JButton addLocation = new JButton("ADD NEW LOCATION");
	
	private final JTextField newAddress = new JTextField("", 50);
	private final JTextArea description = new JTextArea("", 4, 50);

	
	
	public LocationUI (BiFunction<String, String, Boolean> handler) {
		
		var address = wrapH(List.of(new JLabel("address"), newAddress));
		var desc = wrapH(List.of(new JLabel("description"), description));
		var wrapper = new JPanel(new GridBagLayout());
		var gbc = new GridBagConstraints();
		gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
		wrapper.setAlignmentY(Component.CENTER_ALIGNMENT);
		var list = List.of(address, desc, addLocation);
			list.forEach(e -> {
				e.setAlignmentX(Component.CENTER_ALIGNMENT);
				gbc.gridy = list.indexOf(e);
				wrapper.add(e, gbc);
			});
		north.add(wrapper);
		north.repaint();
		setUp(handler);
	}

	private void setUp(BiFunction<String, String, Boolean> handler) {
		addLocation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!newAddress.getText().equals("") &&
						!Location.DAO.exists(newAddress.getText())) {
					if(handler.apply(newAddress.getText(),
							description.getText())) {
						JOptionPane.showMessageDialog(null, "a new Location has been correctly added");
					}
				} else {
					JOptionPane.showMessageDialog(null, "An error occurred\n- The address field cannot"
							+ "be empty\n- The current location may already exist", "Data error",
							JOptionPane.ERROR_MESSAGE);
				}
				newAddress.setText("");
				description.setText("");
			}
		});
	}

	@Override
	public JPanel getUpperPanel() {
		return this.north;
	}

	@Override
	public JPanel getLowerPanel() {
		return this.center;
	}
	
	/** 
	 * Wraps components into panels containing them, with horizontal disposition on the right.
	 */
	private JComponent wrapH(Collection<JComponent> elements) {
		var wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		elements.stream().forEach(e -> wrapper.add(e));
		return wrapper;
	}

}

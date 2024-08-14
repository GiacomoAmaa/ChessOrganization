package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.BiFunction;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import data.Location;

public class LocationUI implements UserInterface {
	
	private final JPanel north = new JPanel();
	
	private final JButton addLocation = new JButton("ADD NEW LOCATION");
	
	private final JTextField newAddress = new JTextField("", 50);
	private final JTextArea description = new JTextArea("", 4, 50);

	
	
	public LocationUI (BiFunction<String, String, Boolean> handler) {
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
	public JPanel getNorth() {
		return this.north;
	}

	@Override
	public JPanel getCenter() {
		return new JPanel();
	}

}

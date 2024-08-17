package GUI.admin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import GUI.api.UserInterface;
import util.TextPrompt;

public class AddRefereeUI implements UserInterface {
	
	private final int MAX_LENGTH = 30;
	private final JPanel north = new JPanel(new BorderLayout()),
			center = new JPanel();
	
	private DefaultComboBoxModel<String> cbm = new DefaultComboBoxModel<>(new String[] {"---"});
	private final JComboBox<String> address = new JComboBox<>(cbm);
	private final JTextField username = new JTextField(MAX_LENGTH),
			name = new JTextField(MAX_LENGTH),
			lastname = new JTextField(MAX_LENGTH),
			cf = new JTextField(MAX_LENGTH);
	private final JButton register = new JButton("REGISTER");
	private final JPasswordField password = new JPasswordField(MAX_LENGTH);
	
	public AddRefereeUI(Supplier<List<String>> getLocation, Function<Map<String, String>, Integer> addRef) {
		// initializing components
		password.setEchoChar('*');
		// initializing panel
		var nameLab = wrapH(List.of(new JLabel("name\t"), name));
		var lastnameLab = wrapH(List.of(new JLabel("lastname\t"), lastname));
		var cfLab = wrapH(List.of(new JLabel("CF\t"), cf));
		var usernameLab = wrapH(List.of(new JLabel("username\t"), username));
		var passwordLab = wrapH(List.of(new JLabel("password\t"), password));
		var addressLab = wrapH(List.of(new JLabel("location\t"), address));
		var wrapper = new JPanel(new GridBagLayout());
		var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
		wrapper.setAlignmentY(Component.CENTER_ALIGNMENT);
		var list = List.of(nameLab, lastnameLab, cfLab, usernameLab, passwordLab, addressLab, register);
			list.forEach(e -> {
				e.setAlignmentX(Component.CENTER_ALIGNMENT);
				gbc.gridy = list.indexOf(e);
				wrapper.add(e, gbc);
			});
		this.north.add(wrapper);
		this.north.revalidate();
		this.north.repaint();
		setUp(getLocation, addRef);
		
		

	}

	private void setUp(Supplier<List<String>> getLocation, Function<Map<String, String>, Integer> addRef) {
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(List.of(username, name, lastname, cf).stream()
						.allMatch(t -> !t.getText().isEmpty()) &&
						password.getPassword().length != 0 &&
						!address.getSelectedItem().equals("---")) {
					var id = addRef.apply(Map.of("name", name.getText(), "lastname",
							lastname.getText(), "cf", cf.getText(),
							"username", username.getText(), "password",
							String.valueOf(password.getPassword()), "address",
							address.getSelectedItem().toString()));
					if (id > 0) {
						JOptionPane.showMessageDialog(null, "Registration completed, your ID is:  " + id);
					} else {
						JOptionPane.showMessageDialog(null, "Impossible to register this user", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Fields cannot be empty", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				List.of(username, name, lastname, cf, password).stream()
					.forEach(field -> {
						field.setText("");
					});
				address.setSelectedItem("---");
			}

		});
		var choices = getLocation.get();
		choices.add("---");
		this.cbm.removeAllElements();
		this.cbm.addAll(choices);
		
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

package GUI;

import java.awt.Component;
import java.awt.Dimension;
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DefaultEditor;

import com.toedter.calendar.JDateChooser;


public class AnnounceUI implements UserInterface {
	
	private final JPanel north = new JPanel();
	
	private final Dimension dateChooser = new Dimension(305, 20);
	
	private final JButton postAnnounce = new JButton("POST ANNOUNCE");
	
	private final JTextField announceName = new JTextField("", 30);
	
	private final SpinnerNumberModel minModel = new SpinnerNumberModel(2, 2, 32, 1),
			maxModel = new SpinnerNumberModel(32, 2, 32, 1);
	private final JSpinner maxSubs = new JSpinner(maxModel),
			minSubs = new JSpinner(minModel);
	
	private final JDateChooser exprDate = new JDateChooser();
	
	private DefaultComboBoxModel<String> cbm = new DefaultComboBoxModel<>(new String[] {"---"});
	private final JComboBox<String> address = new JComboBox<>(cbm);

	public AnnounceUI(Supplier<List<String>> getLocation, Function<Map<String, Object>, Boolean> postAnn) {
		
		var name = wrapH(List.of(new JLabel("name"), this.announceName));
		var address = wrapH(List.of(new JLabel("address"), this.address));
		var exprDate = wrapH(List.of(new JLabel("expires"), this.exprDate));
		var subs = wrapH(List.of(new JLabel("max. subscriptions"), this.maxSubs,
			new JLabel("min. subscriptions"), this.minSubs));
		var wrapper = new JPanel(new GridBagLayout());
		var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
		wrapper.setAlignmentY(Component.CENTER_ALIGNMENT);
		var list = List.of(name, address, exprDate, subs, this.postAnnounce);
			list.forEach(e -> {
				e.setAlignmentX(Component.CENTER_ALIGNMENT);
				gbc.gridy = list.indexOf(e);
				wrapper.add(e, gbc);
			});
		this.north.add(wrapper);

		setUp(getLocation, postAnn);
		this.north.revalidate();
		this.north.repaint();
	}

	private void setUp(Supplier<List<String>> getLocation, Function<Map<String, Object>, Boolean> postAnn) {
		
		this.exprDate.setPreferredSize(this.dateChooser);
		this.exprDate.setDate(new java.util.Date());
		this.exprDate.setMinSelectableDate(new java.util.Date());
		((DefaultEditor) this.minSubs.getEditor()).getTextField().setEditable(false);
		((DefaultEditor) this.maxSubs.getEditor()).getTextField().setEditable(false);
		this.exprDate.getDateEditor().getUiComponent().setFocusable(false);
		
		this.postAnnounce.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if((int) minSubs.getValue() <= (int) maxSubs.getValue() && !address.getSelectedItem().equals("---") &&
						announceName.getText().isEmpty() && announceName.getText().length() <= 30) {
					
					if(postAnn.apply(Map.of("name", announceName.getText(), "address", address.getSelectedItem(),
							"date", new java.sql.Date(exprDate.getDate().getTime()), "min", (Integer)minSubs.getValue(),
							"max", (Integer)maxSubs.getValue()))) {
						
						JOptionPane.showMessageDialog(null, "Announce succesfully posted");
					} else {
						JOptionPane.showMessageDialog(null, "The announce can't be posted",
								"Error occurred", JOptionPane.ERROR_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(null, "Check the correct pattern of a post:\n"
							+ "- name cannot be longer than 30 characters\n"
							+ "- min must be lower/equal than max subscribers", "Pattern errors",
							JOptionPane.ERROR_MESSAGE);
				}

				announceName.setText("");
				minSubs.setValue(2);
				maxSubs.setValue(32);
				address.setSelectedIndex(-1);
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
		return new JPanel(); 
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

package guitry;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Collection;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.UserType;

public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final int MAX_LENGTH = 30;
	private static final JPanel panel = new JPanel(new BorderLayout());
	private static final JLabel title = new JLabel("Chess Org");
	private static final JTextField username = new JTextField(GUI.MAX_LENGTH),
			cardNumber = new JTextField(GUI.MAX_LENGTH);
	private static final JComboBox<UserType> userChoice = new JComboBox<>(UserType.values());
	private static final JButton confirmType = new JButton("OK"), login = new JButton("LOG IN");
	
	private static final JPasswordField password = new JPasswordField(GUI.MAX_LENGTH);
	
	public GUI() {
		super("Chess Organization");
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(GUI.panel);
		initialize();
		setVisible(true);
	}

	private void initialize() {
		GUI.panel.setAlignmentX(LEFT_ALIGNMENT);
		GUI.password.setEchoChar('*');
		//
		GUI.title.setFont(new Font("Serif", Font.BOLD, 36));
		GUI.title.setHorizontalAlignment(JLabel.CENTER);
		GUI.panel.add(title, BorderLayout.NORTH);
		//
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(wrap(List.of(new JLabel("login as"), userChoice, confirmType)));
		centerPanel.add(wrap(List.of(new JLabel("username\t"), GUI.username)));
		centerPanel.add(wrap(List.of(new JLabel("password\t"), GUI.password)));
		centerPanel.add(wrap(List.of(new JLabel("card number\t"), GUI.cardNumber)));
		centerPanel.add(wrap(List.of(GUI.login)));
		GUI.panel.add(centerPanel, BorderLayout.CENTER);
		pack();
	}
	
	private JComponent wrap(Collection<JComponent> elements) {
		var wrapper = new JPanel();
		elements.stream().forEach(e -> wrapper.add(e));
		return wrapper;
	}
	
}

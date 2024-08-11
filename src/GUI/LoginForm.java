package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.swing.ImageIcon;

import util.Pair;
import util.TextPrompt;
import util.UserType;
import util.loaders.FontLoader;

public class LoginForm extends JFrame {
	
	private static final long serialVersionUID = 1L,
			RENDER = 50;
	private static final int MAX_LENGTH = 30;
	private static final int FONT_SIZE = 36;
	private static final int ERROR_SIZE = 15;
	private static final JPanel panel = new JPanel(new BorderLayout());
	private static final JLabel title = new JLabel("Chess Org");
	private static final JTextField username = new JTextField(LoginForm.MAX_LENGTH),
			name = new JTextField(LoginForm.MAX_LENGTH),
			lastname = new JTextField(LoginForm.MAX_LENGTH),
			cf = new JTextField(LoginForm.MAX_LENGTH),
			cardNumber = new JTextField(LoginForm.MAX_LENGTH);
	private static final JComboBox<UserType> role = new JComboBox<>(UserType.values());
	private static final JButton login = new JButton("LOG IN"),
			register = new JButton("REGISTER");
	private static final JPasswordField password = new JPasswordField(LoginForm.MAX_LENGTH);
	private static final ImageIcon logo = new ImageIcon(LoginForm.class.getResource("/icons/logo.png"));
	private static final UpdateAgent agent = new UpdateAgent();
	private static final FontLoader fontload = new FontLoader();
	
	public LoginForm() {
		super("Chess Organization");
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(LoginForm.panel);
		setIconImage(LoginForm.logo.getImage());
		initialize();
		setLocationRelativeTo(null);
		updateType();
		setVisible(true);
	}
	
	public void setRegisterHandler(Consumer<Map<String, String>> handler) {
		LoginForm.register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handler.accept(getRegisterData());
			}
			
		});
	}
	
	public void setLogInHandler(Consumer<Pair<UserType, Map<String, String>>> handler) {
		LoginForm.login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handler.accept(new Pair<UserType, Map<String, String>>((UserType)LoginForm.role.getSelectedItem(), getLoginData()));
			}
			
		});
	}
	
	/**
	 * shows an error and resets fields when log in trial goes wrong
	 */
	public void Error() {
		LoginForm.username.setText("");
		LoginForm.password.setText("");
		LoginForm.name.setText("");
		LoginForm.lastname.setText("");
		LoginForm.cf.setText("");
		updateType();
		JOptionPane.showMessageDialog(null, "Username or password are not valid, please try again.");
	}

	/**
	 * shows a registration error
	 */
	public void missingData() {
		LoginForm.username.setText("");
		LoginForm.password.setText("");
		LoginForm.name.setText("");
		LoginForm.lastname.setText("");
		LoginForm.cf.setText("");
		updateType();
		JOptionPane.showMessageDialog(null,
			"This data are required in order to register\n\t"
			+ "- name\n\t- lastname\n\t- cf\n\t- username\n\t"
			+ "password\nSome data is missing!"
		);
	}
	
	public void alreadyExist() {
		LoginForm.username.setText("");
		LoginForm.password.setText("");
		LoginForm.name.setText("");
		LoginForm.lastname.setText("");
		LoginForm.cf.setText("");
		updateType();
		JOptionPane.showMessageDialog(null, "This player already exists, please try again.");
	}	

	/**
	 * Sets up the frame.
	 */
	private void initialize() {
		// TODO import font for labels
		// initializing the update thread
		LoginForm.agent.start();
		// initializing components
		LoginForm.password.setEchoChar('*');
		//LoginForm.error.setVisible();
		// initializing northern panel
		LoginForm.title.setFont(new Font("Serif", Font.BOLD, LoginForm.FONT_SIZE));
		LoginForm.title.setHorizontalAlignment(JLabel.CENTER);
		LoginForm.panel.add(wrap(List.of(LoginForm.title, new JLabel(LoginForm.logo))), BorderLayout.NORTH);
		// initializing center panel
		JPanel centerPanel = new JPanel();
		List.of(LoginForm.name, LoginForm.lastname, LoginForm.cf).stream().forEach((text) -> {
			new TextPrompt("just for registration", text);
		});
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(wrap(List.of(new JLabel("submit as\t"), LoginForm.role)));
		centerPanel.add(wrapH(List.of(new JLabel("name\t"), LoginForm.name)));
		centerPanel.add(wrapH(List.of(new JLabel("lastname\t"), LoginForm.lastname)));
		centerPanel.add(wrapH(List.of(new JLabel("CF\t"), LoginForm.cf)));
		centerPanel.add(wrapH(List.of(new JLabel("username\t"), LoginForm.username)));
		centerPanel.add(wrapH(List.of(new JLabel("password\t"), LoginForm.password)));
		centerPanel.add(wrapH(List.of(new JLabel("card num.\t"), LoginForm.cardNumber)));
		LoginForm.panel.add(centerPanel, BorderLayout.CENTER);
		// initializing southern panel
		JPanel southernPanel = new JPanel();
		southernPanel.setLayout(new BoxLayout(southernPanel, BoxLayout.Y_AXIS));
		southernPanel.add(wrap(List.of(login, register)));
		LoginForm.panel.add(southernPanel, BorderLayout.SOUTH);
		// packing frame
		pack();
	}
	
	/** 
	 * Wraps components into panels containing them.
	 */
	private JComponent wrap(Collection<JComponent> elements) {
		var wrapper = new JPanel();
		elements.stream().forEach(e -> wrapper.add(e));
		return wrapper;
	}
	
	/** 
	 * Wraps components into panels containing them, with horizontal disposition on the right.
	 */
	private JComponent wrapH(Collection<JComponent> elements) {
		var wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		elements.stream().forEach(e -> wrapper.add(e));
		return wrapper;
	}
	
	/**
	 * Updates the login form based on the type of user chosen.
	 */
	private static void updateType() {
		var selectedType = (UserType)LoginForm.role.getSelectedItem();
		LoginForm.cardNumber.setEnabled(selectedType.equals(UserType.REFEREE));
		LoginForm.cardNumber.setText(selectedType.equals(UserType.REFEREE) ? "" : "----");
		LoginForm.register.setEnabled(selectedType.equals(UserType.PLAYER));
		LoginForm.name.setEnabled(selectedType.equals(UserType.PLAYER));
		LoginForm.lastname.setEnabled(selectedType.equals(UserType.PLAYER));
		LoginForm.cf.setEnabled(selectedType.equals(UserType.PLAYER));
	}
	
	private Map<String, String> getLoginData() {
		if(((UserType)LoginForm.role.getSelectedItem()).equals(UserType.REFEREE)) {
			return Map.of("username", LoginForm.username.getText(),
				"password", String.valueOf(LoginForm.password.getPassword()),
				"cardNumber", LoginForm.cardNumber.getText());
		}
		else {
			return Map.of("username", LoginForm.username.getText(),
					"password", String.valueOf(LoginForm.password.getPassword()));
		}
	}
	
	private Map<String, String> getRegisterData() {
		if (List.of(LoginForm.name, LoginForm.lastname, LoginForm.cf, LoginForm.username, LoginForm.password).stream()
			.map(t -> t.getText())
			.allMatch(s -> s.length() != 0)
		) {
			return Map.of("name", LoginForm.name.getText(),"lastname", LoginForm.lastname.getText(),
					"cf", LoginForm.cf.getText(), "username", LoginForm.username.getText(),
					"password", String.valueOf(LoginForm.password.getPassword()));
		}
		return Map.of();
	}
	
	public void close() {
		LoginForm.agent.interrupt();
		setVisible(false);
	}
	
	/**
	 * need a thread to refresh the GUI every time the type gets changed.
	 */
	private static class UpdateAgent extends Thread {
		
		private static boolean flag = true;

		public UpdateAgent() {
			super();
		}

		@Override
		public void run() {
			while(UpdateAgent.flag) {
				try {
					LoginForm.updateType();
					TimeUnit.MILLISECONDS.sleep(RENDER);
				} catch (InterruptedException e) {
					flag = false;
				}
			}
		}		
	}
}

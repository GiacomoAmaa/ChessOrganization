package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.IDateEditor;
import com.toedter.calendar.JDateChooser;

import util.loaders.FontLoader;

public class AdminUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final Dimension screen = new Dimension(700, 700),
			dateChooser = new Dimension(305, 20);
	private static final double PADDING = 0.067;
	private static final float TEXT_SIZE = 15,
			TITLE_SIZE = 18;
	private static final JPanel panel = new JPanel(new BorderLayout()),
			// centerPane represents the center section of pane
			centerPane = new JPanel();
	private static final ImageIcon logo = new ImageIcon(AdminUI.class.getResource("/icons/logo.png")),
			defIcon = new ImageIcon(AdminUI.class.getResource("/icons/default.png"));
	private static final JLabel defText = new JLabel("Welcome to Chess Org");
	private static final JMenuBar menu = new JMenuBar();
	private static final JMenu search = new JMenu("Search"),
			tourn = new JMenu("Post announce"),
			logout = new JMenu("Logout");
	private static final JMenuItem searchPlayers = new JMenuItem("Search players"),
			searchGames = new JMenuItem("Search games");
	private static final JButton launchSearch = new JButton(new ImageIcon(AdminUI.class.getResource("/icons/magnifying-glass.png"))),
			postAnnounce = new JButton("POST ANNOUNCE");
	private static final JTextField searchBox = new JTextField("", 50),
			address = new JTextField("", 30);
	//private static final SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	//private static final JFormattedTextField exprDate = new JFormattedTextField(df);
	private static final JDateChooser exprDate = new JDateChooser(); 
	private static final SpinnerNumberModel minModel = new SpinnerNumberModel(2, 2, 32, 1),
			maxModel = new SpinnerNumberModel(32, 2, 32, 1);
	private static final JSpinner maxSubs = new JSpinner(AdminUI.maxModel),
			minSubs = new JSpinner(AdminUI.minModel);
	private static final FontLoader fontLoad = new FontLoader();
	private static Optional<JMenu> selected = Optional.empty();
	
	public AdminUI() {
		super("Chess Organization");
		setSize(AdminUI.screen);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(AdminUI.panel);
		setIconImage(AdminUI.logo.getImage());
		initialize();
		setVisible(true);
	}
	
	public void setSearchHandler(Function<String, List<String>> handler) {
		AdminUI.launchSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				var result = handler.apply(AdminUI.searchBox.getText());
				// show results
			}
			
		});
	}
	
	public void setPostHandler(Function<Map<String, Object>, Boolean> handler) {
		AdminUI.postAnnounce.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					var date = Optional.ofNullable(AdminUI.exprDate.getDate());
					if (date.isEmpty()) {
						AdminUI.minSubs.setValue(2);
						AdminUI.maxSubs.setValue(32);
						AdminUI.address.setText("");
						JOptionPane.showMessageDialog(null, "A date must be set",
								"Date Error", JOptionPane.ERROR_MESSAGE);
					}
				if((int) AdminUI.minSubs.getValue() <= (int) AdminUI.maxSubs.getValue() &&
						AdminUI.exprDate.getDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDateTime().compareTo(LocalDateTime.now())>= 0 &&
						!AdminUI.address.getText().equals("")) {
					if(handler.apply(Map.of("address", AdminUI.address.getText(),
							"date", new java.sql.Date(AdminUI.exprDate.getDate().getTime()),
							"min", (Integer)AdminUI.minSubs.getValue(),
							"max", (Integer)AdminUI.maxSubs.getValue()))) {
						AdminUI.minSubs.setValue(2);
						AdminUI.maxSubs.setValue(32);
						AdminUI.address.setText("");
						JOptionPane.showMessageDialog(null, "Announce succesfully posted");
					}
					JOptionPane.showMessageDialog(null, "Check the correct pattern of a post:\n"
							+ "- address cannot be empty\n- date cannot be past\n"
							+ "- min must be lower/equal than max subscribers"
							+ "- subscribers number can only be integers", "Pattern errors",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
	}
	
	private void initialize() {
		// initializing components
		setHandler(AdminUI.tourn, () -> {
			loadTournaments();
			AdminUI.selected = Optional.of(tourn);
			update();
		});
		setHandler(AdminUI.logout, () -> {
			AdminUI.selected = Optional.of(AdminUI.logout);
			update();
			int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?","WARNING",JOptionPane.YES_NO_OPTION);

			if(dialogButton == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		});
		AdminUI.searchGames.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO should be a different method
				loadSearch();
				AdminUI.selected = Optional.of(search);
				update();
			}
			
		});
		AdminUI.searchPlayers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadSearch();
				AdminUI.selected = Optional.of(search);
				update();
			}
			
		});
		// making components not editable by keyboard
		AdminUI.exprDate.setPreferredSize(AdminUI.dateChooser);
		AdminUI.exprDate.setDate(new Date());
		AdminUI.exprDate.setMinSelectableDate(new Date());
		JComponent editor = AdminUI.minSubs.getEditor();
        JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
        textField.setEditable(false);
        editor = AdminUI.maxSubs.getEditor();
        textField = ((JSpinner.DefaultEditor) editor).getTextField();
        textField.setEditable(false);
        var comp = (IDateEditor)AdminUI.exprDate.getDateEditor().getUiComponent();
        ((Component) comp).setFocusable(false);
		AdminUI.postAnnounce.setAlignmentX(Component.CENTER_ALIGNMENT);
		// adding every menu item to its corresponding menu
		AdminUI.search.add(AdminUI.searchPlayers);
		AdminUI.search.add(AdminUI.searchGames);
		// inserting centerPane into pane
		AdminUI.panel.add(centerPane, BorderLayout.CENTER);
		// TODO il font non carica
		AdminUI.defText.setFont(AdminUI.fontLoad.getTextFont().deriveFont(AdminUI.TEXT_SIZE));
		// initializing northern panel
		List.of(tourn, search, logout, searchPlayers, searchGames).stream()
			.forEach(elem -> {
				if (elem instanceof JMenu) {
					AdminUI.menu.add(elem);
				}
				elem.setFont(AdminUI.fontLoad.getTitleFont().deriveFont(AdminUI.TITLE_SIZE));
			});
		AdminUI.panel.add(AdminUI.menu, BorderLayout.NORTH);
		// initializing center panel
		AdminUI.centerPane.add(wrapV(List.of(AdminUI.defText, new JLabel(AdminUI.defIcon))), BorderLayout.CENTER);
		//final BoardGUI board= new BoardGUI(new Game(List.of(new Pair<>("",""))));
		//UserUI.panel.add(board.getGui(), BorderLayout.CENTER);
	}
	
	private void loadTournaments() {
		AdminUI.centerPane.removeAll();
		AdminUI.centerPane.revalidate();
		var address = wrapH(List.of(new JLabel("address"), AdminUI.address));
		var exprDate = wrapH(List.of(new JLabel("expires"), AdminUI.exprDate));
		var subs = wrapH(List.of(new JLabel("max. subscriptions"), AdminUI.maxSubs,
			new JLabel("min. subscriptions"), AdminUI.minSubs));
		var wrapper = new JPanel(new GridBagLayout());
		var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
		wrapper.setAlignmentY(Component.CENTER_ALIGNMENT);
		var list = List.of(address, exprDate, subs, AdminUI.postAnnounce);
			list.forEach(e -> {
				e.setAlignmentX(CENTER_ALIGNMENT);
				gbc.gridy = list.indexOf(e);
				wrapper.add(e, gbc);
			});
		AdminUI.centerPane.add(wrapper);
		AdminUI.centerPane.repaint();
	}

	private void loadSearch() {
		AdminUI.centerPane.removeAll();
		AdminUI.centerPane.revalidate();
		var wrapper = new JPanel();
		wrapper.add(AdminUI.searchBox);
		wrapper.add(AdminUI.launchSearch);
		wrapper.setAlignmentX(CENTER_ALIGNMENT);
		AdminUI.centerPane.add(wrapper);
		AdminUI.centerPane.repaint();
	}
	
	private void update() {
		List.of(AdminUI.search, AdminUI.tourn).stream()
			.forEach(btn -> {
				if(AdminUI.selected.equals(Optional.of(btn))) {
					btn.setForeground(Color.BLUE);
				} else {
					btn.setForeground(Color.BLACK);
				}
			});
	}
	
	/** 
	 * Wraps components into vertical panels containing them.
	 */
	private static JComponent wrapV(Collection<JComponent> elements) {
		elements.stream()
			.forEach(e -> {
				e.setAlignmentX(CENTER_ALIGNMENT);
				e.setBorder(new EmptyBorder((int)(AdminUI.screen.height * AdminUI.PADDING),
						(int)(AdminUI.screen.height * AdminUI.PADDING),
						(int)(AdminUI.screen.height * AdminUI.PADDING),
						(int)(AdminUI.screen.height * AdminUI.PADDING)));
				});
		var wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		wrapper.setAlignmentX(CENTER_ALIGNMENT);
		wrapper.setAlignmentY(CENTER_ALIGNMENT);
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
	
	private void setHandler(JComponent comp, Runnable handler) {
		comp.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				handler.run();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				return;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				return;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				return;
			}

			@Override
			public void mouseExited(MouseEvent e) {
				return;
			}
			
		});
	}

}

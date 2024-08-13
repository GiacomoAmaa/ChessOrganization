package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.PlayerControllerImpl;
import GUI.Table;
import data.Announce;
import util.loaders.FontLoader;

public class PlayerUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private static final Dimension screen = new Dimension(700, 700);
	private static final double PADDING = 0.067;
	private static final float TEXT_SIZE = 15,
			TITLE_SIZE = 18;
	private static final JPanel panel = new JPanel(new BorderLayout()),
			// centerPane represents the center section of pane
			centerPane = new JPanel();
	private static final ImageIcon logo = new ImageIcon(PlayerUI.class.getResource("/icons/logo.png")),
			defIcon = new ImageIcon(PlayerUI.class.getResource("/icons/default.png"));
	private static final JLabel defText = new JLabel("Welcome to Chess Org");
	private static final JMenuBar menu = new JMenuBar();
	private static final JMenu games = new JMenu("My Games"),
			stats = new JMenu("My stats"),
			search = new JMenu("Search"),
			tourn = new JMenu("Sign in for tournaments"),
			logout = new JMenu("Logout");
	private static final JMenuItem searchPlayers = new JMenuItem("Search players"),
			searchGames = new JMenuItem("Search games");
	private static final JButton launchSearch = new JButton(new ImageIcon(PlayerUI.class.getResource("/icons/magnifying-glass.png")));
	private static final JTextField searchBox = new JTextField("", 50);
	private static final FontLoader fontLoad = new FontLoader();
	private static Optional<JMenu> selected = Optional.empty();
	
	public PlayerUI() {
		super("Chess Organization");
		setSize(PlayerUI.screen);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(PlayerUI.panel);
		setIconImage(PlayerUI.logo.getImage());
		initialize();
		setVisible(true);
	}
	
	public void setSearchHandler(Function<String, List<String>> handler) {
		PlayerUI.launchSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				var result = handler.apply(PlayerUI.searchBox.getText());
				// show results
			}
			
		});
	}
	
	public void setStatsHandler(Supplier<Map<String, Number>> handler) {
		PlayerUI.stats.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PlayerUI.selected = Optional.of(stats);
				update();
				loadStats(handler.get());
			}
			
		});
		
	}
	
	public void setTournamentsHandler(Supplier<List<List<String>>> handler, Function<Integer, Boolean> isSub,
			Function<Integer, Boolean> subscribe) {
		setHandler(PlayerUI.tourn, () -> {
			loadTournaments(handler.get(), isSub, subscribe);
			PlayerUI.selected = Optional.of(tourn);
			update();
		});
	}
	
	public void close() {
		setVisible(false);
	}
	
	private void initialize() {
		// initializing components
		setHandler(PlayerUI.games, () -> {
			loadGames();
			PlayerUI.selected = Optional.of(PlayerUI.games);
			update();
		});
		setHandler(PlayerUI.logout, () -> {
			PlayerUI.selected = Optional.of(PlayerUI.logout);
			update();
			int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?","WARNING",JOptionPane.YES_NO_OPTION);

			if(dialogButton == JOptionPane.YES_OPTION) {
				close();
				System.exit(0);
			}
		});
		PlayerUI.searchGames.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO should be a different method
				loadSearch();
				PlayerUI.selected = Optional.of(search);
				update();
			}
			
		});
		PlayerUI.searchPlayers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadSearch();
				PlayerUI.selected = Optional.of(search);
				update();
			}
			
		});
		// adding every menu item to its corresponding menu
		PlayerUI.search.add(PlayerUI.searchPlayers);
		PlayerUI.search.add(PlayerUI.searchGames);
		// inserting centerPane into pane
		PlayerUI.panel.add(centerPane, BorderLayout.CENTER);
		// TODO il font non carica
		PlayerUI.defText.setFont(PlayerUI.fontLoad.getTextFont().deriveFont(PlayerUI.TEXT_SIZE));
		// initializing northern panel
		List.of(games, stats, tourn, search, logout, searchPlayers, searchGames).stream()
			.forEach(elem -> {
				if (elem instanceof JMenu) {
					PlayerUI.menu.add(elem);
				}
				elem.setFont(PlayerUI.fontLoad.getTitleFont().deriveFont(PlayerUI.TITLE_SIZE));
			});
		PlayerUI.panel.add(PlayerUI.menu, BorderLayout.NORTH);
		// initializing center panel
		PlayerUI.centerPane.add(wrapV(List.of(PlayerUI.defText, new JLabel(PlayerUI.defIcon))), BorderLayout.CENTER);
		//final BoardGUI board= new BoardGUI(new Game(List.of(new Pair<>("",""))));
		//UserUI.panel.add(board.getGui(), BorderLayout.CENTER);
	}
	
	private void update() {
		List.of(PlayerUI.games, PlayerUI.search, PlayerUI.tourn, PlayerUI.stats).stream()
			.forEach(btn -> {
				if(PlayerUI.selected.equals(Optional.of(btn))) {
					btn.setForeground(Color.BLUE);
				} else {
					btn.setForeground(Color.BLACK);
				}
			});
	}
	
	private void loadStats(Map<String, Number> data) {
		// TODO implementation
	}

	private void loadGames() {
		//final BoardGUI board= new BoardGUI(new Game(List.of(new Pair<>("P:e2:::e4","P:e7:#::e5"))));
		final RegisterGameUI form= new RegisterGameUI();
		PlayerUI.centerPane.removeAll();
		PlayerUI.centerPane.revalidate();
		//UserUI.centerPane.add(board.getBoard());
		/*
		 * Samu: il codice qui sotto l'ho aggiunto io per prova
		 */
		var wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		wrapper.setAlignmentX(CENTER_ALIGNMENT);
		wrapper.setAlignmentY(CENTER_ALIGNMENT);
		wrapper.add(form.getPanel());
		wrapper.add(form.getBoard());
		PlayerUI.centerPane.add(wrapper);
		/*
		 * Samu: non occupare la parte sud del del pannello, è più difficile da ripulire
		 * metti tutto al centro con un wrapper, il tuo codice è commentato qui sotto.
		 * 
		 * PlayerUI.centerPane.add(form.getPanel());
		 * PlayerUI.panel.add(form.getBoard(),BorderLayout.SOUTH);
		 */
		PlayerUI.centerPane.repaint();
		//UserUI.panel.add(board.getRightSidebar(),BorderLayout.WEST);
		//UserUI.panel.add(board.getLeftSidebar(),BorderLayout.EAST);
		//UserUI.panel.add(board.getFooter(),BorderLayout.SOUTH);
		//pack();
	}
	
	private void loadTournaments(List<List<String>> data, Function<Integer, Boolean> isSub,
			Function<Integer, Boolean> subscribe) {
		PlayerUI.centerPane.removeAll();
		PlayerUI.centerPane.revalidate();
		var table = new Table("Announces");
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
		var btn = table.getButton();
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRowIndex();
				if (index != -1) {
					var subs = Integer.valueOf(data.get(index).get(3).split("/")[0]);
					var max = Integer.valueOf(data.get(index).get(3).split("/")[1]);
					if(!isSub.apply(keys.get(index)) && 
							subs <  max) {
						if (subscribe.apply(keys.get(index)))
							JOptionPane.showMessageDialog(null, "You succesfully subscribed!");
					} else {
						JOptionPane.showMessageDialog(null, "The announce is already full, or you're"
								+ " already subscribed", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			
		});
		PlayerUI.centerPane.add(table.getPanel());
		repaint();
	}

	private void loadSearch() {
		PlayerUI.centerPane.removeAll();
		PlayerUI.centerPane.revalidate();
		var wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		final SearchUI form= new SearchUI();
		wrapper.add(form.getPanel());
		wrapper.add(form.getBoard());
		wrapper.setAlignmentX(CENTER_ALIGNMENT);
		PlayerUI.centerPane.add(wrapper);
		PlayerUI.centerPane.repaint();

	}
	
	/** 
	 * Wraps components into vertical panels containing them.
	 */
	private static JComponent wrapV(Collection<JComponent> elements) {
		elements.stream()
			.forEach(e -> {
				e.setAlignmentX(CENTER_ALIGNMENT);
				e.setBorder(new EmptyBorder((int)(PlayerUI.screen.height * PlayerUI.PADDING),
						(int)(PlayerUI.screen.height * PlayerUI.PADDING),
						(int)(PlayerUI.screen.height * PlayerUI.PADDING),
						(int)(PlayerUI.screen.height * PlayerUI.PADDING)));
				});
		var wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		wrapper.setAlignmentX(CENTER_ALIGNMENT);
		wrapper.setAlignmentY(CENTER_ALIGNMENT);
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

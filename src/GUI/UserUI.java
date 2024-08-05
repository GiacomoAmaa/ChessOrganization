package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import util.loaders.FontLoader;

public class UserUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private static final Dimension screen = new Dimension(700, 700);
	private static final double PADDING = 0.067;
	private static final float FONT_SIZE = 15;
	private static final JPanel panel = new JPanel(new BorderLayout()),
			// centerPane represents the center section of pane
			centerPane = new JPanel();
	private static final ImageIcon logo = new ImageIcon(UserUI.class.getResource("/icons/logo.png")),
			defIcon = new ImageIcon(UserUI.class.getResource("/icons/default.png"));
	private static final JLabel defText = new JLabel("Welcome to Chess Org");
	private static final JMenuBar menu = new JMenuBar();
	private static final JMenu games = new JMenu("MyGames"),
			stats = new JMenu("My stats"),
			search = new JMenu("Search"),
			tourn = new JMenu("Sign in for tournaments"),
			logout = new JMenu("Logout");
	private static final JMenuItem searchPlayers = new JMenuItem("Search players"),
			searchGames = new JMenuItem("Search games");
	private static final JButton launchSearch = new JButton(new ImageIcon(UserUI.class.getResource("/icons/magnifying-glass.png")));
	private static final JTextField searchBox = new JTextField("", 50);
	private static final FontLoader fontLoad = new FontLoader();
	private static Optional<JMenu> selected = Optional.empty();
	
	public UserUI() {
		super("Chess Organization");
		setSize(UserUI.screen);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(UserUI.panel);
		setIconImage(UserUI.logo.getImage());
		initialize();
		setVisible(true);
	}
	
	public void setSearchHandler(Function<String, List<String>> handler) {
		UserUI.launchSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				var result = handler.apply(UserUI.searchBox.getText());
				// show results
			}
			
		});
	}
	
	private void initialize() {
		// initializing components
		UserUI.games.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				loadGames();
				UserUI.selected = Optional.of(games);
				update();
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
		UserUI.stats.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				loadStats();
				UserUI.selected = Optional.of(stats);
				update();
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
		UserUI.tourn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				loadTournaments();
				UserUI.selected = Optional.of(tourn);
				update();
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
		UserUI.searchPlayers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadSearch();
				UserUI.selected = Optional.of(search);
				update();
			}
			
		});
		UserUI.searchGames.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO should be a different method
				loadSearch();
				UserUI.selected = Optional.of(search);
				update();
			}
			
		});
		UserUI.logout.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?","WARNING",JOptionPane.YES_NO_OPTION);

				if(dialogButton == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
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
		// adding every menu item to its corresponding menu
		UserUI.search.add(UserUI.searchPlayers);
		UserUI.search.add(UserUI.searchGames);
		// inserting centerPane into pane
		UserUI.panel.add(centerPane, BorderLayout.CENTER);
		// TODO il font non carica
		UserUI.defText.setFont(UserUI.fontLoad.getTextFont().deriveFont(UserUI.FONT_SIZE));
		// initializing northern panel
		List.of(games, stats, tourn, search, logout).stream()
			.forEach(elem -> {
				UserUI.menu.add(elem);
			});
		UserUI.panel.add(UserUI.menu, BorderLayout.NORTH);
		// initializing center panel
		UserUI.centerPane.add(wrapV(List.of(UserUI.defText, new JLabel(UserUI.defIcon))), BorderLayout.CENTER);
		//final BoardGUI board= new BoardGUI(new Game(List.of(new Pair<>("",""))));
		//UserUI.panel.add(board.getGui(), BorderLayout.CENTER);
	}
	
	private void update() {
		List.of(UserUI.games, UserUI.search, UserUI.tourn, UserUI.stats).stream()
			.forEach(btn -> {
				if(UserUI.selected.equals(Optional.of(btn))) {
					btn.setForeground(Color.BLUE);
				} else {
					btn.setForeground(Color.BLACK);
				}
			});
	}

	private void loadGames() {
		//final BoardGUI board= new BoardGUI(new Game(List.of(new Pair<>("P:e2:::e4","P:e7:#::e5"))));
		final JPanel games = new GamesTable().getPanel();
		final RegisterGameUI form= new RegisterGameUI();
		UserUI.centerPane.removeAll();
		UserUI.centerPane.revalidate();
		//UserUI.centerPane.add(board.getBoard());
		UserUI.centerPane.add(games);
		//UserUI.centerPane.add(form.getPanel());
		//UserUI.panel.add(form.getBoard(),BorderLayout.SOUTH);
		UserUI.centerPane.repaint();
		//UserUI.panel.add(board.getRightSidebar(),BorderLayout.WEST);
		//UserUI.panel.add(board.getLeftSidebar(),BorderLayout.EAST);
		//UserUI.panel.add(board.getFooter(),BorderLayout.SOUTH);
		//pack();
	}
	
	private void loadStats() {
		// TODO Auto-generated method stub
		
	}
	
	private void loadTournaments() {
		// TODO Auto-generated method stub
		
	}

	private void loadSearch() {
		UserUI.centerPane.removeAll();
		UserUI.centerPane.revalidate();
		var wrapper = new JPanel();
		wrapper.add(UserUI.searchBox);
		wrapper.add(UserUI.launchSearch);
		wrapper.setAlignmentX(CENTER_ALIGNMENT);
		UserUI.centerPane.add(wrapper);
		UserUI.centerPane.repaint();
	}
	
	/** 
	 * Wraps components into vertical panels containing them.
	 */
	private static JComponent wrapV(Collection<JComponent> elements) {
		elements.stream()
			.forEach(e -> {
				e.setAlignmentX(CENTER_ALIGNMENT);
				e.setBorder(new EmptyBorder((int)(UserUI.screen.height * UserUI.PADDING),
						(int)(UserUI.screen.height * UserUI.PADDING),
						(int)(UserUI.screen.height * UserUI.PADDING),
						(int)(UserUI.screen.height * UserUI.PADDING)));
				});
		var wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		wrapper.setAlignmentX(CENTER_ALIGNMENT);
		wrapper.setAlignmentY(CENTER_ALIGNMENT);
		elements.stream().forEach(e -> wrapper.add(e));
		return wrapper;
	}
	
}

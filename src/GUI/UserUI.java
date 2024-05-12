package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import board.Game;
import util.Pair;
import util.loaders.FontLoader;

public class UserUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private static final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private static final double PADDING = 0.067,
			FONT_SIZE = 0.02;
	private static final JPanel panel = new JPanel(new BorderLayout()),
			// centerPane represents the center section of pane
			centerPane = new JPanel();
	private static final ImageIcon logo = new ImageIcon(UserUI.class.getResource("/icons/logo.png")),
			defIcon = new ImageIcon(UserUI.class.getResource("/icons/default.png")),
			// initialize this icon in order to be scaled in this size
			logoutIcon = new ImageIcon(new ImageIcon(UserUI.class.getResource("/icons/logout.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	private static final JLabel defText = new JLabel("Welcome to Chess Org");
	private static final JMenuBar menu = new JMenuBar(); // could be removed (still to check)
	private static final JButton games = new JButton("MY GAMES"),
			stats = new JButton("STATS"),
			tourn = new JButton("SIGN IN FOR TOURNAMENTS"),
			search = new JButton("SEARCH"),
			logout = new JButton(UserUI.logoutIcon);
	private static final FontLoader fontLoad = new FontLoader();
	private static Optional<JButton> selected = Optional.empty();
	
	public UserUI() {
		super("Chess Organization");
		setSize(UserUI.screen);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(UserUI.panel);
		setIconImage(UserUI.logo.getImage());
		initialize();
		setVisible(true);
	}
	
	private void initialize() {
		// initializing components
		UserUI.games.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadGames();
				UserUI.selected = Optional.of(games);
				update();
			}
			
		});
		UserUI.stats.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadStats();
				UserUI.selected = Optional.of(stats);
				update();
			}
			
		});
		UserUI.tourn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadTournaments();
				UserUI.selected = Optional.of(tourn);
				update();
			}
			
		});
		UserUI.search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadSearch();
				UserUI.selected = Optional.of(search);
				update();
			}
			
		});
		UserUI.logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?","WARNING",JOptionPane.YES_NO_OPTION);

					if(dialogButton == JOptionPane.YES_OPTION) {
						System.exit(0);
					} else {
						remove(dialogButton);
					}
			}
			
		});
		// inserting centerPane into pane
		UserUI.panel.add(centerPane, BorderLayout.CENTER);
		// TODO il font non carica
		UserUI.defText.setFont(UserUI.fontLoad.getTextFont());
		// initializing northern panel
		List.of(games, stats, tourn, search, Box.createHorizontalGlue(), logout).stream()
			.forEach(elem -> {
				UserUI.menu.add(elem);
				elem.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, (int)(UserUI.screen.height * UserUI.FONT_SIZE)));
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
		// TODO per qualche motivo entra qui ma il pannello non si aggiorna
		final BoardGUI board= new BoardGUI(new Game(List.of(new Pair<>("",""))));
		UserUI.centerPane.removeAll();
		UserUI.centerPane.revalidate();
		UserUI.centerPane.add(board.getGui());
		UserUI.centerPane.repaint();
	}
	
	private void loadStats() {
		// TODO Auto-generated method stub
		
	}
	
	private void loadTournaments() {
		// TODO Auto-generated method stub
		
	}

	private void loadSearch() {
		// TODO Auto-generated method stub
		
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

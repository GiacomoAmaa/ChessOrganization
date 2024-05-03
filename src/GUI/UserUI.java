package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class UserUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private static final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private static final double PADDING = 0.067,
			FONT_SIZE = 0.02;
	private static final JPanel panel = new JPanel(new BorderLayout());
	private static final ImageIcon logo = new ImageIcon(UserUI.class.getResource("/icons/logo.png")),
			defIcon = new ImageIcon(UserUI.class.getResource("/icons/default.png"));
	private static final JLabel defaultTxt = new JLabel("Welcome to Chess Org");
	private static final JMenuBar menu = new JMenuBar(); // could be removed (still to check)
	private static final JButton games = new JButton("MY GAMES"),
			stats = new JButton("STATS"),
			tourn = new JButton("SIGN IN FOR TOURNAMENTS"),
			search = new JButton("SEARCH");
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
		games.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadGames();
				UserUI.selected = Optional.of(games);
				update();
			}
			
		});
		stats.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadStats();
				UserUI.selected = Optional.of(stats);
				update();
			}
			
		});
		tourn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadTournaments();
				UserUI.selected = Optional.of(tourn);
				update();
			}
			
		});
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadSearch();
				UserUI.selected = Optional.of(search);
				update();
			}
			
		});
		UserUI.defaultTxt.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, (int)(UserUI.screen.height * UserUI.FONT_SIZE)));
		// initializing northern panel
		List.of(games, stats, tourn, search).stream()
			.forEach(elem -> {
				UserUI.menu.add(elem);
				elem.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, (int)(UserUI.screen.height * UserUI.FONT_SIZE)));
			});
		
		UserUI.panel.add(UserUI.menu, BorderLayout.NORTH);
		// initializing center panel
		UserUI.panel.add(wrapV(List.of(UserUI.defaultTxt, new JLabel(UserUI.defIcon))), BorderLayout.CENTER);
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
		// TODO Auto-generated method stub
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
	private JComponent wrapV(Collection<JComponent> elements) {
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

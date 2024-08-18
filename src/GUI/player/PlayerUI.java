package GUI.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GUI.MyGamesUI;
import GUI.SearchUI;
import GUI.Table;
import GUI.api.UserInterface;
import util.UserType;
import util.loaders.FontLoader;

public class PlayerUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private static final Dimension screen = new Dimension(700, 700);
	private static final double PADDING = 0.067;
	private static final float TEXT_SIZE = 15,
			TITLE_SIZE = 18;
	private Supplier<List<List<String>>> getAnn;
	private Function<Integer, Boolean> isSub;
	private Function<Integer, Boolean> subscribe;
	private Consumer<Integer> unsubscribe;
	
	private static final JPanel panel = new JPanel(new BorderLayout()),
			// centerPane represents the center section of pane
			centerPane = new JPanel();
	private static final ImageIcon logo = new ImageIcon(PlayerUI.class.getResource("/icons/logo.png")),
			defIcon = new ImageIcon(PlayerUI.class.getResource("/icons/default.png"));
	private static final JLabel defText = new JLabel("Welcome to Chess Org");
	private static final JMenuBar menu = new JMenuBar();
	private static final JMenu personal = new JMenu("Personal Area"),
			search = new JMenu("Search"),
			tournaments = new JMenu("Announcements"),
			logout = new JMenu("Logout");
	private static final JMenuItem games = new JMenuItem("Games"),
			stats = new JMenuItem("Statistics");

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
	
	public void setTournamentsHandler(Supplier<List<List<String>>> handler, Function<Integer, Boolean> isSub,
			Function<Integer, Boolean> subscribe, Consumer<Integer> unsubscribe) {
		this.getAnn = handler;
		this.isSub = isSub;
		this.subscribe = subscribe;
		this.unsubscribe = unsubscribe;
	}
	
	public void close() {
		setVisible(false);
	}
	
	private void initialize() {
		PlayerUI.games.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayerUI.selected = Optional.of(PlayerUI.personal);
				loadUI(new MyGamesUI(UserType.PLAYER));
				update();
			}
		});

		PlayerUI.stats.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayerUI.selected = Optional.of(PlayerUI.personal);
				loadUI(new StatisticsUI());
				update();
			}
		});

		setHandler(PlayerUI.search, () -> {
			loadUI(new SearchUI());
			PlayerUI.selected = Optional.of(PlayerUI.search);
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
		
		setHandler(PlayerUI.tournaments, () -> {
			loadUI(new TournamentsUI(getAnn, isSub, subscribe, unsubscribe));
			PlayerUI.selected = Optional.of(tournaments);
			update();
		});
		
		// adding every menu item to its corresponding menu
		PlayerUI.personal.add(PlayerUI.games);
		PlayerUI.personal.add(PlayerUI.stats);
		// inserting centerPane into pane
		PlayerUI.panel.add(centerPane, BorderLayout.CENTER);
		PlayerUI.defText.setFont(PlayerUI.fontLoad.getTextFont().deriveFont(PlayerUI.TEXT_SIZE));
		// initializing northern panel
		List.of(personal, tournaments, search, logout, games, stats).stream()
			.forEach(elem -> {
				if (elem instanceof JMenu) {
					PlayerUI.menu.add(elem);
				}
				elem.setFont(PlayerUI.fontLoad.getTitleFont().deriveFont(PlayerUI.TITLE_SIZE));
			});
		PlayerUI.panel.add(PlayerUI.menu, BorderLayout.NORTH);
		// initializing center panel
		PlayerUI.centerPane.add(wrapV(List.of(PlayerUI.defText, new JLabel(PlayerUI.defIcon))), BorderLayout.CENTER);
	}
	
	private void update() {
		List.of(PlayerUI.personal, PlayerUI.search, PlayerUI.tournaments).stream()
			.forEach(btn -> {
				if(PlayerUI.selected.equals(Optional.of(btn))) {
					btn.setForeground(Color.BLUE);
				} else {
					btn.setForeground(Color.BLACK);
				}
			});
	}
	
	private void loadUI(UserInterface ui) {
		PlayerUI.centerPane.removeAll();
		PlayerUI.centerPane.revalidate();
		var wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		wrapper.setAlignmentX(CENTER_ALIGNMENT);
		wrapper.setAlignmentY(CENTER_ALIGNMENT);
		wrapper.add(ui.getUpperPanel());
		wrapper.add(ui.getLowerPanel());
		PlayerUI.centerPane.add(wrapper);
		PlayerUI.centerPane.repaint();
	}

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

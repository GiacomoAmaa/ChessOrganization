package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.loaders.FontLoader;

public class RefereeUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private static final Dimension screen = new Dimension(700, 700);
	private static final double PADDING = 0.067;
	private static final float TEXT_SIZE = 15,
			TITLE_SIZE = 18;

	private static final JPanel panel = new JPanel(new BorderLayout()),
			// centerPane represents the center section of pane
			centerPane = new JPanel();
	private static final ImageIcon logo = new ImageIcon(RefereeUI.class.getResource("/icons/logo.png")),
			defIcon = new ImageIcon(RefereeUI.class.getResource("/icons/default.png"));
	private static final JLabel defText = new JLabel("Welcome to Chess Org");

	private static final JMenuBar menu = new JMenuBar();
	private static final JMenu designations = new JMenu("Designations"),
			search = new JMenu("Search"),
			games = new JMenu("My Games"),
			logout = new JMenu("Logout");

	private static final FontLoader fontLoad = new FontLoader();

	private static Optional<JMenu> selected = Optional.empty();
	
	public RefereeUI() {
		super("Chess Organization");
		setSize(RefereeUI.screen);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(RefereeUI.panel);
		setIconImage(RefereeUI.logo.getImage());
		initialize();
		setVisible(true);
	}
	
	public void close() {
		setVisible(false);
	}
	
	private void initialize() {

		setHandler(RefereeUI.search, () -> {
			loadUI(new SearchUI());
			RefereeUI.selected = Optional.of(RefereeUI.search);
			update();
		});

		setHandler(RefereeUI.logout, () -> {
			RefereeUI.selected = Optional.of(RefereeUI.logout);
			update();
			int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?","WARNING",JOptionPane.YES_NO_OPTION);

			if(dialogButton == JOptionPane.YES_OPTION) {
				close();
				System.exit(0);
			}
		});
		
		// inserting centerPane into pane
		RefereeUI.panel.add(centerPane, BorderLayout.CENTER);
		RefereeUI.defText.setFont(RefereeUI.fontLoad.getTextFont().deriveFont(RefereeUI.TEXT_SIZE));
		// initializing northern panel
		List.of(designations, games, search, logout).stream()
			.forEach(elem -> {
				if (elem instanceof JMenu) {
					RefereeUI.menu.add(elem);
				}
				elem.setFont(RefereeUI.fontLoad.getTitleFont().deriveFont(RefereeUI.TITLE_SIZE));
			});
		RefereeUI.panel.add(RefereeUI.menu, BorderLayout.NORTH);
		// initializing center panel
		RefereeUI.centerPane.add(wrapV(List.of(RefereeUI.defText, new JLabel(RefereeUI.defIcon))), BorderLayout.CENTER);
	}
	
	private void update() {
		List.of(RefereeUI.designations, RefereeUI.search, RefereeUI.games).stream()
			.forEach(btn -> {
				if(RefereeUI.selected.equals(Optional.of(btn))) {
					btn.setForeground(Color.BLUE);
				} else {
					btn.setForeground(Color.BLACK);
				}
			});
	}
	
	private void loadUI(UserInterface ui) {
		RefereeUI.centerPane.removeAll();
		RefereeUI.centerPane.revalidate();
		var wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		wrapper.setAlignmentX(CENTER_ALIGNMENT);
		wrapper.setAlignmentY(CENTER_ALIGNMENT);
		wrapper.add(ui.getUpperPanel());
		wrapper.add(ui.getLowerPanel());
		RefereeUI.centerPane.add(wrapper);
		RefereeUI.centerPane.repaint();
	}
	
	/** 
	 * Wraps components into vertical panels containing them.
	 */
	private static JComponent wrapV(Collection<JComponent> elements) {
		elements.stream()
			.forEach(e -> {
				e.setAlignmentX(CENTER_ALIGNMENT);
				e.setBorder(new EmptyBorder((int)(RefereeUI.screen.height * RefereeUI.PADDING),
						(int)(RefereeUI.screen.height * RefereeUI.PADDING),
						(int)(RefereeUI.screen.height * RefereeUI.PADDING),
						(int)(RefereeUI.screen.height * RefereeUI.PADDING)));
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

package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Optional;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import java.awt.event.MouseEvent;

import java.awt.Color;


public class RefereeUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private static final Dimension screen = new Dimension(700,700);
	private static final JPanel panel = new JPanel(new BorderLayout()), centerPane = new JPanel();
	private static final ImageIcon logo = new ImageIcon(UserUI.class.getResource("/icons/logo.png"));
	private static final JMenuBar navbar = new JMenuBar();
	private static final JMenu games = new JMenu("Designations");
	private static final JMenu stats = new JMenu("Statistics");
	private static final JMenu search = new JMenu("Search");
	private static final JMenu logout = new JMenu("Logout");
	private static final JMenuItem searchgames = new JMenuItem("Games");
	private static final JMenuItem searchplayers = new JMenuItem("Players");
	private static final JMenuItem history = new JMenuItem("History");
	private static final JMenuItem toRegister = new JMenuItem("New games");
	private static Optional<JMenu> selected = Optional.empty();

	public RefereeUI() {
		super("Chess Organization");
		setSize(RefereeUI.screen);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(RefereeUI.panel);
		setIconImage(RefereeUI.logo.getImage());
		initializeNavbar();
		panel.add(centerPane,BorderLayout.CENTER);
		setVisible(true);
	}

	private void initializeNavbar() {
		RefereeUI.navbar.add(games);
		RefereeUI.history.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RefereeUI.selected = Optional.of(games);
				updateNavbar();
			}
		});
		RefereeUI.games.add(history);
		RefereeUI.toRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RefereeUI.selected = Optional.of(games);
				updateNavbar();
				RefereeUI.centerPane.removeAll();
				RefereeUI.centerPane.revalidate();
				RefereeUI.centerPane.add(new RegisterGameUI().getPanel());
				RefereeUI.centerPane.repaint();
			}
		});
		RefereeUI.games.add(toRegister);
		RefereeUI.navbar.add(search);
		RefereeUI.search.add(searchgames);
		RefereeUI.searchgames.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RefereeUI.selected = Optional.of(search);
				updateNavbar();
			}
		});
		RefereeUI.search.add(searchplayers);
		RefereeUI.searchplayers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RefereeUI.selected = Optional.of(search);
				updateNavbar();
			}
		});
		navbar.add(stats);
		RefereeUI.stats.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?","WARNING",JOptionPane.YES_NO_OPTION);
				if(dialogButton == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				RefereeUI.selected = Optional.of(stats);
				updateNavbar();
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
		navbar.add(logout);
		RefereeUI.logout.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?","WARNING",JOptionPane.YES_NO_OPTION);
				if(dialogButton == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				RefereeUI.selected = Optional.of(logout);
				updateNavbar();
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
		RefereeUI.panel.add(navbar,BorderLayout.NORTH);
	}

	private void updateNavbar() {
		Set.of(RefereeUI.games,RefereeUI.stats,RefereeUI.search,RefereeUI.logout).forEach(btn -> {
            if(RefereeUI.selected.equals(Optional.of(btn))) {
                btn.setForeground(Color.BLUE);
            } else {
                btn.setForeground(Color.BLACK);
            }
        });
		return;
	}

}

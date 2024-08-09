package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SearchUI {
	
	private final JPanel panel = new JPanel(new GridLayout(0, 5));
	private final GamesTable table = new GamesTable();

    private static final JComboBox<String> searchType =
    		new JComboBox<>(new String[] {"Players", "Games"});

    private static final JTextField firstName = new JTextField(),
    		secondName = new JTextField(), firstDate = new JTextField("dd/mm/yyyy"),
    		secondDate = new JTextField("dd/mm/yyyy");
    
    private static final JButton searchButton = new JButton("Search");
    
    public SearchUI() {
        this.panel.add(searchType);
        this.panel.add(new JLabel("Name:   ", SwingConstants.RIGHT));
        this.panel.add(firstName);
        this.panel.add(new JLabel("Surname:   ", SwingConstants.RIGHT));
        this.panel.add(secondName);
        this.panel.add(new JLabel("Since:   ", SwingConstants.RIGHT));
        this.panel.add(firstDate);
        this.panel.add(new JLabel("To:   ", SwingConstants.RIGHT));	
        this.panel.add(secondDate);
        this.panel.add(searchButton);
    	setupForm();
    }

    private void setupForm() {
    	
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
                sdf.setLenient(false);
                try {
                    Date date1 = sdf.parse(firstDate.getText());
                    Date date2 = sdf.parse(secondDate.getText());

                } catch (ParseException ex) {

                }
            }
        });

    }
    
	public JPanel getPanel() {
		return panel;
	}

	public JPanel getBoard() {
		return this.table.getPanel();
	}

}

package be.vodelee.ultimate.ihm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import be.vodelee.ultimate.core.Match;
import be.vodelee.ultimate.core.Team;
import be.vodelee.ultimate.core.Tournament;

public class EncodeMatchScreen extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private MainScreen mainScreen;
	private PoolPanel poolPanel;
	private Container container;

	private JPanel upperPanel;
	private JPanel chooseMatchPanel;
	private JComboBox chooseMatchComboBox;
	private JPanel encodeMatchPanel;
	private JLabel team1Label;
	private JTextField team1TextField;
	private JLabel separatorLabel;
	private JTextField team2TextField;
	private JLabel team2Label;


	private JPanel lowerPanel;
	private JButton validateButton;
	private JButton cancelButton;

	private boolean alreadyUsed;
	
	private Match match;
	
	private EncodeMatchWindowAdapter encodeMatchWindowAdapter;


	EncodeMatchScreen (MainScreen mainScreen, PoolPanel poolPanel){
		super("Encoder un résultat");
		this.mainScreen = mainScreen;
		this.poolPanel = poolPanel;
		// Deactivate the main screen
		mainScreen.setEnabled(false);
		// But activate it when clik on close
		encodeMatchWindowAdapter = new EncodeMatchWindowAdapter();
		addWindowListener(encodeMatchWindowAdapter);

		container = getContentPane();

		container.setLayout(new BorderLayout());

		// Create the upper panel
		upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout(2,1));

		// Create the choose match panel
		chooseMatchPanel = new JPanel();
		int category = poolPanel.getPoolCategoy();
		if(category == Team.ADULT_CATEGORY){
			chooseMatchComboBox = new JComboBox(Tournament.getInstance().getPoolMap().get(poolPanel.getPoolNumber()).getMatchNotPlayedList().toArray());
		}
		else{
			if(category == Team.KID_CATEGORY){
				chooseMatchComboBox = new JComboBox(Tournament.getInstance().getKidPool().getMatchNotPlayedList().toArray());
			}
			else{ // Women category
				chooseMatchComboBox = new JComboBox(Tournament.getInstance().getWomenPool().getMatchNotPlayedList().toArray());
			}
		}
		chooseMatchComboBox.insertItemAt("Choisir un match", 0);
		chooseMatchComboBox.setSelectedIndex(0);
		alreadyUsed = false;
		chooseMatchComboBox.addActionListener(this);
		chooseMatchPanel.add(new JLabel("Match à encoder"));
		chooseMatchPanel.add(chooseMatchComboBox);
		upperPanel.add(chooseMatchPanel);


		// create update team panel
		encodeMatchPanel= new JPanel();
		team1Label = new JLabel();
		team1TextField = new JTextField(2);
		team1TextField.setEnabled(false);
		separatorLabel =new JLabel("-");
		team2Label = new JLabel();
		team2TextField = new JTextField(2);
		team2TextField.setEnabled(false);
		encodeMatchPanel.add(team1Label);
		encodeMatchPanel.add(team1TextField);
		encodeMatchPanel.add(separatorLabel);
		encodeMatchPanel.add(team2TextField);
		encodeMatchPanel.add(team2Label);
		upperPanel.add(encodeMatchPanel);
		container.add(upperPanel, BorderLayout.CENTER);

		// Create lower Panel : contains Validate and Cancel Button
		lowerPanel = new JPanel();
		validateButton = new JButton("Valider");
		validateButton.setEnabled(false);
		validateButton.addActionListener(this);
		lowerPanel.add(validateButton);
		cancelButton = new JButton("Annuler");
		cancelButton.addActionListener(this);
		lowerPanel.add(cancelButton);
		container.add(lowerPanel, BorderLayout.SOUTH);

		this.getRootPane().setDefaultButton(validateButton);
		container.add(lowerPanel,BorderLayout.SOUTH);

		setResizable(false);
		pack();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == chooseMatchComboBox){
			if(!alreadyUsed){
				chooseMatchComboBox.removeItemAt(0);
				alreadyUsed = true;
			}
			team1TextField.setEnabled(true);
			team2TextField.setEnabled(true);
			validateButton.setEnabled(true);
			// search the match
			match = (Match)chooseMatchComboBox.getSelectedItem();
			// update du nom de l'équipe 1
			team1Label.setText(match.getTeam1().toString());
			// update du nom de l'équipe 2
			team2Label.setText(match.getTeam2().toString());
			pack();
			return;
		}
		if(e.getSource() == validateButton){
			// Validate the data's
			int goalNumberTeam1;
			int goalNumberTeam2;			
			// Team 1
			if(team1TextField.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "Veuillez remplir le nombre de buts de la première équipe", "Information manquante", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try{
				goalNumberTeam1 = Integer.parseInt(team1TextField.getText());
				if(goalNumberTeam1 < 0){
					JOptionPane.showMessageDialog(this, "Nombre de buts invalide pour la première équipe!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Nombre de buts invalide pour la première équipe!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
				return;
			}
			// Team 2
			if(team2TextField.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "Veuillez remplir le nombre de buts de la deuxième équipe", "Information manquante", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try{
				goalNumberTeam2 = Integer.parseInt(team2TextField.getText());
				if(goalNumberTeam2 < 0){
					JOptionPane.showMessageDialog(this, "Nombre de buts invalide pour la deuxième équipe!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Nombre de buts invalide pour la deuxième équipe!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
				return;
			}
			// Update of the classement model
			Set<Team> teamSet;
			if(poolPanel.getPoolCategoy() == Team.ADULT_CATEGORY){
				teamSet = Tournament.getInstance().getPoolMap().get(poolPanel.getPoolNumber()).getTeamSet();
			}
			else{
				if(poolPanel.getPoolCategoy() == Team.KID_CATEGORY){
					teamSet = Tournament.getInstance().getKidPool().getTeamSet();
				}
				else{ // Women category
					teamSet = Tournament.getInstance().getWomenPool().getTeamSet();
				}
			}
			
			// Remove the 2 teams
			teamSet.remove(match.getTeam1());
			teamSet.remove(match.getTeam2());
			// Update the 2 teams with the result
			match.encodeResult(goalNumberTeam1, goalNumberTeam2);
			// Add the 2 teams
			teamSet.add(match.getTeam1());
			teamSet.add(match.getTeam2());
			// Reactivate the main Screen
			mainScreen.setEnabled(true);
			// Update the tables
			poolPanel.updatePoolTables();
			dispose();
			return;
		}
		if(e.getSource() == cancelButton){
			// Reactivate the main Screen
			mainScreen.setEnabled(true);
			dispose();
			return;
		}
	}

	class EncodeMatchWindowAdapter extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent we) {
			mainScreen.setEnabled(true);
		}
	}
}

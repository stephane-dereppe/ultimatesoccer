package be.vodelee.ultimate.ihm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import be.vodelee.ultimate.core.Team;
import be.vodelee.ultimate.core.Tournament;

public class AddTeamScreen extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;

	private MainScreen mainScreen;

	private Container container;
	JPanel upperPanel;
	private JLabel teamNameLabel;
	private JTextField teamNameTextField;
	private JLabel teamCaptainLabel;
	private JTextField teamCaptainTextField;
	private JLabel teamCategoryLabel;
	private ButtonGroup buttonGroup;
	private JRadioButton adultCategoryRadioButton;
	private JRadioButton childCategoryRadioButton;
	private JRadioButton womenCategoryRadioButton;
	private JPanel buttonGroupPanel;
	private JPanel numberOfPlayerPanel;
	private JLabel numberOfPlayerLabel;
	private JTextField numberOfPlayerTextField;

	private JPanel lowerPanel;
	private JButton validateButton;
	private JButton cancelButton;
	private AddTeamWindowAdapter addTeamWindowAdaptater;

	public AddTeamScreen(MainScreen mainScreen) {
		super("Ajouter une équipe");
		this.mainScreen = mainScreen;
		// desactivate the main screen while adding a team
		this.mainScreen.setEnabled(false);
		// But activate it when clik on close
		addTeamWindowAdaptater = new AddTeamWindowAdapter();
		addWindowListener(addTeamWindowAdaptater);

		container = getContentPane();

		// create upper panel
		upperPanel = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 0;
		upperPanel.setLayout(new GridBagLayout());
		teamNameLabel = new JLabel("Nom de l'équipe      ");
		teamNameTextField = new JTextField(20);
		upperPanel.add(teamNameLabel,gbc);
		gbc.gridx = 1; gbc.gridy = 0;
		upperPanel.add(teamNameTextField, gbc);
		teamCaptainLabel = new JLabel("Capitaine de l'équipe ");
		gbc.gridx = 0; gbc.gridy = 1;
		upperPanel.add(teamCaptainLabel, gbc);
		teamCaptainTextField = new JTextField(20);
		gbc.gridx = 1; gbc.gridy = 1;
		upperPanel.add(teamCaptainTextField, gbc);
		teamCategoryLabel = new JLabel("Catégorie de l'équipe");
		gbc.gridx = 0; gbc.gridy = 2;
		upperPanel.add(teamCategoryLabel,gbc);
		buttonGroupPanel = new JPanel();
		buttonGroup = new ButtonGroup();
		adultCategoryRadioButton = new JRadioButton("Catégorie adulte",true);
		childCategoryRadioButton = new JRadioButton("Catégorie enfant");
		womenCategoryRadioButton = new JRadioButton("Catégorie fille");
		buttonGroup.add(adultCategoryRadioButton);
		buttonGroup.add(childCategoryRadioButton);
		buttonGroup.add(womenCategoryRadioButton);
		buttonGroupPanel.setLayout(new GridLayout(2,2));
		buttonGroupPanel.add(adultCategoryRadioButton);
		buttonGroupPanel.add(childCategoryRadioButton);
		buttonGroupPanel.add(womenCategoryRadioButton);
		gbc.gridx = 1; gbc.gridy = 2;
		upperPanel.add(buttonGroupPanel,gbc);
		numberOfPlayerPanel = new JPanel();
		numberOfPlayerLabel = new JLabel("Nombre de joueurs");
		numberOfPlayerTextField = new JTextField(2);
		numberOfPlayerPanel.add(numberOfPlayerLabel);
		numberOfPlayerPanel.add(numberOfPlayerTextField);
		gbc.gridx = 0; gbc.gridy = 3;
		upperPanel.add(numberOfPlayerPanel, gbc);
		container.add(upperPanel,BorderLayout.CENTER);

		// Create lower Panel : contains Validate and Cancel Button
		lowerPanel = new JPanel();
		validateButton = new JButton("Valider");
		validateButton.addActionListener(this);
		lowerPanel.add(validateButton);
		cancelButton = new JButton("Annuler");
		cancelButton.addActionListener(this);
		lowerPanel.add(cancelButton);
		container.add(lowerPanel, BorderLayout.SOUTH);

		this.getRootPane().setDefaultButton(validateButton);
		setResizable(false);
		pack();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == validateButton){
			// Validate the data 
			if(teamNameTextField.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "Veuillez compléter le nom de l'équipe", "Information manquante", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(teamCaptainTextField.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "Veuillez compléter le nom du capitaine", "Information manquante", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(numberOfPlayerTextField.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "Veuillez compléter le nombre de joueurs", "Information manquante", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// validate the number of player 
			int numberOfPlayer;
			try{
				numberOfPlayer = Integer.parseInt(numberOfPlayerTextField.getText());
				if(numberOfPlayer <= 0){
					JOptionPane.showMessageDialog(this, "Nombre de joueurs invalide!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Nombre de joueurs invalide!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int category = Team.ADULT_CATEGORY;
			if(adultCategoryRadioButton.isSelected()) category= Team.ADULT_CATEGORY;
			if(childCategoryRadioButton.isSelected()) category= Team.KID_CATEGORY;
			if(womenCategoryRadioButton.isSelected()) category= Team.WOMEN_CATEGORY;
			Team team = new Team(teamNameTextField.getText(),teamCaptainTextField.getText(),category, numberOfPlayer);
			if(Tournament.getInstance().getTeamMap().containsKey(team.toString())){
				// The team already exist
				JOptionPane.showMessageDialog(this, "Cette équipe existe déjà \nChangez le nom de l'équipe!", "Erreur",JOptionPane.ERROR_MESSAGE);
				return;
			}
			else{
				Tournament.getInstance().getTeamMap().put(team.toString(), team);
				// Update the table of teams
				mainScreen.updateTeamTable();
				// Reactivate the main Screen
				mainScreen.setEnabled(true);
				System.out.println("j'ai cliqué sur OK");
				dispose();
				return;

			}
		}
		if(e.getSource() == cancelButton){
			System.out.println("j'ai cliqué sur annuler");
			// Reactivate the main Screen
			mainScreen.setEnabled(true);
			dispose();
			return;
		}
	}
	class AddTeamWindowAdapter extends WindowAdapter{
		public void windowClosing(WindowEvent we) { 
			mainScreen.setEnabled(true);
		}
	}
}

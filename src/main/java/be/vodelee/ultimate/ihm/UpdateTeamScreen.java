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
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import be.vodelee.ultimate.core.Team;
import be.vodelee.ultimate.core.Tournament;

public class UpdateTeamScreen extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private MainScreen mainScreen;

	private Container container;

	private JPanel chooseTeamPanel;
	private JComboBox chooseTeamComboBox;

	private JPanel updateTeamPanel;
	private JLabel teamNameLabel;
	private JTextField teamNameTextField;
	private JLabel teamCaptainLabel;
	private JTextField teamCaptainTextField;
	private JLabel teamCategoryLabel;
	private ButtonGroup buttonGroup;
	private JRadioButton adultCategoryRadioButton;
	private JRadioButton kidCategoryRadioButton;
	private JRadioButton womenCategoryRadioButton;
	private JPanel buttonGroupPanel;
	private JPanel numberOfPlayerPanel;
	private JLabel numberOfPlayerLabel;
	private JTextField numberOfPlayerTextField;

	private JPanel lowerPanel;
	private JButton validateButton;
	private JButton cancelButton;

	private UpdateTeamWindowAdapter updateTeamWindowAdapter;
	
	Team teamChoosen;
	private boolean alreadyUsed;
	Team teamUpdated;

	UpdateTeamScreen (MainScreen mainScreen){
		super("Modifier une équipe");
		this.mainScreen = mainScreen;
		// Desactivate the main screen
		mainScreen.setEnabled(false);
		// But activate it when clik on close
		updateTeamWindowAdapter = new UpdateTeamWindowAdapter();
		addWindowListener(updateTeamWindowAdapter);

		container = getContentPane();

		container.setLayout(new BorderLayout());

		// Create the choose team panel
		chooseTeamPanel = new JPanel();
		chooseTeamComboBox = new JComboBox(Tournament.getInstance().getTeamMap().keySet().toArray());
		chooseTeamComboBox.insertItemAt("Choisir équipe",0);
		chooseTeamComboBox.setSelectedIndex(0);
		alreadyUsed = false;
		chooseTeamComboBox.addActionListener(this);
		chooseTeamPanel.add(new JLabel("Equipe à modifier"));
		chooseTeamPanel.add(chooseTeamComboBox);
		container.add(chooseTeamPanel, BorderLayout.WEST);

		// create update team panel
		updateTeamPanel = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 0;
		updateTeamPanel.setLayout(new GridBagLayout());
		teamNameLabel = new JLabel("Nom de l'équipe      ");
		teamNameTextField = new JTextField(20);
		teamNameTextField.setEnabled(false);
		updateTeamPanel.add(teamNameLabel,gbc);
		gbc.gridx = 1; gbc.gridy = 0;
		updateTeamPanel.add(teamNameTextField, gbc);
		teamCaptainLabel = new JLabel("Capitaine de l'équipe ");
		gbc.gridx = 0; gbc.gridy = 1;
		updateTeamPanel.add(teamCaptainLabel, gbc);
		teamCaptainTextField = new JTextField(20);
		teamCaptainTextField.setEnabled(false);
		gbc.gridx = 1; gbc.gridy = 1;
		updateTeamPanel.add(teamCaptainTextField, gbc);
		teamCategoryLabel = new JLabel("Catégorie de l'équipe");
		gbc.gridx = 0; gbc.gridy = 2;
		updateTeamPanel.add(teamCategoryLabel,gbc);
		buttonGroupPanel = new JPanel();
		buttonGroup = new ButtonGroup();
		adultCategoryRadioButton = new JRadioButton("Catégorie adulte",true);
		adultCategoryRadioButton.setEnabled(false);
		kidCategoryRadioButton = new JRadioButton("Catégorie enfant");
		kidCategoryRadioButton.setEnabled(false);
		womenCategoryRadioButton = new JRadioButton("Catégorie fille");
		womenCategoryRadioButton.setEnabled(false);
		buttonGroup.add(adultCategoryRadioButton);
		buttonGroup.add(kidCategoryRadioButton);
		buttonGroup.add(womenCategoryRadioButton);
		buttonGroupPanel.setLayout(new GridLayout(2,2));
		buttonGroupPanel.add(adultCategoryRadioButton);
		buttonGroupPanel.add(kidCategoryRadioButton);
		buttonGroupPanel.add(womenCategoryRadioButton);
		gbc.gridx = 1; gbc.gridy = 2;
		updateTeamPanel.add(buttonGroupPanel,gbc);
		numberOfPlayerPanel = new JPanel();
		numberOfPlayerLabel = new JLabel("Nombre de joueurs");
		numberOfPlayerTextField = new JTextField(2);
		numberOfPlayerTextField.setEnabled(false);
		numberOfPlayerPanel.add(numberOfPlayerLabel);
		numberOfPlayerPanel.add(numberOfPlayerTextField);
		gbc.gridx = 0; gbc.gridy = 3;
		updateTeamPanel.add(numberOfPlayerPanel, gbc);
		container.add(updateTeamPanel,BorderLayout.CENTER);

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


		container.add(lowerPanel,BorderLayout.SOUTH);

		this.getRootPane().setDefaultButton(validateButton);
		setResizable(false);
		pack();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == chooseTeamComboBox){
			if(!alreadyUsed){
				chooseTeamComboBox.removeItemAt(0);
				alreadyUsed = true;
			}
			// Reactivate the input field
			validateButton.setEnabled(true);
			teamNameTextField.setEnabled(true);
			teamCaptainTextField.setEnabled(true);
			adultCategoryRadioButton.setEnabled(true);
			kidCategoryRadioButton.setEnabled(true);
			womenCategoryRadioButton.setEnabled(true);
			numberOfPlayerTextField.setEnabled(true);
			teamChoosen = Tournament.getInstance().getTeamMap().get(chooseTeamComboBox.getSelectedItem().toString());
			teamNameTextField.setText(teamChoosen.getName());
			teamCaptainTextField.setText(teamChoosen.getCaptain());
			if(teamChoosen.getCategory() == Team.ADULT_CATEGORY){
				adultCategoryRadioButton.setSelected(true);
			}
			else{
				if(teamChoosen.getCategory() == Team.KID_CATEGORY){
					kidCategoryRadioButton.setSelected(true);
				}
				else{
					womenCategoryRadioButton.setSelected(true);
				}
			}
			numberOfPlayerTextField.setText(""+teamChoosen.getNumberOfPlayer());
			return;
		}

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
			// Update the model of teams
			int category = Team.ADULT_CATEGORY;
			if(adultCategoryRadioButton.isSelected()) category= Team.ADULT_CATEGORY;
			if(kidCategoryRadioButton.isSelected()) category= Team.KID_CATEGORY;
			if(womenCategoryRadioButton.isSelected()) category= Team.WOMEN_CATEGORY;
			teamUpdated = new Team(teamNameTextField.getText(),teamCaptainTextField.getText(),category, numberOfPlayer);
			Map<String, Team> teamMap = Tournament.getInstance().getTeamMap();
			// If the name of the name has not changed
			if(teamChoosen.equals(teamUpdated)){
				teamMap.put(teamUpdated.toString(), teamUpdated);
			}
			else{
				// The name has changed
				teamMap.remove(teamChoosen.toString());
				if(teamMap.containsKey(teamUpdated.toString())){
					// Another has already this name
					JOptionPane.showMessageDialog(this, "Une autre équipe a déjà ce nom! \nChangez le nom de l'équipe!", "Erreur",JOptionPane.ERROR_MESSAGE);
					teamMap.put(teamChoosen.toString(), teamChoosen);
					return;
				}
				else{
					// No team has this name ==> add the team in the model
					teamMap.put(teamUpdated.toString(), teamUpdated);
				}
			}
			mainScreen.updateTeamTable();
			// Reactivate the main Screen
			mainScreen.setEnabled(true);
			System.out.println("j'ai cliqué sur OK");
			dispose();
			return;
		}
		if(e.getSource() == cancelButton){
			System.out.println("j'ai cliqué sur annuler");
			// Reactivate the main Screen
			mainScreen.setEnabled(true);
			dispose();
			return;
		}
	}

	class UpdateTeamWindowAdapter extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent we) {
			mainScreen.setEnabled(true);
		}
	}
}

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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import be.vodelee.ultimate.core.Team;
import be.vodelee.ultimate.core.Tournament;

public class DeleteTeamScreen extends JFrame implements ActionListener{

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
	private JRadioButton childCategoryRadioButton;
	private JRadioButton womenCategoryRadioButton;
	private JPanel buttonGroupPanel;
	private JPanel numberOfPlayerPanel;
	private JLabel numberOfPlayerLabel;
	private JTextField numberOfPlayerTextField;

	private JPanel lowerPanel;
	private JButton validateButton;
	private JButton cancelButton;

	private DeleteTeamWindowAdapter deleteTeamWindowAdapter;

	Team teamChoosen;
	private boolean alreadyUsed;

	DeleteTeamScreen (MainScreen mainScreen){
		super("Supprimer une équipe");
		this.mainScreen = mainScreen;
		// Deactivate the main screen
		mainScreen.setEnabled(false);
		// But activate it when clik on close
		deleteTeamWindowAdapter = new DeleteTeamWindowAdapter();
		addWindowListener(deleteTeamWindowAdapter);

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
		teamNameTextField.setEditable(false);
		updateTeamPanel.add(teamNameLabel,gbc);
		gbc.gridx = 1; gbc.gridy = 0;
		updateTeamPanel.add(teamNameTextField, gbc);
		teamCaptainLabel = new JLabel("Capitaine de l'équipe ");
		gbc.gridx = 0; gbc.gridy = 1;
		updateTeamPanel.add(teamCaptainLabel, gbc);
		teamCaptainTextField = new JTextField(20);
		teamCaptainTextField.setEditable(false);
		gbc.gridx = 1; gbc.gridy = 1;
		updateTeamPanel.add(teamCaptainTextField, gbc);
		teamCategoryLabel = new JLabel("Catégorie de l'équipe");
		gbc.gridx = 0; gbc.gridy = 2;
		updateTeamPanel.add(teamCategoryLabel,gbc);
		buttonGroupPanel = new JPanel();
		buttonGroup = new ButtonGroup();
		adultCategoryRadioButton = new JRadioButton("Catégorie adulte",true);
		adultCategoryRadioButton.setEnabled(false);
		childCategoryRadioButton = new JRadioButton("Catégorie enfant");
		childCategoryRadioButton.setEnabled(false);
		womenCategoryRadioButton = new JRadioButton("Catégorie fille");
		womenCategoryRadioButton.setEnabled(false);
		buttonGroup.add(adultCategoryRadioButton);
		buttonGroup.add(childCategoryRadioButton);
		buttonGroup.add(womenCategoryRadioButton);
		buttonGroupPanel.setLayout(new GridLayout(2,2));
		buttonGroupPanel.add(adultCategoryRadioButton);
		buttonGroupPanel.add(childCategoryRadioButton);
		buttonGroupPanel.add(womenCategoryRadioButton);
		gbc.gridx = 1; gbc.gridy = 2;
		updateTeamPanel.add(buttonGroupPanel,gbc);
		numberOfPlayerPanel = new JPanel();
		numberOfPlayerLabel = new JLabel("Nombre de joueurs");
		numberOfPlayerTextField = new JTextField(2);
		numberOfPlayerTextField.setEditable(false);
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

		/*******************************/

		this.getRootPane().setDefaultButton(validateButton);
		setResizable(false);
		pack();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == chooseTeamComboBox){
			System.out.println("J'ai touché au combo box");
			if(!alreadyUsed){
				chooseTeamComboBox.removeItemAt(0);
				alreadyUsed = true;
			}
			validateButton.setEnabled(true);
			teamChoosen = Tournament.getInstance().getTeamMap().get(chooseTeamComboBox.getSelectedItem().toString());
			teamNameTextField.setText(teamChoosen.getName());
			teamCaptainTextField.setText(teamChoosen.getCaptain());
			if(teamChoosen.getCategory() == Team.ADULT_CATEGORY){
				adultCategoryRadioButton.setSelected(true);
			}
			else{
				if(teamChoosen.getCategory() == Team.KID_CATEGORY){
					childCategoryRadioButton.setSelected(true);
				}
				else{
					womenCategoryRadioButton.setSelected(true);
				}
			}
			numberOfPlayerTextField.setText(""+teamChoosen.getNumberOfPlayer());
			return;
		}
		if(e.getSource() == validateButton){
			int reponse = JOptionPane.showConfirmDialog(this, "Voulez-vous supprimer cette équipe ?", "Suppression équipe", JOptionPane.YES_NO_OPTION);
			if (reponse == JOptionPane.NO_OPTION){
				return;
			}
			Tournament.getInstance().getTeamMap().remove(teamChoosen.toString());
			// Update the table of teams
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

	class DeleteTeamWindowAdapter extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent we) {
			mainScreen.setEnabled(true);
		}
	}
}
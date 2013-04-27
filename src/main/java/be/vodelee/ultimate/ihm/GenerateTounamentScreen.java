package be.vodelee.ultimate.ihm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import be.vodelee.ultimate.core.Tournament;

public class GenerateTounamentScreen extends JFrame{

	private static final long serialVersionUID = 1L;

	private MainScreen mainScreen;
	
	private Container container;
	
	private JPanel paramPanel;
	private JPanel numberOfPoolPanel;
	private JLabel numberOfPoolLabel;
	private JTextField numberOfPoolTextField;
	private JCheckBox poolOfKidsCheckBox;
	private JCheckBox poolOfWomenCheckBox;
	private JPanel numberOfPointForMatchWinPanel;
	private JLabel numberOfpointForMatchWinLabel;
	private JTextField numberOfpointForMatchWinTextField;
	private JPanel numberOfPointForMatchEqualityPanel;
	private JLabel numberOfpointForMatchEqualityLabel;
	private JTextField numberOfpointForMatchEqualityTextField;
	private JPanel numberOfPointForMatchLostPanel;
	private JLabel numberOfpointForMatchLostLabel;
	private JTextField numberOfpointForMatchLostTextField;
	
	
	private JPanel lowerPanel;
	private JButton validateButton;
	private JButton cancelButton;
	
	private GenerateTournamentActionListener generateTournamentActionListener;
	private GenerateTournamentWindowAdapter generateTournamentWindowAdapter;
	
	GenerateTounamentScreen(MainScreen mainscreen){
		super("Paramètres");
		
		this.mainScreen = mainscreen;
		
		// Deactivate the main screen
		mainScreen.setEnabled(false);
		// But activate it when clik on close
		generateTournamentWindowAdapter = new GenerateTournamentWindowAdapter();
		addWindowListener(generateTournamentWindowAdapter);
		
		container = getContentPane();
		
		generateTournamentActionListener = new GenerateTournamentActionListener();
		// Create the param panel
		paramPanel = new JPanel();
		paramPanel.setLayout(new GridLayout(6,1));
		numberOfPoolPanel = new JPanel();
		numberOfPoolLabel = new JLabel("Nombre de terrains :");
		numberOfPoolTextField = new JTextField("6",2);
		numberOfPoolPanel.add(numberOfPoolLabel);
		numberOfPoolPanel.add(numberOfPoolTextField);
		paramPanel.add(numberOfPoolPanel);
		poolOfKidsCheckBox = new JCheckBox("Créer une pool enfant",true);
		poolOfWomenCheckBox = new JCheckBox("Créer une pool de fille");
		paramPanel.add(poolOfKidsCheckBox);
		paramPanel.add(poolOfWomenCheckBox);
		
		numberOfPointForMatchWinPanel = new JPanel();
		numberOfpointForMatchWinLabel = new JLabel("Nombre de point par match gagne : ");
		numberOfpointForMatchWinTextField = new JTextField(""+Tournament.POINTS_FOR_MATCH_WON ,1);
		numberOfPointForMatchWinPanel.add(numberOfpointForMatchWinLabel);
		numberOfPointForMatchWinPanel.add(numberOfpointForMatchWinTextField);
		
		numberOfPointForMatchEqualityPanel = new JPanel();
		numberOfpointForMatchEqualityLabel = new JLabel("Nombre de point par match egalite :");
		numberOfpointForMatchEqualityTextField = new JTextField(""+Tournament.POINTS_FOR_MATCH_EQUALITY ,1);
		numberOfPointForMatchEqualityPanel.add(numberOfpointForMatchEqualityLabel);
		numberOfPointForMatchEqualityPanel.add(numberOfpointForMatchEqualityTextField);
		
		numberOfPointForMatchLostPanel = new JPanel();
		numberOfpointForMatchLostLabel = new JLabel("Nombre de point par match perdu : ");
		numberOfpointForMatchLostTextField = new JTextField(""+Tournament.POINTS_FOR_MATCH_LOST ,1);
		numberOfPointForMatchLostPanel.add(numberOfpointForMatchLostLabel);
		numberOfPointForMatchLostPanel.add(numberOfpointForMatchLostTextField);

		paramPanel.add(numberOfPointForMatchWinPanel);
		paramPanel.add(numberOfPointForMatchEqualityPanel);
		paramPanel.add(numberOfPointForMatchLostPanel);
		
		container.add(paramPanel,BorderLayout.CENTER);
		
		// Create lower Panel : contains Validate and Cancel Button
		lowerPanel = new JPanel();
		validateButton = new JButton("Valider");
		validateButton.addActionListener(generateTournamentActionListener);
		lowerPanel.add(validateButton);
		cancelButton = new JButton("Annuler");
		cancelButton.addActionListener(generateTournamentActionListener);
		lowerPanel.add(cancelButton);
		container.add(lowerPanel, BorderLayout.SOUTH);
		
		setResizable(false);
		pack();
	}
	
	class GenerateTournamentActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == validateButton){
				int numberOfTerrain;
				// Check number of pool mandatory
				if(numberOfPoolTextField.getText().trim().equals("")){
					JOptionPane.showMessageDialog(mainScreen, "Nombre de terrains obligatoire!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
					return;
				}
				// validate the number of terrain 
				try{
					numberOfTerrain = Integer.parseInt(numberOfPoolTextField.getText().trim());
					if(numberOfTerrain <= 0){
						JOptionPane.showMessageDialog(mainScreen, "Nombre de terrains invalide!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(mainScreen, "Nombre de terrains invalide!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Check the points for match won
				int pointsForMatchWon;
				// is number present?
				if(numberOfpointForMatchWinTextField.getText().trim().equals("")){
					JOptionPane.showMessageDialog(mainScreen, "Nombre de points par match gagne obligatoire!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
					return;
				}
				// number of points valide ?
				try{
					pointsForMatchWon = Integer.parseInt(numberOfpointForMatchWinTextField.getText().trim());
					if(pointsForMatchWon <= 0){
						JOptionPane.showMessageDialog(mainScreen, "Nombre de point par match gagne invalide!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(mainScreen, "Nombre de point par match gagne invalide!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Check the points for match equality
				int pointsForMatchEquality;
				// is number present?
				if(numberOfpointForMatchEqualityTextField.getText().trim().equals("")){
					JOptionPane.showMessageDialog(mainScreen, "Nombre de points par match egalite obligatoire!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
					return;
				}
				// number of points valide ?
				try{
					pointsForMatchEquality = Integer.parseInt(numberOfpointForMatchEqualityTextField.getText().trim());
					if(pointsForMatchEquality < 0){
						JOptionPane.showMessageDialog(mainScreen, "Nombre de points par match egalite invalide!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(mainScreen, "Nombre de points par match egalite invalide!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
				// Check the points for match lost
				int pointsForMatchLost;
				// is number present?
				if(numberOfpointForMatchLostTextField.getText().trim().equals("")){
					JOptionPane.showMessageDialog(mainScreen, "Nombre de points par match perdu obligatoire!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
					return;
				}
				// number of points valide ?
				try{
					pointsForMatchLost = Integer.parseInt(numberOfpointForMatchLostTextField.getText().trim());
					if(pointsForMatchLost < 0){
						JOptionPane.showMessageDialog(mainScreen, "Nombre de points par match perdu invalide!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(mainScreen, "Nombre de points par match perdu invalide!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
				// Check if enough terrain for pool of kids AND pool of women
				if(numberOfTerrain== 1 & poolOfKidsCheckBox.isSelected() & poolOfWomenCheckBox.isSelected()){
					JOptionPane.showMessageDialog(mainScreen, "Pas assez de terrains pour faire une pool enfant et femme!! \n ", "Erreur",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Reactivate the main Screen
				mainScreen.setEnabled(true);
				mainScreen.buildPoolsScreen(numberOfTerrain, poolOfKidsCheckBox.isSelected(), poolOfWomenCheckBox.isSelected());
				Tournament.POINTS_FOR_MATCH_WON = pointsForMatchWon;
				Tournament.POINTS_FOR_MATCH_EQUALITY = pointsForMatchEquality;
				Tournament.POINTS_FOR_MATCH_LOST = pointsForMatchLost;
				
				Tournament.getInstance().generatePools(numberOfTerrain, poolOfKidsCheckBox.isSelected(), poolOfWomenCheckBox.isSelected());
				System.out.println("j'ai cliqué sur OK");
				// desactivate the inscription buttons
				mainScreen.disableButton();
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
	}
	
	class GenerateTournamentWindowAdapter extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent we) {
			mainScreen.setEnabled(true);
		}
	}
	
}

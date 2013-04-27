package be.vodelee.ultimate.ihm;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import be.vodelee.ultimate.core.Team;
import be.vodelee.ultimate.core.Tournament;

public class PoolPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private MainScreen mainScreen;
	
	private JPanel cardPanel;
	private CardLayout cardLayout;
	private JScrollPane classementScrollPane;
	private ClassementTableModel classementTableModel;
	private UltimateJTable classementTable;
	private JScrollPane matchListScrollPane;
	private MatchListTableModel matchListTableModel;
	private UltimateJTable  matchListTable;
	
	private EncodeMatchScreen encodeMatchScreen;
	private UpdateMatchScreen updateMatchScreen;
	
	private JPanel controlPanel;
	private JButton showClassementButton;
	private JButton showMatchListButton;
	private JButton encodeMatchButton;
	private JButton updateMatchButton;
	
	private int poolNumber;
	private int poolCategoy;
	
	public PoolPanel(MainScreen ms, int poolNumber, int poolCategory) {
		super();
		this.mainScreen = ms;
		this.poolNumber = poolNumber;
		this.poolCategoy = poolCategory;
		setLayout(new BorderLayout());

		// Build and add the card panel
		cardPanel = new JPanel();
		cardLayout = new CardLayout();
		cardPanel.setLayout(cardLayout);
		
		// Panel for classement
		classementTableModel = new ClassementTableModel(poolNumber, poolCategory);
		classementTable = new UltimateJTable(classementTableModel, 18);
		classementScrollPane = new JScrollPane(classementTable);
		cardPanel.add(classementScrollPane,"classement");

		// Panel for match list
		matchListTableModel = new MatchListTableModel(poolNumber, poolCategory);
		matchListTable = new UltimateJTable(matchListTableModel, 25);
		matchListScrollPane = new JScrollPane(matchListTable);
		cardPanel.add(matchListScrollPane,"matchList");
		add(cardPanel);
		
		
		
		
		// Build and add the control Panel
		controlPanel = new JPanel();
		showClassementButton = new JButton("Classement");
		showClassementButton.addActionListener(this);
		controlPanel.add(showClassementButton);
		showMatchListButton = new JButton("Liste des matchs");
		showMatchListButton.addActionListener(this);
		controlPanel.add(showMatchListButton);
		encodeMatchButton = new JButton("Encoder match");
		encodeMatchButton.addActionListener(this);
		controlPanel.add(encodeMatchButton);
		updateMatchButton = new JButton("Modifier résultat d'un match");
		updateMatchButton.addActionListener(this);
		controlPanel.add(updateMatchButton);
		add(controlPanel, BorderLayout.SOUTH);
		
	}

	public int getPoolNumber() {
		return poolNumber;
	}

	public int getPoolCategoy() {
		return poolCategoy;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == showClassementButton){
			cardLayout.show(cardPanel, "classement");
			System.out.println("Afficher le classement");
			classementTableModel.fireTableDataChanged();
		}
		if(e.getSource() == showMatchListButton){
			cardLayout.show(cardPanel, "matchList");
			System.out.println("Afficher la liste des matchs");
			matchListTableModel.fireTableDataChanged();
		}
		if(e.getSource() == encodeMatchButton){
			if(poolCategoy == Team.ADULT_CATEGORY){
				if(Tournament.getInstance().getPoolMap().get(poolNumber).getMatchNotPlayedList().isEmpty()){
					JOptionPane.showMessageDialog(this, "Tous les matchs de cette pool ont été encodés", "Action impossible", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			else{
				if(poolCategoy == Team.KID_CATEGORY){
					if(Tournament.getInstance().getKidPool().getMatchNotPlayedList().isEmpty()){
						JOptionPane.showMessageDialog(this, "Tous les matchs de cette pool ont été encodés", "Action impossible", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				else{ // Women category
					if(Tournament.getInstance().getWomenPool().getMatchNotPlayedList().isEmpty()){
						JOptionPane.showMessageDialog(this, "Tous les matchs de cette pool ont été encodés", "Action impossible", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}
			encodeMatchScreen =new EncodeMatchScreen(mainScreen,this);
			encodeMatchScreen.setLocationRelativeTo(this);
			encodeMatchScreen.setVisible(true);
			System.out.println("Encode un match");
			return;
		}
		if(e.getSource() == updateMatchButton){
			if(poolCategoy == Team.ADULT_CATEGORY){
				if(Tournament.getInstance().getPoolMap().get(poolNumber).getMatchPlayedList().isEmpty()){
					JOptionPane.showMessageDialog(this, "Aucun match de cette pool n'a déjà été encodé", "Action impossible", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			else{
				if(poolCategoy == Team.KID_CATEGORY){
					if(Tournament.getInstance().getKidPool().getMatchPlayedList().isEmpty()){
						JOptionPane.showMessageDialog(this, "Aucun match de cette pool n'a déjà été encodé", "Action impossible", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				else{ // Women category
					if(Tournament.getInstance().getWomenPool().getMatchPlayedList().isEmpty()){
						JOptionPane.showMessageDialog(this, "Aucun match de cette pool n'a déjà été encodé", "Action impossible", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}
			updateMatchScreen = new UpdateMatchScreen(mainScreen, this);
			updateMatchScreen.setLocationRelativeTo(this);
			updateMatchScreen.setVisible(true);
			System.out.println("Modifier un match");
			return;
		}
	}
	
	public void updatePoolTables(){
		classementTableModel.fireTableDataChanged();
		matchListTableModel.fireTableDataChanged();
	}
}

package be.vodelee.ultimate.ihm;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import be.vodelee.ultimate.core.Pool;
import be.vodelee.ultimate.core.Team;
import be.vodelee.ultimate.core.Tournament;
import be.vodelee.ultimate.persistence.TournamentLoader;
import be.vodelee.ultimate.persistence.TournamentRecorder;


public class MainScreen extends JFrame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	private Container container;

	private JMenuBar jmb;

	private JMenu jmFile;
	private JMenuItem jmiNew;
	private JMenuItem jmiOpen;
	private JMenuItem jmiSave;
	private JMenuItem jmiSaveAs;
	private JMenuItem jmiQuit;

	private JMenu jmHelp;
	private JMenuItem jmiAbout;

	private JTabbedPane jtp;
	private JPanel inscriptionPanel;
	private JLabel totalTeamNbr;
	private JPanel inscriptionButtonsPanel;
	private JButton newTeamButton;
	private JButton updateTeamButton;
	private JButton deleteTeamButton;
	private JButton generateTournamentButton;
	private JButton backToInscriptionButton;
	private TeamTableModel teamTableModel;
	private UltimateJTable teamTable;
	private JScrollPane jspTeamTable;

	private AddTeamScreen addTeamScreen;
	private UpdateTeamScreen updateTeamScreen;
	private DeleteTeamScreen deleteTeamScreen;
	private GenerateTounamentScreen generateTounamentScreen;

	private JPanel kidsPoolPanel;
	private JPanel womenPoolPanel;
	private JPanel[] adultPoolTabPanel;

	public MainScreen() {
		super("Ultimate Soccer");
		container = getContentPane();
		// Create and add the menu Bar
		jmb = new JMenuBar();
		container.add(jmb,BorderLayout.NORTH);
		// Create and add the File menu + KeyStrokes + ActionListener
		jmFile = new JMenu("Fichier");
		jmb.add(jmFile);
		jmiNew = new JMenuItem("Nouveau tournoi");
		jmiNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		jmiNew.addActionListener(this);
		jmiOpen= new JMenuItem("Ouvrir tournoi");
		jmiOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		jmiOpen.addActionListener(this);
		jmiSave = new JMenuItem("Enregistrer");
		jmiSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		jmiSave.addActionListener(this);
		jmiSaveAs = new JMenuItem("Enregistrer sous ...");
		jmiSaveAs.addActionListener(this);
		jmiQuit = new JMenuItem("Quitter");
		jmiQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		jmiQuit.addActionListener(this);
		jmFile.add(jmiNew);
		jmFile.add(jmiOpen);
		jmFile.add(jmiSave);
		jmFile.add(jmiSaveAs);
		jmFile.add(jmiQuit);
		// Create and add the credits menu
		jmHelp = new JMenu("Aide");
		jmb.add(jmHelp);
		jmiAbout = new JMenuItem("Crédits");
		jmiAbout.addActionListener(this);
		jmHelp.add(jmiAbout);


		// Create the tab Panes
		jtp = new JTabbedPane();
		inscriptionPanel = new JPanel();
		jtp.addTab("Inscription / Gestion équipe ", inscriptionPanel);
		container.add(jtp);

		// Build the inscriptionPane
		totalTeamNbr = new JLabel("Nombre d'équipe =0    Joueurs =0");
		totalTeamNbr.setFont(new Font("Serif",Font.BOLD|Font.ITALIC,35));
		inscriptionPanel.setLayout(new BorderLayout());
		inscriptionPanel.add(totalTeamNbr,BorderLayout.NORTH);
		// panel of inscriptionButtons
		inscriptionButtonsPanel = new JPanel();
		inscriptionButtonsPanel.setLayout(new GridLayout(8,1, 10, 10));
		inscriptionPanel.add(inscriptionButtonsPanel, BorderLayout.WEST);
		newTeamButton = new JButton("Ajouter une équipe");
		newTeamButton.addActionListener(this);
		inscriptionButtonsPanel.add(newTeamButton);
		updateTeamButton = new JButton("Modifier une équipe");
		updateTeamButton.addActionListener(this);
		inscriptionButtonsPanel.add(updateTeamButton);
		deleteTeamButton = new JButton("Supprimer une équipe");
		deleteTeamButton.addActionListener(this);
		inscriptionButtonsPanel.add(deleteTeamButton);
		generateTournamentButton = new JButton("Commencer le tournoi");
		generateTournamentButton.addActionListener(this);
		inscriptionButtonsPanel.add(generateTournamentButton);
		backToInscriptionButton = new JButton("Retourner dans la phase d'inscription");
		backToInscriptionButton.addActionListener(this);
		backToInscriptionButton.setEnabled(false);
		inscriptionButtonsPanel.add(backToInscriptionButton);
		// Table of Teams
		teamTableModel = new TeamTableModel();
		teamTable = new UltimateJTable(teamTableModel, 15);
		jspTeamTable = new JScrollPane(teamTable);
		inscriptionPanel.add(jspTeamTable, BorderLayout.CENTER);

		addWindowListener(this);
		
		pack();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	
	public void actionPerformed(ActionEvent e) {
		// listener for menu items
		if(e.getSource() == jmiNew){
			Tournament.getInstance().reset();
			updateTeamTable();
			System.out.println("J'ai cliqué sur le menu Nouveau");
			return;
		}
		if(e.getSource() == jmiOpen){
			System.out.println("J'ai cliqué sur le menu Ouvrir");
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setFileFilter(new XMLFilter());
			int choix  = fileChooser.showOpenDialog(this);
			if (choix == JFileChooser.APPROVE_OPTION){
				System.out.println("choisit Ouvrir");
				File file = fileChooser.getSelectedFile();
				String path = file.getAbsolutePath();
				TournamentLoader tournamentLoader = new TournamentLoader(path, this);
				int result = tournamentLoader.loadTournament();
				if(result == 1){
					JOptionPane.showMessageDialog(this, "Erreur dans le chargement du fichier", "Erreur", JOptionPane.ERROR_MESSAGE);
					return;
				}
				updateTeamTable();
				System.out.println("Chargement du tournoi avec le fichier "+ path);
			}
			else{ 
				System.out.println("choisit annuler");
			}
			return;
		}
		if(e.getSource() == jmiSave){
			System.out.println("J'ai cliqué sur le menu Enregistrer");
			if(Tournament.getInstance().getFileToRecord() == null){
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new XMLFilter());
				int choix  = fileChooser.showSaveDialog(this);
				if (choix == JFileChooser.APPROVE_OPTION){
					System.out.println("choisit Sauver");
					File file = fileChooser.getSelectedFile();
					String path = file.getAbsolutePath();
					new TournamentRecorder().recordTournament(path);
					Tournament.getInstance().setFileToRecord(path);
					System.out.println("Tournoi enregistré dans le fichier "+ path);
				}
				else{
					System.out.println("choisit annuler");
				}
			}
			else{
				new TournamentRecorder().recordTournament(Tournament.getInstance().getFileToRecord());
			}
			return;
		}
		if(e.getSource() == jmiSaveAs){
			System.out.println("J'ai cliqué sur le menu Enregistrer sous ...");
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new XMLFilter());
			int choix  = fileChooser.showSaveDialog(this);
			if (choix == JFileChooser.APPROVE_OPTION){
				System.out.println("choisit Sauver");
				File file = fileChooser.getSelectedFile();
				String path = file.getAbsolutePath();
				new TournamentRecorder().recordTournament(path);
				Tournament.getInstance().setFileToRecord(path);
				System.out.println("Tournoi enregistré dans le fichier "+ path);
			}
			else{
				System.out.println("choisit annuler");
			}
			return;
		}
		if(e.getSource() == jmiQuit){
			System.out.println("J'ai cliqué sur le menu Quitter");
			int answer = JOptionPane.showConfirmDialog(this,"Voulez-vous enregistrer avant de quitter ?", "Quitter",JOptionPane.YES_NO_CANCEL_OPTION);
			if(answer == JOptionPane.YES_OPTION){
				if(Tournament.getInstance().getFileToRecord() == null){
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileFilter(new XMLFilter());
					int choix  = fileChooser.showSaveDialog(this);
					if (choix == JFileChooser.APPROVE_OPTION){
						System.out.println("choisit Sauver");
						File file = fileChooser.getSelectedFile();
						String path = file.getAbsolutePath();
						new TournamentRecorder().recordTournament(path);
						Tournament.getInstance().setFileToRecord(path);
						System.out.println("Tournoi enregistré dans le fichier "+ path);
					}
					else{
						System.out.println("choisit annuler");
					}
				}
				else{
					new TournamentRecorder().recordTournament(Tournament.getInstance().getFileToRecord());
				}
				System.exit(0);
				return;	
			}
			if(answer == JOptionPane.NO_OPTION){
				System.exit(0);
				return;
			}
			if(answer == JOptionPane.CANCEL_OPTION){
				return;
			}
			
		}
		if(e.getSource() == jmiAbout){
			// TODO mettre une icone de bière
			JOptionPane.showInternalMessageDialog(container, "Ce programme a été developpé par saoulaaaaaaaaaaaaaaard Stefan. \n" +
					"N'oubliez pas de lui payer des pintes\n\n Ultimate Soccer (dernier vendredi du mois de juin) \n www.jeunessevodelee.be", 
					"Crédits", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("J'ai cliqué sur le menu Credits");
			return;
		}
		// Listener for inscription panel
		if(e.getSource() == newTeamButton){
			addTeamScreen = new AddTeamScreen(this);
			addTeamScreen.setLocationRelativeTo(inscriptionPanel);
			addTeamScreen.setVisible(true);
			System.out.println("j'ai cliqué sur nouveau");
			return;
		}
		if(e.getSource() == updateTeamButton){
			// Test if there is a team to update
			if(Tournament.getInstance().getTeamMap().keySet().isEmpty()){
				JOptionPane.showMessageDialog(this, "Il n'y a encore aucune équipe!", "Erreur",JOptionPane.ERROR_MESSAGE);
				return;
			}
			else{
				updateTeamScreen = new UpdateTeamScreen(this);
				updateTeamScreen.setLocationRelativeTo(inscriptionPanel);
				updateTeamScreen.setVisible(true);
				System.out.println("j'ai cliqué sur modifier");
				return;
			}
		}
		if(e.getSource() == deleteTeamButton){
			// Test if there is a team to delete
			if(Tournament.getInstance().getTeamMap().keySet().isEmpty()){
				JOptionPane.showMessageDialog(this, "Il n'y a encore aucune équipe!", "Erreur",JOptionPane.ERROR_MESSAGE);
				return;
			}
			deleteTeamScreen = new DeleteTeamScreen(this);
			deleteTeamScreen.setLocationRelativeTo(inscriptionPanel);
			deleteTeamScreen.setVisible(true);
			System.out.println("j'ai cliqué sur supprrimer");
			return;
		}
		if(e.getSource() == generateTournamentButton){
			generateTounamentScreen = new GenerateTounamentScreen(this);
			generateTounamentScreen.setLocationRelativeTo(inscriptionPanel);
			generateTounamentScreen.setVisible(true);
			Tournament.getInstance().setTournamentStep(Tournament.POOLS_STEP);
			System.out.println("jai cliqué sur Commencer le tournoi");
		}
		if(e.getSource() == backToInscriptionButton){
			int answer = JOptionPane.showConfirmDialog(this, "Voulez-vous retouner dans la phase d'inscription?\n Les données des pools seront perdues", 
					"Recommencer tournoi", JOptionPane.YES_NO_OPTION);
			if(answer == JOptionPane.YES_OPTION){
				// Reactivate the inscription buttons
				enableButton();
				if(adultPoolTabPanel != null){
					for(int i = 0; i < adultPoolTabPanel.length; i++){
						jtp.remove(adultPoolTabPanel[i]);
					}
				}
				jtp.remove(kidsPoolPanel);
				jtp.remove(womenPoolPanel);	
				Tournament.getInstance().setKidPool(null);
				Tournament.getInstance().setWomenPool(null);
				Tournament.getInstance().setPoolMap(null);
				Tournament.getInstance().resetTeamsStats();
				Tournament.getInstance().setTournamentStep(Tournament.INSCRIPTION_STEP);
				Pool.poolNumberCounter = 1;
			}
		}
	}

	public void updateTeamTable(){
		teamTableModel.fireTableDataChanged();
		// Count the number of player
		int numberOfPlayer = 0;
		for(Team t:Tournament.getInstance().getTeamMap().values()){
			numberOfPlayer = numberOfPlayer + t.getNumberOfPlayer();
		}
		// update the label
		totalTeamNbr.setText("Nombre d'équipe =" + teamTableModel.getRowCount()+ "    Joueurs =" + numberOfPlayer);
	}

	public void buildPoolsScreen(int numberOfPool, boolean poolOfKids, boolean poolOfWomen){
		int numberOfAdultPool = numberOfPool;
		if(poolOfKids) numberOfAdultPool--;
		if(poolOfWomen) numberOfAdultPool--;
		
		adultPoolTabPanel = new JPanel[numberOfAdultPool];
		int j = 0;
		for(int i=0 ; i < numberOfAdultPool ; i++){
			adultPoolTabPanel[i] = new PoolPanel(this, (i+1), Team.ADULT_CATEGORY);
			jtp.addTab("Pool "+ (i+1), adultPoolTabPanel[i]);
			j++;
		}
		if(poolOfKids){
			kidsPoolPanel = new PoolPanel(this,j+1, Team.KID_CATEGORY);
			jtp.addTab("Pool enfant", kidsPoolPanel);
			j++;
		}
		if(poolOfWomen){
			womenPoolPanel = new PoolPanel(this, j+1, Team.WOMEN_CATEGORY);
			jtp.addTab("Pool femme", womenPoolPanel);
		}
		pack();
	}

	public void disableButton(){
		newTeamButton.setEnabled(false);
		updateTeamButton.setEnabled(false);
		deleteTeamButton.setEnabled(false);
		generateTournamentButton.setEnabled(false);
		backToInscriptionButton.setEnabled(true);
	}

	public void enableButton(){
		newTeamButton.setEnabled(true);
		updateTeamButton.setEnabled(true);
		deleteTeamButton.setEnabled(true);
		generateTournamentButton.setEnabled(true);
		backToInscriptionButton.setEnabled(false);
	}

	class XMLFilter extends FileFilter{
		@Override
		public boolean accept(File f) {
			String nomFichier = f.getName();
			// On n'affiche pas les fichiers cache unix
			if(nomFichier.startsWith(".")){
				return false;
			}
			StringTokenizer st = new StringTokenizer(nomFichier,".");
			if(st.countTokens()==1){
				return true;
			}
			else{
				String extension = null;
				while(st.hasMoreTokens()){
					extension = st.nextToken();
				}
				if(extension.compareTo("xml")==0 ||
						extension.compareTo("XML")==0){
					return true;
				}
				else{
					return false;
				}
			}
		}
		@Override
		public String getDescription() {
			return "*.xml ; *.XML";
		}
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		int answer = JOptionPane.showConfirmDialog(this,"Voulez-vous enregistrer avant de quitter ?", "Quitter",JOptionPane.YES_NO_CANCEL_OPTION);
		if(answer == JOptionPane.YES_OPTION){
			if(Tournament.getInstance().getFileToRecord() == null){
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new XMLFilter());
				int choix  = fileChooser.showSaveDialog(this);
				if (choix == JFileChooser.APPROVE_OPTION){
					System.out.println("choisit Sauver");
					File file = fileChooser.getSelectedFile();
					String path = file.getAbsolutePath();
					new TournamentRecorder().recordTournament(path);
					Tournament.getInstance().setFileToRecord(path);
					System.out.println("Tournoi enregistré dans le fichier "+ path);
				}
				else{
					System.out.println("choisit annuler");
				}
			}
			else{
				new TournamentRecorder().recordTournament(Tournament.getInstance().getFileToRecord());
			}
			System.exit(0);
			return;	
		}
		if(answer == JOptionPane.NO_OPTION){
			System.exit(0);
			return;
		}
		if(answer == JOptionPane.CANCEL_OPTION){
			return;
		}
		
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}
}

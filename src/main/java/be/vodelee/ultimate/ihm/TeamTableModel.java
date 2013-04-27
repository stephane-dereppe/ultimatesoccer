package be.vodelee.ultimate.ihm;

import javax.swing.table.AbstractTableModel;

import be.vodelee.ultimate.core.Team;
import be.vodelee.ultimate.core.Tournament;


public class TeamTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;

	public int getRowCount() {
		return Tournament.getInstance().getTeamMap().values().size();
	}
	
	public int getColumnCount() {
		return 4;
	}
	
	public Object getValueAt(int row, int col) {
		Object[] teamArray = Tournament.getInstance().getTeamMap().values().toArray();
		Team t = (Team)teamArray[row];
		if(col == 0) return t.getName();
		if(col == 1) return t.getCaptain();
		if(col == 2) return t.getNumberOfPlayer();
		String category;
		if(t.getCategory() == Team.ADULT_CATEGORY){
			category = "Adulte";
		}
		else{
			if(t.getCategory() == Team.KID_CATEGORY){
				category = "Enfant";
			}
			else{
				// Category women
				category = "Fille";
			}
		}
		if(col == 3) return category;
		return null;
	}
	
	@Override
	public String getColumnName(int colNbr) {
		if (colNbr == 0) return "Equipe";
		if(colNbr == 1) return "Capitaine";
		if (colNbr == 2) { 
			return "Nombre joueur";
		}
		else{
			return "Catégorie";
		}
	}
}

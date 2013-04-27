package be.vodelee.ultimate.ihm;

import javax.swing.table.AbstractTableModel;

import be.vodelee.ultimate.core.Team;
import be.vodelee.ultimate.core.Tournament;

public class ClassementTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;

	private int poolNumber;
	private int poolCategory;

	public ClassementTableModel(int poolNumber, int poolCategory){
		this.poolNumber = poolNumber;
		this.poolCategory = poolCategory;
	}

	public int getColumnCount() {
		return 9;
	}

	public int getRowCount() {
		if(poolCategory == Team.ADULT_CATEGORY){
			if(Tournament.getInstance().getPoolMap() != null){
				return Tournament.getInstance().getPoolMap().get(Integer.valueOf(poolNumber)).getTeamSet().size();	
			}
			else{
				return 0;
			}
		}
		if(poolCategory == Team.KID_CATEGORY){
			if(Tournament.getInstance().getKidPool() != null){
				return Tournament.getInstance().getKidPool().getTeamSet().size();	
			}
			else{
				return 0;
			}
		}
		if(poolCategory == Team.WOMEN_CATEGORY){
			if(Tournament.getInstance().getWomenPool() != null){
				return Tournament.getInstance().getWomenPool().getTeamSet().size();	
			}
			else{
				return 0;
			}
		}
		return 0;
	}

	public Object getValueAt(int row, int col) {
		if(poolCategory == Team.ADULT_CATEGORY){
			Object[] teamArray = Tournament.getInstance().getPoolMap().get(Integer.valueOf(poolNumber)).getTeamSet().toArray();
			Team t = (Team)teamArray[teamArray.length-1-row];
			if(col == 0) return row+1;
			if(col == 1) return t.getName();
			if(col == 2) return t.getNumberOfMatchPlayed();
			if(col == 3) return t.getNumberOfMatchWon();
			if(col == 4) return t.getNumberOfMatchLost();
			if(col == 5) return t.getNumberOfMatchEquality();
			if(col == 6) return t.getNumberOfGoalFor();
			if(col == 7) return t.getNumberOfGoalAgainst();
			if(col == 8) return t.getNumberOfPoints();
		}
		if(poolCategory == Team.KID_CATEGORY){
			Object[] teamArray = Tournament.getInstance().getKidPool().getTeamSet().toArray();
			Team t = (Team)teamArray[teamArray.length-1-row];
			if(col == 0) return row+1;
			if(col == 1) return t.getName();
			if(col == 2) return t.getNumberOfMatchPlayed();
			if(col == 3) return t.getNumberOfMatchWon();
			if(col == 4) return t.getNumberOfMatchLost();
			if(col == 5) return t.getNumberOfMatchEquality();
			if(col == 6) return t.getNumberOfGoalFor();
			if(col == 7) return t.getNumberOfGoalAgainst();
			if(col == 8) return t.getNumberOfPoints();
		}
		if(poolCategory == Team.WOMEN_CATEGORY){
			Object[] teamArray = Tournament.getInstance().getWomenPool().getTeamSet().toArray();
			Team t = (Team)teamArray[teamArray.length-1-row];
			if(col == 0) return row+1;
			if(col == 1) return t.getName();
			if(col == 2) return t.getNumberOfMatchPlayed();
			if(col == 3) return t.getNumberOfMatchWon();
			if(col == 4) return t.getNumberOfMatchLost();
			if(col == 5) return t.getNumberOfMatchEquality();
			if(col == 6) return t.getNumberOfGoalFor();
			if(col == 7) return t.getNumberOfGoalAgainst();
			if(col == 8) return t.getNumberOfPoints();
		}
		return null;
	}
	@Override
	public String getColumnName(int colNbr) {
		if(colNbr == 0) return "";
		if(colNbr == 1) return "";
		if(colNbr == 2) return "J";
		if(colNbr == 3) return "G";
		if(colNbr == 4) return "P";
		if(colNbr == 5) return "N";
		if(colNbr == 6) return "BP";
		if (colNbr == 7) {
			return "BC";
		}
		else{
			return "Points";
		}
	}
}

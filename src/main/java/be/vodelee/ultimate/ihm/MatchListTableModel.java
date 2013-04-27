package be.vodelee.ultimate.ihm;

import javax.swing.table.AbstractTableModel;

import be.vodelee.ultimate.core.Match;
import be.vodelee.ultimate.core.Team;
import be.vodelee.ultimate.core.Tournament;

public class MatchListTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;

	private int poolNumber;
	private int poolCategory;
	
	public MatchListTableModel(int poolNumber, int poolCategory){
		this.poolNumber = poolNumber;
		this.poolCategory = poolCategory;
	}
	
	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		if(poolCategory == Team.ADULT_CATEGORY){
			if(Tournament.getInstance().getPoolMap() != null){
				return Tournament.getInstance().getPoolMap().get(Integer.valueOf(poolNumber)).getMatchList().size();	
			}
			else{
				return 0;
			}
		}
		if(poolCategory == Team.KID_CATEGORY){
			if(Tournament.getInstance().getKidPool() != null){
				return Tournament.getInstance().getKidPool().getMatchList().size();	
			}
			else{
				return 0;
			}
		}
		if(poolCategory == Team.WOMEN_CATEGORY){
			if(Tournament.getInstance().getWomenPool() != null){
				return Tournament.getInstance().getWomenPool().getMatchList().size();	
			}
			else{
				return 0;
			}
		}
		return 0;
	}

	public Object getValueAt(int row, int col) {
		if(poolCategory == Team.ADULT_CATEGORY){
			Object[] matchArray = Tournament.getInstance().getPoolMap().get(Integer.valueOf(poolNumber)).getMatchList().toArray();
			Match m = (Match)matchArray[row];
			if(col == 0) return m.getTeam1().toString();
			if(col == 1) {
				if(m.isPlayed()){
					return (m.getGoalTeam1() + " - " + m.getGoalTeam2());
				}
				else{
					return " - ";
				}
			}
			if(col == 2) return m.getTeam2().toString();
		}
		if(poolCategory == Team.KID_CATEGORY){
			Object[] matchArray = Tournament.getInstance().getKidPool().getMatchList().toArray();
			Match m = (Match)matchArray[row];
			if(col == 0) return m.getTeam1().toString();
			if(col == 1) {
				if(m.isPlayed()){
					return (m.getGoalTeam1() + " - " + m.getGoalTeam2());
				}
				else{
					return " - ";
				}
			}
			if(col == 2) return m.getTeam2().toString();
		}
		if(poolCategory == Team.WOMEN_CATEGORY){
			Object[] matchArray = Tournament.getInstance().getWomenPool().getMatchList().toArray();
			Match m = (Match)matchArray[row];
			if(col == 0) return m.getTeam1().toString();
			if(col == 1) {
				if(m.isPlayed()){
					return (m.getGoalTeam1() + " - " + m.getGoalTeam2());
				}
				else{
					return " - ";
				}
			}
			if(col == 2) return m.getTeam2().toString();
		}
		return null;
	}
	
	@Override
	public String getColumnName(int colNbr) {
		if(colNbr == 0) return "Equipe 1";
		if(colNbr == 1) {
			 return "Résultat";
		}
		else{
			return "Equipe 2";
		}
	}
}

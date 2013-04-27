package be.vodelee.ultimate.core;


public class Match {

	private Team team1;
	private Team team2;
	
	private Integer goalTeam1;
	private Integer goalTeam2;

	private boolean isPlayed;
	/**
	 * Constructor for match not yet played
	 * @param team1 first team
	 * @param team2 second team
	 */
	public Match(Team team1, Team team2){
		this.team1 = team1;
		this.team2 = team2;
		this.isPlayed = false;
	}
	
	/**
	 * Constructor for match played - while loading the tournament
	 * @param team1 first team
	 * @param team2 second team
	 * @param goalTeam1 goal scored by team 1
	 * @param goalTeam2 goal scored by team 2
	 */
	public Match(Team team1, Team team2, int goalTeam1, int goalTeam2){
		this.team1 = team1;
		this.team2 = team2;
		this.goalTeam1 = goalTeam1;
		this.goalTeam2 = goalTeam2;
		this.isPlayed = true;
	}
	
	
	
	public boolean isPlayed() {
		return isPlayed;
	}

	public Team getTeam1() {
		return team1;
	}

	public Team getTeam2() {
		return team2;
	}

	public Integer getGoalTeam1() {
		return goalTeam1;
	}

	public Integer getGoalTeam2() {
		return goalTeam2;
	}

	/**
	 * Encode the result for the match
	 * @param goalTeam1 goal scored by team 1
	 * @param goalTeam2 goal scored by team 2
	 */
	public void encodeResult(int goalTeam1, int goalTeam2){
		this.goalTeam1 = goalTeam1;
		this.goalTeam2 = goalTeam2;
		this.isPlayed = true;
	
		// Update the team model
		// Team 1 has won
		if(goalTeam1 > goalTeam2){
			// Team1
			team1.setNumberOfGoalFor(team1.getNumberOfGoalFor()+goalTeam1);
			team1.setNumberOfGoalAgainst(team1.getNumberOfGoalAgainst()+goalTeam2);
			team1.setNumberOfMatchWon(team1.getNumberOfMatchWon()+1);
			team1.setNumberOfMatchPlayed(team1.getNumberOfMatchPlayed()+1);
			team1.setNumberOfPoints(team1.getNumberOfPoints()+Tournament.POINTS_FOR_MATCH_WON);
			// Team2
			team2.setNumberOfGoalFor(team2.getNumberOfGoalFor()+goalTeam2);
			team2.setNumberOfGoalAgainst(team2.getNumberOfGoalAgainst()+goalTeam1);
			team2.setNumberOfMatchLost(team2.getNumberOfMatchLost()+1);
			team2.setNumberOfMatchPlayed(team2.getNumberOfMatchPlayed()+1);
			team2.setNumberOfPoints(team2.getNumberOfPoints()+Tournament.POINTS_FOR_MATCH_LOST);
			
			isPlayed = true;
			return;
		}
		// Team 2 has won
		if(goalTeam1 < goalTeam2){
			// Team2
			team2.setNumberOfGoalFor(team2.getNumberOfGoalFor()+goalTeam2);
			team2.setNumberOfGoalAgainst(team2.getNumberOfGoalAgainst()+goalTeam1);
			team2.setNumberOfMatchWon(team2.getNumberOfMatchWon()+1);
			team2.setNumberOfMatchPlayed(team2.getNumberOfMatchPlayed()+1);
			team2.setNumberOfPoints(team2.getNumberOfPoints()+Tournament.POINTS_FOR_MATCH_WON);
			// Team1
			team1.setNumberOfGoalFor(team1.getNumberOfGoalFor()+goalTeam1);
			team1.setNumberOfGoalAgainst(team1.getNumberOfGoalAgainst()+goalTeam2);
			team1.setNumberOfMatchLost(team1.getNumberOfMatchLost()+1);
			team1.setNumberOfMatchPlayed(team1.getNumberOfMatchPlayed()+1);
			team1.setNumberOfPoints(team1.getNumberOfPoints()+Tournament.POINTS_FOR_MATCH_LOST);
			
			isPlayed = true;
			return;
		}
		// Equality
		if(goalTeam1 == goalTeam2){
			// Team1
			team1.setNumberOfGoalFor(team1.getNumberOfGoalFor()+goalTeam1);
			team1.setNumberOfGoalAgainst(team1.getNumberOfGoalAgainst()+goalTeam2);
			team1.setNumberOfMatchEquality(team1.getNumberOfMatchEquality()+1);
			team1.setNumberOfMatchPlayed(team1.getNumberOfMatchPlayed()+1);
			team1.setNumberOfPoints(team1.getNumberOfPoints()+Tournament.POINTS_FOR_MATCH_EQUALITY);
			// Team2
			team2.setNumberOfGoalFor(team2.getNumberOfGoalFor()+goalTeam2);
			team2.setNumberOfGoalAgainst(team2.getNumberOfGoalAgainst()+goalTeam1);
			team2.setNumberOfMatchEquality(team2.getNumberOfMatchEquality()+1);
			team2.setNumberOfMatchPlayed(team2.getNumberOfMatchPlayed()+1);
			team2.setNumberOfPoints(team2.getNumberOfPoints()+Tournament.POINTS_FOR_MATCH_EQUALITY);
			
			isPlayed = true;
			return;
		}
	}
	
	public void updateResult(int newGoalTeam1, int newGoalTeam2){
		
		assert isPlayed;
		
		team1.setNumberOfGoalFor(team1.getNumberOfGoalFor()-this.goalTeam1 + newGoalTeam1);
		team1.setNumberOfGoalAgainst(team1.getNumberOfGoalAgainst()- this.goalTeam2 + newGoalTeam2);
		
		team2.setNumberOfGoalFor(team2.getNumberOfGoalFor()-this.goalTeam2 + newGoalTeam2);
		team2.setNumberOfGoalAgainst(team2.getNumberOfGoalAgainst()- this.goalTeam1 + newGoalTeam1);
		
		// team 1 has won with old result
		if(this.goalTeam1 > this.goalTeam2){
			// now team1 win
			if(newGoalTeam1 > newGoalTeam2){
				this.goalTeam1 = newGoalTeam1;
				this.goalTeam2 = newGoalTeam2;
				return;
			}
			// now team2 win
			if(newGoalTeam1 < newGoalTeam2){
				team1.setNumberOfMatchWon(team1.getNumberOfMatchWon() -1);
				team1.setNumberOfMatchLost(team1.getNumberOfMatchLost() + 1);
				team1.setNumberOfPoints(team1.getNumberOfPoints()-Tournament.POINTS_FOR_MATCH_WON);
				
				team2.setNumberOfMatchWon(team2.getNumberOfMatchWon() +1);
				team2.setNumberOfMatchLost(team2.getNumberOfMatchLost() -1);
				team2.setNumberOfPoints(team2.getNumberOfPoints()+Tournament.POINTS_FOR_MATCH_WON);
				
				this.goalTeam1 = newGoalTeam1;
				this.goalTeam2 = newGoalTeam2;
				return;
			}
			// now equality
			if(newGoalTeam1 == newGoalTeam2){
				team1.setNumberOfMatchWon(team1.getNumberOfMatchWon() -1);
				team1.setNumberOfMatchEquality(team1.getNumberOfMatchEquality() + 1);
				team1.setNumberOfPoints(team1.getNumberOfPoints()-Tournament.POINTS_FOR_MATCH_WON+Tournament.POINTS_FOR_MATCH_EQUALITY);

				team2.setNumberOfMatchEquality(team2.getNumberOfMatchEquality() +1);
				team2.setNumberOfMatchLost(team2.getNumberOfMatchLost() -1);
				team2.setNumberOfPoints(team2.getNumberOfPoints()+Tournament.POINTS_FOR_MATCH_EQUALITY);
				
				this.goalTeam1 = newGoalTeam1;
				this.goalTeam2 = newGoalTeam2;
				return;
			}
			
		}
		// team 2 has won with old result
		if(this.goalTeam1 < this.goalTeam2){
			// now team2 win
			if(newGoalTeam1 < newGoalTeam2){
				this.goalTeam1 = newGoalTeam1;
				this.goalTeam2 = newGoalTeam2;
				return;
			}
			// now team1 win
			if(newGoalTeam1 > newGoalTeam2){
				team1.setNumberOfMatchWon(team1.getNumberOfMatchWon() +1);
				team1.setNumberOfMatchLost(team1.getNumberOfMatchLost() - 1);
				team1.setNumberOfPoints(team1.getNumberOfPoints()+Tournament.POINTS_FOR_MATCH_WON);
				
				team2.setNumberOfMatchWon(team2.getNumberOfMatchWon() -1);
				team2.setNumberOfMatchLost(team2.getNumberOfMatchLost() +1);
				team2.setNumberOfPoints(team2.getNumberOfPoints()-Tournament.POINTS_FOR_MATCH_WON);
				
				this.goalTeam1 = newGoalTeam1;
				this.goalTeam2 = newGoalTeam2;
				return;
			}
			// now equality
			if(newGoalTeam1 == newGoalTeam2){
				team1.setNumberOfMatchLost(team1.getNumberOfMatchLost() -1);
				team1.setNumberOfMatchEquality(team1.getNumberOfMatchEquality() + 1);
				team1.setNumberOfPoints(team1.getNumberOfPoints()+Tournament.POINTS_FOR_MATCH_EQUALITY);

				team2.setNumberOfMatchEquality(team2.getNumberOfMatchEquality() +1);
				team2.setNumberOfMatchWon(team2.getNumberOfMatchWon() -1);
				team2.setNumberOfPoints(team2.getNumberOfPoints()-Tournament.POINTS_FOR_MATCH_WON+Tournament.POINTS_FOR_MATCH_EQUALITY);
				
				this.goalTeam1 = newGoalTeam1;
				this.goalTeam2 = newGoalTeam2;
				return;
			}
			
		}
		// equality with old result
		if(this.goalTeam1 == this.goalTeam2){
			// now equality
			if(newGoalTeam1 == newGoalTeam2){
				this.goalTeam1 = newGoalTeam1;
				this.goalTeam2 = newGoalTeam2;
				return;
			}
			// now team1 win
			if(newGoalTeam1 > newGoalTeam2){
				team1.setNumberOfMatchWon(team1.getNumberOfMatchWon() +1);
				team1.setNumberOfMatchEquality(team1.getNumberOfMatchEquality() - 1);
				team1.setNumberOfPoints(team1.getNumberOfPoints()-Tournament.POINTS_FOR_MATCH_EQUALITY+Tournament.POINTS_FOR_MATCH_WON);
				
				team2.setNumberOfMatchEquality(team2.getNumberOfMatchEquality() -1);
				team2.setNumberOfMatchLost(team2.getNumberOfMatchLost() +1);
				team2.setNumberOfPoints(team2.getNumberOfPoints()-Tournament.POINTS_FOR_MATCH_EQUALITY);
				
				this.goalTeam1 = newGoalTeam1;
				this.goalTeam2 = newGoalTeam2;
				return;
			}
			// now team2 win
			if(newGoalTeam1 < newGoalTeam2){
				team1.setNumberOfMatchLost(team1.getNumberOfMatchLost() +1);
				team1.setNumberOfMatchEquality(team1.getNumberOfMatchEquality() - 1);
				team1.setNumberOfPoints(team1.getNumberOfPoints()-Tournament.POINTS_FOR_MATCH_EQUALITY);

				team2.setNumberOfMatchEquality(team2.getNumberOfMatchEquality() -1);
				team2.setNumberOfMatchWon(team2.getNumberOfMatchWon() +1);
				team2.setNumberOfPoints(team2.getNumberOfPoints()-Tournament.POINTS_FOR_MATCH_EQUALITY+Tournament.POINTS_FOR_MATCH_WON);
				
				this.goalTeam1 = newGoalTeam1;
				this.goalTeam2 = newGoalTeam2;
				return;
			}
			
		}
		
	}
	
	@Override
	public String toString() {
		if(isPlayed){
			return team1 + " " + goalTeam1 + " - " + goalTeam2 + " " + team2; 
		}
		else{
			return team1 + "  -  " + team2;	
		}
		
	}
}

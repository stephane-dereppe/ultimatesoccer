package be.vodelee.ultimate.core;

public class Team implements Comparable<Team>{

	/**
	 * Type of category adult, kid of women
	 */
	public final static int ADULT_CATEGORY = 0;
	public final static int KID_CATEGORY = 1;
	public final static int WOMEN_CATEGORY = 2;

	private String name;
	private String captain;
	private int category;
	private int numberOfPlayer;
	private int numberOfMatchPlayed;
	private int numberOfMatchWon;
	private int numberOfMatchLost;
	private int numberOfMatchEquality;
	private int numberOfGoalFor;
	private int numberOfGoalAgainst;
	private int numberOfPoints;

	/**
	 * Default constructor for the team class
	 * @param name the name of the team
	 * @param captain the captain's name of the team
	 * @param category the team's category
	 * @param numberOfPlayer the number of player of the team
	 */
	public Team(String name, String captain, int category, int numberOfPlayer) {
		this.name = new String(name.trim().toUpperCase());
		this.captain = new String(captain.trim());
		this.category = category;
		this.numberOfPlayer = numberOfPlayer;
	}
	/**
	 * Constructor for the team class once the inscriptions are finished
	 * @param name
	 * @param captain
	 * @param category
	 * @param numberOfPlayer
	 * @param numberOfMatchPlayed
	 * @param numberOfMatchWon
	 * @param numberOfMatchLost
	 * @param numberOfMatchEquality
	 * @param numberOfGoalFor
	 * @param numberOfGoalAgainst
	 * @param numberOfPoints
	 */
	public Team (String name, String captain, int category, int numberOfPlayer, int numberOfMatchPlayed, int numberOfMatchWon, 
			int numberOfMatchLost, int numberOfMatchEquality,int numberOfGoalFor, int numberOfGoalAgainst, int numberOfPoints){
		this.name = new String(name.trim().toUpperCase());
		this.captain = new String(captain.trim());
		this.category = category;
		this.numberOfPlayer = numberOfPlayer;
		this.numberOfMatchPlayed = numberOfMatchPlayed;
		this.numberOfMatchWon = numberOfMatchWon;
		this.numberOfMatchLost = numberOfMatchLost;
		this.numberOfMatchEquality = numberOfMatchEquality;
		this.numberOfGoalFor = numberOfGoalFor;
		this.numberOfGoalAgainst = numberOfGoalAgainst;
		this.numberOfPoints = numberOfPoints;
	}

	public String getName() {
		return name;
	}
	public String getCaptain() {
		return captain;
	}
	public int getCategory() {
		return category;
	}
	public int getNumberOfPlayer() {
		return numberOfPlayer;
	}
	public int getNumberOfMatchPlayed() {
		return numberOfMatchPlayed;
	}
	public int getNumberOfMatchWon() {
		return numberOfMatchWon;
	}
	public int getNumberOfMatchLost() {
		return numberOfMatchLost;
	}
	public int getNumberOfMatchEquality() {
		return numberOfMatchEquality;
	}
	public int getNumberOfGoalFor() {
		return numberOfGoalFor;
	}
	public int getNumberOfGoalAgainst() {
		return numberOfGoalAgainst;
	}
	public int getNumberOfPoints() {
		return numberOfPoints;
	}
	public void setNumberOfMatchPlayed(int numberOfMatchPlayed) {
		this.numberOfMatchPlayed = numberOfMatchPlayed;
	}
	public void setNumberOfMatchWon(int numberOfMatchWon) {
		this.numberOfMatchWon = numberOfMatchWon;
	}
	public void setNumberOfMatchLost(int numberOfMatchLost) {
		this.numberOfMatchLost = numberOfMatchLost;
	}
	public void setNumberOfMatchEquality(int numberOfMatchEquality) {
		this.numberOfMatchEquality = numberOfMatchEquality;
	}
	public void setNumberOfGoalFor(int numberOfGoalFor) {
		this.numberOfGoalFor = numberOfGoalFor;
	}
	public void setNumberOfGoalAgainst(int numberOfGoalAgainst) {
		this.numberOfGoalAgainst = numberOfGoalAgainst;
	}
	public void setNumberOfPoints(int numberOfPoints) {
		this.numberOfPoints = numberOfPoints;
	}
	@Override
	public String toString() {
		return name;
	}

	public int compareTo(Team team2) {
		// Test on the number of Points
		if(this.numberOfPoints > team2.numberOfPoints)
			return 1;
		if(this.numberOfPoints < team2.numberOfPoints)
			return -1;
		// Test on the number of match won
		if(this.numberOfMatchWon > team2.numberOfMatchWon)
			return 1;
		if(this.numberOfMatchWon < team2.numberOfMatchWon)
			return -1;
		// Test on goalaverage
		int goalAverageTeam1 = this.numberOfGoalFor - this.numberOfGoalAgainst;
		int goalAverageTeam2 = team2.numberOfGoalFor - team2.numberOfGoalAgainst;
		if(goalAverageTeam1 > goalAverageTeam2)
			return 1;
		if(goalAverageTeam1 < goalAverageTeam2)
			return -1;
		// Test on the name of the team
		if(this.toString().compareTo(team2.toString()) < 0){
			return 1;
		}
		if(this.toString().compareTo(team2.toString()) > 0){
			return -1;	
		}
		else{
			return 0;
		}
	}
	
	@Override
	public boolean equals(Object team2) {
		if (! (team2 instanceof Team)) return false;
		return this.toString().equals(team2.toString());
	}
	@Override
	public int hashCode() {
		return this.toString().length();
	}
}
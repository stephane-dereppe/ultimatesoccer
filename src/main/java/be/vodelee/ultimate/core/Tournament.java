package be.vodelee.ultimate.core;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Tournament {

	private static Tournament instance;

	public final static int INSCRIPTION_STEP = 0;
	public final static int POOLS_STEP = 1;

	public static int POINTS_FOR_MATCH_WON = 3;
	public static int POINTS_FOR_MATCH_EQUALITY = 1;
	public static int POINTS_FOR_MATCH_LOST = 0;
	
	private int tournamentStep;

	private Map<String,Team> teamMap;

	private String fileToRecord;

	private Pool kidPool;
	private Pool womenPool;
	private Map<Integer, Pool> poolMap;

	public Map<String, Team> getTeamMap() {
		return teamMap;
	}

	public Map<Integer, Pool> getPoolMap() {
		return poolMap;
	}

	public Pool getKidPool() {
		return kidPool;
	}

	public void setKidPool(Pool kidPool) {
		this.kidPool = kidPool;
	}

	public void setWomenPool(Pool womenPool) {
		this.womenPool = womenPool;
	}

	public void setPoolMap(Map<Integer, Pool> poolMap) {
		this.poolMap = poolMap;
	}

	public Pool getWomenPool() {
		return womenPool;
	}

	public void setTeamMap(Map<String, Team> teamMap) {
		this.teamMap = teamMap;
	}

	public static Tournament getInstance(){
		if(instance == null){
			instance = new Tournament();
		}
		return instance;
	}

	private Tournament() {
		tournamentStep = INSCRIPTION_STEP;
		teamMap = new TreeMap<String, Team>();
		fileToRecord = null;
	}

	public int getTournamentStep(){
		return tournamentStep;
	}

	public void setTournamentStep(int tournamentStep) {
		this.tournamentStep = tournamentStep;
	}

	public String getFileToRecord() {
		return fileToRecord;
	}

	public void setFileToRecord(String fileToRecord) {
		this.fileToRecord = fileToRecord;
	}

	public void reset(){
		teamMap = new TreeMap<String, Team>();
		fileToRecord = null;
		kidPool = null;
		womenPool =null;
		poolMap = null;
		Pool.poolNumberCounter = 1;
	}

	public void resetTeamsStats(){
		for(Team t : getTeamMap().values()){
			t.setNumberOfGoalAgainst(0);
			t.setNumberOfGoalFor(0);
			t.setNumberOfMatchEquality(0);
			t.setNumberOfMatchLost(0);
			t.setNumberOfMatchPlayed(0);
			t.setNumberOfMatchWon(0);
			t.setNumberOfPoints(0);
		}
	}
	
	public void generatePools(int numberOfPool, boolean isKidPool, boolean isWomenPool){
		int numberOfAdultPool = numberOfPool;
		if(isKidPool) numberOfAdultPool--;
		if(isWomenPool)	numberOfAdultPool--;

		if(numberOfAdultPool != 0){
			poolMap = new HashMap<Integer, Pool>();
			for(int i = 0 ; i < numberOfAdultPool ; i++){
				Pool pool = new Pool();
				poolMap.put(Integer.valueOf(pool.getPoolNumber()), pool);
			}

			int counter = 0;
			for(Team t:teamMap.values()){
				if(t.getCategory() == Team.ADULT_CATEGORY){
					poolMap.get((counter%numberOfAdultPool)+1).addTeam(t);
					counter++;
				}
			}
			for(Pool p:poolMap.values()){
				p.generateMatch();
			}
		}
		if(isKidPool){
			kidPool = new Pool();
			for(Team t:teamMap.values()){
				if(t.getCategory()== Team.KID_CATEGORY){
					kidPool.addTeam(t);
				}
			}
			kidPool.generateMatch();
		}
		if(isWomenPool){
			womenPool = new Pool();
			for(Team t:teamMap.values()){
				if(t.getCategory()== Team.WOMEN_CATEGORY){
					womenPool.addTeam(t);
				}
			}
			womenPool.generateMatch();
		}

	}
}







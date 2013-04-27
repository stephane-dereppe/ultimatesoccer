package be.vodelee.ultimate.persistence;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import be.vodelee.ultimate.core.Match;
import be.vodelee.ultimate.core.Pool;
import be.vodelee.ultimate.core.Team;
import be.vodelee.ultimate.core.Tournament;
import be.vodelee.ultimate.ihm.MainScreen;

public class TournamentLoader {
	private String file;
	
	private MainScreen mainScreen;

	public TournamentLoader (String file , MainScreen mainscreen){
		this.file = file;
		this.mainScreen = mainscreen;
	}

	public int loadTournament(){
		Element root;
		Document document;

		Element tournamentStep;
		
		Element teamList;
	
		SAXBuilder sxb = new SAXBuilder();
		// Parsing du document xml
		try{
			document = sxb.build(new File(this.file));
			root = document.getRootElement();
			tournamentStep = root.getChild("TournamentStep");
			teamList = root.getChild("TeamList");
			List<Element> teamElementList = teamList.getChildren();
			Map<String,Team> teamMap = new TreeMap<String, Team>();

			for(Element teamElement:teamElementList){
				Team team = new Team(teamElement.getAttributeValue("Name"),
						teamElement.getAttributeValue("Captain"), 
						Integer.parseInt(teamElement.getAttributeValue("Category")),
						Integer.parseInt(teamElement.getAttributeValue("NbrOfPlayer")), 
						Integer.parseInt(teamElement.getAttributeValue("NbrOfMatchPlayed")), 
						Integer.parseInt(teamElement.getAttributeValue("NbrOfMatchWon")),
						Integer.parseInt(teamElement.getAttributeValue("NbrOfMatchLost")),
						Integer.parseInt(teamElement.getAttributeValue("NbrOfMatchEquality")), 
						Integer.parseInt(teamElement.getAttributeValue("NbrOfGoalFor")), 
						Integer.parseInt(teamElement.getAttributeValue("NbrOfGoalAgainst")), 
						Integer.parseInt(teamElement.getAttributeValue("NbrOfPoints")));
				teamMap.put(team.toString(), team);
			}
			
			// Clean the current tournament
			Tournament.getInstance().reset();
			// Set the map of teams
			Tournament.getInstance().setTeamMap(teamMap);
			// Set the file
			Tournament.getInstance().setFileToRecord(file);
			
			if(tournamentStep.getAttributeValue("NbPointForWin")!=null){
				Tournament.POINTS_FOR_MATCH_WON = Integer.parseInt(tournamentStep.getAttributeValue("NbPointForWin"));
			}
			
			if(tournamentStep.getAttributeValue("NbPointForEquality")!=null){
				Tournament.POINTS_FOR_MATCH_EQUALITY = Integer.parseInt(tournamentStep.getAttributeValue("NbPointForEquality"));
			}
			
			if(tournamentStep.getAttributeValue("NbPointForLost")!=null){
				Tournament.POINTS_FOR_MATCH_LOST = Integer.parseInt(tournamentStep.getAttributeValue("NbPointForLost"));
			}
			
			// STEP 2
			if(Integer.parseInt(tournamentStep.getAttributeValue("Step")) == Tournament.POOLS_STEP){
				Tournament.getInstance().setTournamentStep(Tournament.POOLS_STEP);
				mainScreen.disableButton();
				Element poolListElement = root.getChild("PoolList");
				List<Element> poolElementList = poolListElement.getChildren();
				Pool.poolNumberCounter = 1;
				boolean isKidPool = false;
				boolean isWomenPool = false; 
				int numberOfPool = 0;
				// for each pool
				for(Element poolElement : poolElementList){
					numberOfPool++;
					int poolNumber = Integer.parseInt(poolElement.getAttributeValue("PoolNumber"));
					Set<Team> teamSet = new TreeSet<Team>();
					// read each team
					Element poolTeamListElement = poolElement.getChild("TeamList");
					List<Element> poolTeamElementList = poolTeamListElement.getChildren();
					for(Element poolTeamElement : poolTeamElementList){
						String teamName = poolTeamElement.getAttributeValue("Name");
						teamSet.add(Tournament.getInstance().getTeamMap().get(teamName));
					}
					
					List<Match> matchList = new Vector<Match>();
					Element matchListElement = poolElement.getChild("MatchList");
					List<Element> matchElementList = matchListElement.getChildren();
					for(Element matchElement : matchElementList){
						String team1Name = matchElement.getAttributeValue("Team1");
						Team team1 = Tournament.getInstance().getTeamMap().get(team1Name);
						String team2Name = matchElement.getAttributeValue("Team2");
						Team team2 = Tournament.getInstance().getTeamMap().get(team2Name);
						String isPlayed = matchElement.getAttributeValue("isPlayed");
						Match match;
						if(isPlayed.compareTo("false") == 0){
							match = new Match(team1,team2);
						}
						else{
							int goalTeam1 = Integer.parseInt(matchElement.getAttributeValue("GoalTeam1"));
							int goalTeam2 = Integer.parseInt(matchElement.getAttributeValue("GoalTeam2"));
							match = new Match(team1, team2, goalTeam1, goalTeam2);
						}
						matchList.add(match);
					}
					
					Pool pool = new Pool(teamSet,matchList,poolNumber);
					
					int category = Integer.parseInt(poolElement.getAttributeValue("category"));
					if(category == Team.ADULT_CATEGORY){
						if(Tournament.getInstance().getPoolMap() == null){
							Map<Integer, Pool> poolMap = new HashMap<Integer, Pool>();
							poolMap.put(pool.getPoolNumber(), pool);
							Tournament.getInstance().setPoolMap(poolMap);
						}
						else{
							Tournament.getInstance().getPoolMap().put(pool.getPoolNumber(), pool);
						}
					}
					if(category == Team.KID_CATEGORY){
						isKidPool = true;
						Tournament.getInstance().setKidPool(pool);
					}
					if(category == Team.WOMEN_CATEGORY){
						isWomenPool = true;
						Tournament.getInstance().setWomenPool(pool);
					}
				}
				mainScreen.buildPoolsScreen(numberOfPool, isKidPool, isWomenPool);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return 1;
		}

		return 0;
	}
}

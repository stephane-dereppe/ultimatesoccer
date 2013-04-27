package be.vodelee.ultimate.persistence;

import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Map;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import be.vodelee.ultimate.core.Match;
import be.vodelee.ultimate.core.Pool;
import be.vodelee.ultimate.core.Team;
import be.vodelee.ultimate.core.Tournament;

public class TournamentRecorder {

	Document document;

	Element root;

	public TournamentRecorder() {
		root = new Element("Tournament");
		document = new Document(root);
	}

	public void recordTournament(String file){

		Tournament tournament = Tournament.getInstance();
		Element tournamentStepElement = new Element("TournamentStep");
		tournamentStepElement.setAttribute("Step", tournament.getTournamentStep()+"");
		tournamentStepElement.setAttribute("NbPointForWin", Tournament.POINTS_FOR_MATCH_WON+"");
		tournamentStepElement.setAttribute("NbPointForEquality", Tournament.POINTS_FOR_MATCH_EQUALITY+"");
		tournamentStepElement.setAttribute("NbPointForLost", Tournament.POINTS_FOR_MATCH_LOST+"");
		root.addContent(tournamentStepElement);
		recordTeams(tournament.getTeamMap());
		if(tournament.getTournamentStep() == Tournament.POOLS_STEP)
		{
			recordMatchs();
		}
		record(file);
		return;
	}

	public void recordTeams(Map<String, Team> teamMap){
		Element teamListElement = new Element("TeamList");
		root.addContent(teamListElement);
		Collection<Team> teamList = teamMap.values();
		for(Team team:teamList){
			Element teamElement = new Element("Team");
			teamElement.setAttribute(new Attribute("Name",team.getName()));
			teamElement.setAttribute(new Attribute("Captain",team.getCaptain()));
			teamElement.setAttribute(new Attribute("Category",""+team.getCategory()));
			teamElement.setAttribute(new Attribute("NbrOfPlayer",""+team.getNumberOfPlayer()));
			teamElement.setAttribute(new Attribute("NbrOfMatchPlayed",""+team.getNumberOfMatchPlayed()));
			teamElement.setAttribute(new Attribute("NbrOfMatchWon",""+team.getNumberOfMatchWon()));
			teamElement.setAttribute(new Attribute("NbrOfMatchLost",""+team.getNumberOfMatchLost()));
			teamElement.setAttribute(new Attribute("NbrOfMatchEquality",""+team.getNumberOfMatchEquality()));
			teamElement.setAttribute(new Attribute("NbrOfGoalFor",""+team.getNumberOfGoalFor()));
			teamElement.setAttribute(new Attribute("NbrOfGoalAgainst",""+team.getNumberOfGoalAgainst()));
			teamElement.setAttribute(new Attribute("NbrOfPoints",""+team.getNumberOfPoints()));
			teamListElement.addContent(teamElement);
		}
	}

	private void recordMatchs(){
		Element poolListElement = new Element("PoolList");
		root.addContent(poolListElement);
		// Adult pool if any
		if(Tournament.getInstance().getPoolMap()!= null){
			for(Pool p : Tournament.getInstance().getPoolMap().values()){
				Element poolElement = new Element("Pool");
				poolElement.setAttribute(new Attribute("category",""+Team.ADULT_CATEGORY));
				poolElement.setAttribute(new Attribute("PoolNumber",""+p.getPoolNumber()));
				// record the teams of the pool
				Element teamListElement = new Element("TeamList");
				for(Team t: p.getTeamSet()){
					Element teamElement = new Element("Team");
					teamElement.setAttribute(new Attribute("Name",t.getName()));
					teamListElement.addContent(teamElement);
				}
				poolElement.addContent(teamListElement);
				// record the match of the pool
				Element matchListElement = new Element("MatchList");
				for(Match m : p.getMatchList()){
					Element matchElement = new Element("Match");
					matchElement.setAttribute(new Attribute("isPlayed",m.isPlayed()+""));
					matchElement.setAttribute(new Attribute("Team1",m.getTeam1().toString()));
					matchElement.setAttribute(new Attribute("Team2",m.getTeam2().toString()));
					if(m.getGoalTeam1()!=null){
						matchElement.setAttribute(new Attribute("GoalTeam1",m.getGoalTeam1().toString()));	
					}
					else{
						matchElement.setAttribute(new Attribute("GoalTeam1",""));
					}
					if(m.getGoalTeam2()!= null){
						matchElement.setAttribute(new Attribute("GoalTeam2",m.getGoalTeam2().toString()));	
					}
					else{
						matchElement.setAttribute(new Attribute("GoalTeam2",""));
					}
					matchListElement.addContent(matchElement);
				}
				poolElement.addContent(matchListElement);
				poolListElement.addContent(poolElement);
			}
		}
		// Kid pool
		if(Tournament.getInstance().getKidPool()!=null){
			Element poolElement = new Element("Pool");
			poolElement.setAttribute(new Attribute("category",""+Team.KID_CATEGORY));
			poolElement.setAttribute(new Attribute("PoolNumber",""+Tournament.getInstance().getKidPool().getPoolNumber()));
			// record the teams of the pool
			Element teamListElement = new Element("TeamList");
			for(Team t: Tournament.getInstance().getKidPool().getTeamSet()){
				Element teamElement = new Element("Team");
				teamElement.setAttribute(new Attribute("Name",t.getName()));
				teamListElement.addContent(teamElement);
			}
			poolElement.addContent(teamListElement);
			// record the match of the pool
			Element matchListElement = new Element("MatchList");
			for(Match m : Tournament.getInstance().getKidPool().getMatchList()){
				Element matchElement = new Element("Match");
				matchElement.setAttribute(new Attribute("isPlayed",m.isPlayed()+""));
				matchElement.setAttribute(new Attribute("Team1",m.getTeam1().toString()));
				matchElement.setAttribute(new Attribute("Team2",m.getTeam2().toString()));
				if(m.getGoalTeam1()!=null){
					matchElement.setAttribute(new Attribute("GoalTeam1",m.getGoalTeam1().toString()));	
				}
				else{
					matchElement.setAttribute(new Attribute("GoalTeam1",""));
				}
				if(m.getGoalTeam2()!= null){
					matchElement.setAttribute(new Attribute("GoalTeam2",m.getGoalTeam2().toString()));	
				}
				else{
					matchElement.setAttribute(new Attribute("GoalTeam2",""));
				}
				matchListElement.addContent(matchElement);
			}
			poolElement.addContent(matchListElement);
			poolListElement.addContent(poolElement);
		}
		
		// Women pool
		if(Tournament.getInstance().getWomenPool()!=null){
			Element poolElement = new Element("Pool");
			poolElement.setAttribute(new Attribute("category",""+Team.WOMEN_CATEGORY));
			poolElement.setAttribute(new Attribute("PoolNumber",""+Tournament.getInstance().getWomenPool().getPoolNumber()));
			// record the teams of the pool
			Element teamListElement = new Element("TeamList");
			for(Team t: Tournament.getInstance().getWomenPool().getTeamSet()){
				Element teamElement = new Element("Team");
				teamElement.setAttribute(new Attribute("Name",t.getName()));
				teamListElement.addContent(teamElement);
			}
			poolElement.addContent(teamListElement);
			// record the match of the pool
			Element matchListElement = new Element("MatchList");
			for(Match m : Tournament.getInstance().getWomenPool().getMatchList()){
				Element matchElement = new Element("Match");
				matchElement.setAttribute(new Attribute("isPlayed",m.isPlayed()+""));
				matchElement.setAttribute(new Attribute("Team1",m.getTeam1().toString()));
				matchElement.setAttribute(new Attribute("Team2",m.getTeam2().toString()));
				if(m.getGoalTeam1()!=null){
					matchElement.setAttribute(new Attribute("GoalTeam1",m.getGoalTeam1().toString()));	
				}
				else{
					matchElement.setAttribute(new Attribute("GoalTeam1",""));
				}
				if(m.getGoalTeam2()!= null){
					matchElement.setAttribute(new Attribute("GoalTeam2",m.getGoalTeam2().toString()));	
				}
				else{
					matchElement.setAttribute(new Attribute("GoalTeam2",""));
				}
				matchListElement.addContent(matchElement);
			}
			poolElement.addContent(matchListElement);
			poolListElement.addContent(poolElement);
		}
	}

	private void record(String file)
	{
		try
		{
			XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
			out.output(this.document, new FileOutputStream(file));
		}
		catch (java.io.IOException e){}
	}
}

package be.vodelee.ultimate.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class Pool {
	public static int poolNumberCounter = 1;

	private int poolNumber;

	private Set<Team> teamSet;

	private List<Match> matchList;

	// create a pool
	Pool() {
		this.teamSet = new TreeSet<Team>();
		this.poolNumber = poolNumberCounter;
		poolNumberCounter++;
	}

	// create a pool while loading the tournament
	Pool(Set<Team> teamSet, int poolNumber) {
		this.teamSet = teamSet;
		this.poolNumber = poolNumber;
	}

	// create a pool while loading the tournament with matchs
	public Pool(Set<Team> teamSet, List<Match> matchList, int poolNumber) {
		this.teamSet = teamSet;
		this.matchList = matchList;
		this.poolNumber = poolNumber;
	}

	public List<Match> getMatchList() {
		return matchList;
	}

	public List<Match> getMatchPlayedList() {
		List<Match> matchPlayedList = new Vector<Match>();
		for (Match m : matchList) {
			if (m.isPlayed()) {
				matchPlayedList.add(m);
			}
		}
		return matchPlayedList;
	}

	public List<Match> getMatchNotPlayedList() {
		List<Match> matchNotPlayedList = new Vector<Match>();
		for (Match m : matchList) {
			if (!m.isPlayed()) {
				matchNotPlayedList.add(m);
			}
		}
		return matchNotPlayedList;
	}

	public int getPoolNumber() {
		return poolNumber;
	}

	public void addTeam(Team team) {
		teamSet.add(team);
	}

	public Set<Team> getTeamSet() {
		return teamSet;
	}

	public void generateMatch() {
		List<Match> matchList = new Vector<Match>();
		Object[] teamArray = teamSet.toArray();
		for (int i = 0; i < teamArray.length; i++) {
			Team team1 = (Team) teamArray[i];
			for (int j = (i + 1); j < teamArray.length; j++) {
				Team team2 = (Team) teamArray[j];
				matchList.add(new Match(team1, team2));
			}
		}
		this.matchList = (List<Match>) orderMatchList(matchList);
	}

	private List<Match> randomizeMatchList(List<Match> initialList) {
		List<Match> finalList = new Vector<Match>();
		while (!initialList.isEmpty()) {
			int i = ((int) (Math.random() * 10000)) % initialList.size();
			finalList.add(initialList.remove(i));
		}
		return finalList;
	}

	private List<Match> orderMatchList(List<Match> initialList) {

		// Create the set with team and nbr of forseen match
		Set<TeamWithNbrOfMatch> teamSet = new HashSet<TeamWithNbrOfMatch>();
		for (Match m : initialList) {
			TeamWithNbrOfMatch t = new TeamWithNbrOfMatch(m.getTeam1(), 0);
			teamSet.add(t);
			TeamWithNbrOfMatch t2 = new TeamWithNbrOfMatch(m.getTeam2(), 0);
			teamSet.add(t2);
		}

		List<Match> finalList = new Vector<Match>();

		while (!initialList.isEmpty()) {
			Set<TeamWithNbrOfMatch> excludeTeamSet = new HashSet<TeamWithNbrOfMatch>();
			Match match = this.findBestMatch(teamSet, initialList,
					excludeTeamSet);
			// If no match found, redo the search with more excludeTeam
			while (match == null) {
				match = this
						.findBestMatch(teamSet, initialList, excludeTeamSet);
			}
			int index = initialList.indexOf(match);
			initialList.remove(index);
			finalList.add(match);
		}

		return finalList;
	}

	private Match findBestMatch(Set<TeamWithNbrOfMatch> teamSet,
			List<Match> initialList, Set<TeamWithNbrOfMatch> excludeTeamSet) {
		TeamWithNbrOfMatch t1 = null;
		TeamWithNbrOfMatch t2 = null;

		// look team with less match
		int minValue1 = 10000;
		int minValue2 = 10000;
		for (TeamWithNbrOfMatch t : teamSet) {
			// Take the team with the less match
			if (minValue1 > t.getNbrOfMatch()) {
				minValue1 = t.getNbrOfMatch();
				t1 = t;
				continue;
			}
			// Take the team with the less match not yet tested with t1
			if (minValue2 > t.getNbrOfMatch()) {
				// One team must not be in the excluded team
				if (!excludeTeamSet.contains(t)) {
					minValue2 = t.getNbrOfMatch();
					t2 = t;
				}
				continue;
			}
		}

		Match match = this.findMatchForTeam(initialList, t1, t2);
		if (match != null) {
			t1.setNbrOfMatch(t1.getNbrOfMatch() + 1);
			t2.setNbrOfMatch(t2.getNbrOfMatch() + 1);
		} else {
			if (t1.getNbrOfMatch() > t2.getNbrOfMatch()
					&& !excludeTeamSet.contains(t1)) {
				excludeTeamSet.add(t1);
			} else {
				excludeTeamSet.add(t2);
			}
		}

		return match;
	}

	private Match findMatchForTeam(List<Match> initialList,
			TeamWithNbrOfMatch t1, TeamWithNbrOfMatch t2) {
		for (Match match : initialList) {
			if ((match.getTeam1().equals(t1.getTeam()) && match.getTeam2()
					.equals(t2.getTeam()))
					|| (match.getTeam1().equals(t2.getTeam()) && match
							.getTeam2().equals(t1.getTeam()))) {
				return match;
			}
		}
		return null;

	}
}

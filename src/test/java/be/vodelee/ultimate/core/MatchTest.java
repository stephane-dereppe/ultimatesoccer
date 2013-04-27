package be.vodelee.ultimate.core;


import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import be.vodelee.ultimate.core.Match;
import be.vodelee.ultimate.core.Team;

public class MatchTest {
	private Match match;
	private Team team1;
	private Team team2;

	@Test
	public void testEncodeResult(){
		team1 = new Team("team1","Stephane",Team.ADULT_CATEGORY,5);
		team2 = new Team("team2","Fabien",Team.ADULT_CATEGORY,5);
		match = new Match(team1,team2);

		assertFalse(match.isPlayed());

		match.encodeResult(2, 1);

		assertTrue(match.isPlayed());

		assertTrue(team1.getNumberOfGoalAgainst() == 1);
		assertTrue(team1.getNumberOfGoalFor() == 2);
		assertTrue(team1.getNumberOfPoints() == 3);
		assertTrue(team1.getNumberOfMatchEquality() == 0);
		assertTrue(team1.getNumberOfMatchLost() == 0);
		assertTrue(team1.getNumberOfMatchWon() == 1);
		assertTrue(team1.getNumberOfMatchPlayed()== 1);

		assertTrue(team2.getNumberOfGoalAgainst() == 2);
		assertTrue(team2.getNumberOfGoalFor() == 1);
		assertTrue(team2.getNumberOfPoints() == 0);
		assertTrue(team2.getNumberOfMatchEquality() == 0);
		assertTrue(team2.getNumberOfMatchLost() == 1);
		assertTrue(team2.getNumberOfMatchWon() == 0);
		assertTrue(team2.getNumberOfMatchPlayed()== 1);

		team1 = new Team("team1","Stephane",Team.ADULT_CATEGORY,5);
		team2 = new Team("team2","Fabien",Team.ADULT_CATEGORY,5);
		match = new Match(team1,team2);

		assertFalse(match.isPlayed());

		match.encodeResult(3, 3);

		assertTrue(match.isPlayed());

		assertTrue(team1.getNumberOfGoalAgainst() == 3);
		assertTrue(team1.getNumberOfGoalFor() == 3);
		assertTrue(team1.getNumberOfPoints() == 1);
		assertTrue(team1.getNumberOfMatchEquality() == 1);
		assertTrue(team1.getNumberOfMatchLost() == 0);
		assertTrue(team1.getNumberOfMatchWon() == 0);
		assertTrue(team1.getNumberOfMatchPlayed()== 1);

		assertTrue(team2.getNumberOfGoalAgainst() == 3);
		assertTrue(team2.getNumberOfGoalFor() == 3);
		assertTrue(team2.getNumberOfPoints() == 1);
		assertTrue(team2.getNumberOfMatchEquality() == 1);
		assertTrue(team2.getNumberOfMatchLost() == 0);
		assertTrue(team2.getNumberOfMatchWon() == 0);
		assertTrue(team2.getNumberOfMatchPlayed()== 1);
		
		team1 = new Team("team1","Stephane",Team.ADULT_CATEGORY,5);
		team2 = new Team("team2","Fabien",Team.ADULT_CATEGORY,5);
		match = new Match(team1,team2);

		assertFalse(match.isPlayed());

		match.encodeResult(1, 3);

		assertTrue(match.isPlayed());

		assertTrue(team1.getNumberOfGoalAgainst() == 3);
		assertTrue(team1.getNumberOfGoalFor() == 1);
		assertTrue(team1.getNumberOfPoints() == 0);
		assertTrue(team1.getNumberOfMatchEquality() == 0);
		assertTrue(team1.getNumberOfMatchLost() == 1);
		assertTrue(team1.getNumberOfMatchWon() == 0);
		assertTrue(team1.getNumberOfMatchPlayed()== 1);

		assertTrue(team2.getNumberOfGoalAgainst() == 1);
		assertTrue(team2.getNumberOfGoalFor() == 3);
		assertTrue(team2.getNumberOfPoints() == 3);
		assertTrue(team2.getNumberOfMatchEquality() == 0);
		assertTrue(team2.getNumberOfMatchLost() == 0);
		assertTrue(team2.getNumberOfMatchWon() == 1);
		assertTrue(team2.getNumberOfMatchPlayed()== 1);
	}
	

	@Test
	public void testUpdateResult(){
		team1 = new Team("team1","Stephane",Team.ADULT_CATEGORY,5);
		team2 = new Team("team2","Fabien",Team.ADULT_CATEGORY,5);
		match = new Match(team1,team2);

		assertFalse(match.isPlayed());

		match.encodeResult(2, 1);

		assertTrue(match.isPlayed());
		
		match.updateResult(3, 0);

		assertTrue(team1.getNumberOfGoalAgainst() == 0);
		assertTrue(team1.getNumberOfGoalFor() == 3);
		assertTrue(team1.getNumberOfPoints() == 3);
		assertTrue(team1.getNumberOfMatchEquality() == 0);
		assertTrue(team1.getNumberOfMatchLost() == 0);
		assertTrue(team1.getNumberOfMatchWon() == 1);
		assertTrue(team1.getNumberOfMatchPlayed()== 1);

		assertTrue(team2.getNumberOfGoalAgainst() == 3);
		assertTrue(team2.getNumberOfGoalFor() == 0);
		assertTrue(team2.getNumberOfPoints() == 0);
		assertTrue(team2.getNumberOfMatchEquality() == 0);
		assertTrue(team2.getNumberOfMatchLost() == 1);
		assertTrue(team2.getNumberOfMatchWon() == 0);
		assertTrue(team2.getNumberOfMatchPlayed()== 1);

		team1 = new Team("team1","Stephane",Team.ADULT_CATEGORY,5);
		team2 = new Team("team2","Fabien",Team.ADULT_CATEGORY,5);
		match = new Match(team1,team2);

		assertFalse(match.isPlayed());

		match.encodeResult(2, 1);

		assertTrue(match.isPlayed());
		
		match.updateResult(1, 2);
		
		assertTrue(team1.getNumberOfGoalAgainst() == 2);
		assertTrue(team1.getNumberOfGoalFor() == 1);
		assertTrue(team1.getNumberOfPoints() == 0);
		assertTrue(team1.getNumberOfMatchEquality() == 0);
		assertTrue(team1.getNumberOfMatchLost() == 1);
		assertTrue(team1.getNumberOfMatchWon() == 0);
		assertTrue(team1.getNumberOfMatchPlayed()== 1);

		assertTrue(team2.getNumberOfGoalAgainst() == 1);
		assertTrue(team2.getNumberOfGoalFor() == 2);
		assertTrue(team2.getNumberOfPoints() == 3);
		assertTrue(team2.getNumberOfMatchEquality() == 0);
		assertTrue(team2.getNumberOfMatchLost() == 0);
		assertTrue(team2.getNumberOfMatchWon() == 1);
		assertTrue(team2.getNumberOfMatchPlayed()== 1);
		
		team1 = new Team("team1","Stephane",Team.ADULT_CATEGORY,5);
		team2 = new Team("team2","Fabien",Team.ADULT_CATEGORY,5);
		match = new Match(team1,team2);

		assertFalse(match.isPlayed());

		match.encodeResult(2, 1);

		assertTrue(match.isPlayed());
		
		match.updateResult(1, 1);

		assertTrue(team1.getNumberOfGoalAgainst() == 1);
		assertTrue(team1.getNumberOfGoalFor() == 1);
		assertTrue(team1.getNumberOfPoints() == 1);
		assertTrue(team1.getNumberOfMatchEquality() == 1);
		assertTrue(team1.getNumberOfMatchLost() == 0);
		assertTrue(team1.getNumberOfMatchWon() == 0);
		assertTrue(team1.getNumberOfMatchPlayed()== 1);

		assertTrue(team2.getNumberOfGoalAgainst() == 1);
		assertTrue(team2.getNumberOfGoalFor() == 1);
		assertTrue(team2.getNumberOfPoints() == 1);
		assertTrue(team2.getNumberOfMatchEquality() == 1);
		assertTrue(team2.getNumberOfMatchLost() == 0);
		assertTrue(team2.getNumberOfMatchWon() == 0);
		assertTrue(team2.getNumberOfMatchPlayed()== 1);
		
		team1 = new Team("team1","Stephane",Team.ADULT_CATEGORY,5);
		team2 = new Team("team2","Fabien",Team.ADULT_CATEGORY,5);
		match = new Match(team1,team2);

		assertFalse(match.isPlayed());

		match.encodeResult(1, 5);

		assertTrue(match.isPlayed());
		
		match.updateResult(3, 0);

		assertTrue(team1.getNumberOfGoalAgainst() == 0);
		assertTrue(team1.getNumberOfGoalFor() == 3);
		assertTrue(team1.getNumberOfPoints() == 3);
		assertTrue(team1.getNumberOfMatchEquality() == 0);
		assertTrue(team1.getNumberOfMatchLost() == 0);
		assertTrue(team1.getNumberOfMatchWon() == 1);
		assertTrue(team1.getNumberOfMatchPlayed()== 1);

		assertTrue(team2.getNumberOfGoalAgainst() == 3);
		assertTrue(team2.getNumberOfGoalFor() == 0);
		assertTrue(team2.getNumberOfPoints() == 0);
		assertTrue(team2.getNumberOfMatchEquality() == 0);
		assertTrue(team2.getNumberOfMatchLost() == 1);
		assertTrue(team2.getNumberOfMatchWon() == 0);
		assertTrue(team2.getNumberOfMatchPlayed()== 1);

		team1 = new Team("team1","Stephane",Team.ADULT_CATEGORY,5);
		team2 = new Team("team2","Fabien",Team.ADULT_CATEGORY,5);
		match = new Match(team1,team2);

		assertFalse(match.isPlayed());

		match.encodeResult(1, 5);

		assertTrue(match.isPlayed());
		
		match.updateResult(1, 2);
		
		assertTrue(team1.getNumberOfGoalAgainst() == 2);
		assertTrue(team1.getNumberOfGoalFor() == 1);
		assertTrue(team1.getNumberOfPoints() == 0);
		assertTrue(team1.getNumberOfMatchEquality() == 0);
		assertTrue(team1.getNumberOfMatchLost() == 1);
		assertTrue(team1.getNumberOfMatchWon() == 0);
		assertTrue(team1.getNumberOfMatchPlayed()== 1);

		assertTrue(team2.getNumberOfGoalAgainst() == 1);
		assertTrue(team2.getNumberOfGoalFor() == 2);
		assertTrue(team2.getNumberOfPoints() == 3);
		assertTrue(team2.getNumberOfMatchEquality() == 0);
		assertTrue(team2.getNumberOfMatchLost() == 0);
		assertTrue(team2.getNumberOfMatchWon() == 1);
		assertTrue(team2.getNumberOfMatchPlayed()== 1);
		
		team1 = new Team("team1","Stephane",Team.ADULT_CATEGORY,5);
		team2 = new Team("team2","Fabien",Team.ADULT_CATEGORY,5);
		match = new Match(team1,team2);

		assertFalse(match.isPlayed());

		match.encodeResult(1, 5);

		assertTrue(match.isPlayed());
		
		match.updateResult(1, 1);

		assertTrue(team1.getNumberOfGoalAgainst() == 1);
		assertTrue(team1.getNumberOfGoalFor() == 1);
		assertTrue(team1.getNumberOfPoints() == 1);
		assertTrue(team1.getNumberOfMatchEquality() == 1);
		assertTrue(team1.getNumberOfMatchLost() == 0);
		assertTrue(team1.getNumberOfMatchWon() == 0);
		assertTrue(team1.getNumberOfMatchPlayed()== 1);

		assertTrue(team2.getNumberOfGoalAgainst() == 1);
		assertTrue(team2.getNumberOfGoalFor() == 1);
		assertTrue(team2.getNumberOfPoints() == 1);
		assertTrue(team2.getNumberOfMatchEquality() == 1);
		assertTrue(team2.getNumberOfMatchLost() == 0);
		assertTrue(team2.getNumberOfMatchWon() == 0);
		assertTrue(team2.getNumberOfMatchPlayed()== 1);

		// was equality
		team1 = new Team("team1","Stephane",Team.ADULT_CATEGORY,5);
		team2 = new Team("team2","Fabien",Team.ADULT_CATEGORY,5);
		match = new Match(team1,team2);

		assertFalse(match.isPlayed());

		match.encodeResult(5, 5);

		assertTrue(match.isPlayed());
		
		match.updateResult(3, 0);

		assertTrue(team1.getNumberOfGoalAgainst() == 0);
		assertTrue(team1.getNumberOfGoalFor() == 3);
		assertTrue(team1.getNumberOfPoints() == 3);
		assertTrue(team1.getNumberOfMatchEquality() == 0);
		assertTrue(team1.getNumberOfMatchLost() == 0);
		assertTrue(team1.getNumberOfMatchWon() == 1);
		assertTrue(team1.getNumberOfMatchPlayed()== 1);

		assertTrue(team2.getNumberOfGoalAgainst() == 3);
		assertTrue(team2.getNumberOfGoalFor() == 0);
		assertTrue(team2.getNumberOfPoints() == 0);
		assertTrue(team2.getNumberOfMatchEquality() == 0);
		assertTrue(team2.getNumberOfMatchLost() == 1);
		assertTrue(team2.getNumberOfMatchWon() == 0);
		assertTrue(team2.getNumberOfMatchPlayed()== 1);

		team1 = new Team("team1","Stephane",Team.ADULT_CATEGORY,5);
		team2 = new Team("team2","Fabien",Team.ADULT_CATEGORY,5);
		match = new Match(team1,team2);

		assertFalse(match.isPlayed());

		match.encodeResult(0, 0);

		assertTrue(match.isPlayed());
		
		match.updateResult(1, 2);
		
		assertTrue(team1.getNumberOfGoalAgainst() == 2);
		assertTrue(team1.getNumberOfGoalFor() == 1);
		assertTrue(team1.getNumberOfPoints() == 0);
		assertTrue(team1.getNumberOfMatchEquality() == 0);
		assertTrue(team1.getNumberOfMatchLost() == 1);
		assertTrue(team1.getNumberOfMatchWon() == 0);
		assertTrue(team1.getNumberOfMatchPlayed()== 1);

		assertTrue(team2.getNumberOfGoalAgainst() == 1);
		assertTrue(team2.getNumberOfGoalFor() == 2);
		assertTrue(team2.getNumberOfPoints() == 3);
		assertTrue(team2.getNumberOfMatchEquality() == 0);
		assertTrue(team2.getNumberOfMatchLost() == 0);
		assertTrue(team2.getNumberOfMatchWon() == 1);
		assertTrue(team2.getNumberOfMatchPlayed()== 1);
		
		team1 = new Team("team1","Stephane",Team.ADULT_CATEGORY,5);
		team2 = new Team("team2","Fabien",Team.ADULT_CATEGORY,5);
		match = new Match(team1,team2);

		assertFalse(match.isPlayed());

		match.encodeResult(2, 2);

		assertTrue(match.isPlayed());
		
		match.updateResult(1, 1);

		assertTrue(team1.getNumberOfGoalAgainst() == 1);
		assertTrue(team1.getNumberOfGoalFor() == 1);
		assertTrue(team1.getNumberOfPoints() == 1);
		assertTrue(team1.getNumberOfMatchEquality() == 1);
		assertTrue(team1.getNumberOfMatchLost() == 0);
		assertTrue(team1.getNumberOfMatchWon() == 0);
		assertTrue(team1.getNumberOfMatchPlayed()== 1);

		assertTrue(team2.getNumberOfGoalAgainst() == 1);
		assertTrue(team2.getNumberOfGoalFor() == 1);
		assertTrue(team2.getNumberOfPoints() == 1);
		assertTrue(team2.getNumberOfMatchEquality() == 1);
		assertTrue(team2.getNumberOfMatchLost() == 0);
		assertTrue(team2.getNumberOfMatchWon() == 0);
		assertTrue(team2.getNumberOfMatchPlayed()== 1);
	
	}

}

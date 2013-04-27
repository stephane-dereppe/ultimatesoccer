package be.vodelee.ultimate.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import be.vodelee.ultimate.core.Team;

public class TeamTest {
	private Team team1;
	private Team team2;
	private Team team3;
	private Team team4;
	private Team team5;


	@Test
	public void testCompareTo() {
		// Create team 1 : best points than team 2
		team1 = new Team("équipe 1   ","stephan", Team.ADULT_CATEGORY, 5, 10, 10, 0, 0, 15, 0, 30);
		// Create Team 2 : best match won than team 3
		team2 = new Team("équipe 2","Fab", Team.ADULT_CATEGORY, 5, 10, 6, 4, 0, 15, 0, 18);
		// Create team 3 : best goalaverage than team 4
		team3 = new Team("équipe 3","Bik", Team.ADULT_CATEGORY, 5, 10, 4, 0, 6, 15, 0, 18);
		// Create Team 4 : best name than team 5
		team4 = new Team("  équipe 4","Dou", Team.ADULT_CATEGORY, 5, 10, 4, 0, 6, 15, 1, 18);
		// Create Team 5 : last Team
		team5 = new Team("équipe 5","Foin", Team.ADULT_CATEGORY, 5, 10, 4, 0, 6, 15, 1, 18);

		assertTrue(team1.compareTo(team1) == 0);
		assertTrue(team1.compareTo(team2) == 1);
		assertTrue(team1.compareTo(team3) == 1);
		assertTrue(team1.compareTo(team4) == 1);
		assertTrue(team1.compareTo(team5) == 1);
		assertTrue(team2.compareTo(team1) == -1);
		assertTrue(team3.compareTo(team1) == -1);
		assertTrue(team4.compareTo(team1) == -1);
		assertTrue(team5.compareTo(team1) == -1);
		
		assertTrue(team2.compareTo(team2) == 0);
		assertTrue(team2.compareTo(team3) == 1);
		assertTrue(team2.compareTo(team4) == 1);
		assertTrue(team2.compareTo(team5) == 1);
		assertTrue(team3.compareTo(team2) == -1);
		assertTrue(team4.compareTo(team2) == -1);
		assertTrue(team5.compareTo(team2) == -1);
		
		assertTrue(team3.compareTo(team3) == 0);
		assertTrue(team3.compareTo(team4) == 1);
		assertTrue(team3.compareTo(team5) == 1);
		assertTrue(team4.compareTo(team3) == -1);
		assertTrue(team5.compareTo(team3) == -1);
		
		assertTrue(team4.compareTo(team4) == 0);
		assertTrue(team4.compareTo(team5) == 1);
		assertTrue(team5.compareTo(team4) == -1);
		
		assertTrue(team5.compareTo(team5) == 0);
	}

	@Test
	public void testEquals(){
		team1 = new Team("équipe 1   ","stephan", Team.ADULT_CATEGORY, 5, 10, 10, 0, 0, 15, 0, 30);
		team2 = new Team("équipe 2","Fab", Team.ADULT_CATEGORY, 5, 10, 6, 4, 0, 15, 0, 18);
		team3 = new Team("équipe 2","Bik", Team.ADULT_CATEGORY, 5, 10, 4, 0, 6, 15, 0, 18);
		team4 = new Team("  équipe 2","Dou", Team.ADULT_CATEGORY, 5, 10, 4, 0, 6, 15, 1, 18);
		team5 = new Team("équipe 2   ","Foin", Team.ADULT_CATEGORY, 5, 10, 4, 0, 6, 15, 1, 18);
		
		assertFalse(team1.equals(team2));
		assertFalse(team2.equals(team1));
		assertTrue(team2.equals(team3));
		assertTrue(team3.equals(team4));
		assertTrue(team4.equals(team5));
	}

	@Test
	public void testHashCode(){
		team1 = new Team("équipe 1   ","stephan", Team.ADULT_CATEGORY, 5, 10, 10, 0, 0, 15, 0, 30);
		team2 = new Team("équipe 2","Fab", Team.ADULT_CATEGORY, 5, 10, 6, 4, 0, 15, 0, 18);
		team3 = new Team("équipe 2","Bik", Team.ADULT_CATEGORY, 5, 10, 4, 0, 6, 15, 0, 18);
		team4 = new Team("  équipe 2","Dou", Team.ADULT_CATEGORY, 5, 10, 4, 0, 6, 15, 1, 18);
		team5 = new Team("équipe 2   ","Foin", Team.ADULT_CATEGORY, 5, 10, 4, 0, 6, 15, 1, 18);
		
		assertTrue(team2.hashCode() == team3.hashCode());
		assertTrue(team4.hashCode() == team3.hashCode());
		assertTrue(team4.hashCode() == team5.hashCode());
	}
}

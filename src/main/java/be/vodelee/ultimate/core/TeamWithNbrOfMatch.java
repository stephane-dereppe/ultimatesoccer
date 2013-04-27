/*
 * CVS file status:
 * 
 * $Id$
 * 
 * Copyright (c) Smals
 */
package be.vodelee.ultimate.core;


/**
 * TODO: Description of the class.
 *
 * @author stéde
 *
 * @since TODO: version number
 *
 */
public class TeamWithNbrOfMatch {

    private Team team;
    private int nbrOfMatch;
    
    /**
     * @return the team
     */
    public Team getTeam() {
        return team;
    }
    
    /**
     * @param team the team to set
     */
    public void setTeam(Team team) {
        this.team = team;
    }
    
    /**
     * @return the nbrOfMatch
     */
    public int getNbrOfMatch() {
        return nbrOfMatch;
    }
    
    /**
     * @param nbrOfMatch the nbrOfMatch to set
     */
    public void setNbrOfMatch(int nbrOfMatch) {
        this.nbrOfMatch = nbrOfMatch;
    }

    /**
     * @param team
     * @param nbrOfMatch
     */
    public TeamWithNbrOfMatch(Team team, int nbrOfMatch) {
        super();
        this.team = team;
        this.nbrOfMatch = nbrOfMatch;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "team = " + team + "   nbrOfMatch = " + nbrOfMatch;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((team == null) ? 0 : team.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TeamWithNbrOfMatch other = (TeamWithNbrOfMatch) obj;
        if (team == null) {
            if (other.team != null)
                return false;
        } else if (!team.equals(other.team))
            return false;
        return true;
    }
    
    
}

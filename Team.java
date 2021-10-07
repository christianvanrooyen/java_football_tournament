
//		This is the class Team, from which as many instances will be created as there are teams

public class Team 
{
	//These variables will essentially be the properties of each Team, accessed through getter and setter methods
	//variables we need to get from reading TeamsIn.txt
	private String teamname;
	//variables that need to be set from analyzing the match list
	private int goals_for = 0;
	private int goals_against = 0;
	private int matches_won = 0;
	private int matches_drawn = 0;
	private int matches_lost = 0;
	//variables that can be set mathematically
	private int match_points = 0;
	private int goaldifference = 0;
	
		
	//*----Constructor----*
	//This constructor is set up to give the team a name
	public Team(String name) {
		this.teamname = name;
	}
	
	//Methods: 
	//Getters and Setters
	
	//variables we need to get from reading TeamsIn.txt
	public String getTeamName()
	{
		return teamname;
	}	
	//variables that need to be set from analyzing the match list
	public int getGoals_for() {
		return this.goals_for;
	}
	public void addGoals_for(int goals_for) {
		this.goals_for = this.goals_for + goals_for;
	}
	
	public int getGoals_against() {
		return this.goals_against;
	}
	public void addGoals_against(int goals_against) {
		this.goals_against = this.goals_against + goals_against;
	}
	
	public int getMatches_won() {
		return this.matches_won;
	}
	public void addMatches_won(int matches_won) {
		this.matches_won = this.matches_won + matches_won;
	}
	
	public int getMatches_drawn() {
		return this.matches_drawn;
	}
	public void addMatches_drawn(int matches_drawn) {
		this.matches_drawn = this.matches_drawn + matches_drawn;
	}
	
	public int getMatches_lost() {
		return this.matches_lost;
	}
	public void addMatches_lost(int matches_lost) {
		this.matches_lost = this.matches_lost + matches_lost;
	}
	
	//variables that can be set mathematically
	public int getMatch_points() {
		this.match_points = (3 * this.matches_won) + (this.matches_drawn);
		return match_points;
	}
	
	public int getGoaldifference() {
		this.goaldifference = this.goals_for - this.goals_against;
		return this.goaldifference;
	}
	
}

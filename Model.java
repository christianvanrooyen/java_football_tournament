//importing necessary libraries
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;
import javax.swing.JOptionPane;

//		This model is completely independent of the user interface.
//		It could as easily be used by a command line or web interface.

//		The Model is independent
public class Model 
{
	//	Model attributes 

	//declare primitive variables
	int numberofteams;
	int possiblematches;
	
	//declare our array list that will store team names
	ArrayList<String> arraylist_teamnames = new ArrayList<>();
	//declare our array that will store match list
	ArrayList<String> arraylist_matchlist = new ArrayList<>();
	//declare our array list which will store the results
	//defining it as data type object to make it easier to contain strings and integers
	ArrayList<Object[]> arraylist_resultstable = new ArrayList<Object[]>();
	
	//Note: We are using Array Lists here, not arrays, to allow resizing
	
    //*----Constructor----*
	//Empty constructor - we call it from class Main_Program
	Model()
	{
		reset();
	}
	public void reset()
	{
		//	These are executed when parameters need to be refreshed
		getnewTeamsArrayList();
		getnewMatchListArrayList();
		setResultsTableArrayList();
		
		numberofteams = counttheTeams();
		possiblematches = calculatepossiblematches();
	
	}
	
	//This function is pretty much identical to counttheTeams()
	public void getnewTeamsArrayList()
	{
		//Re-initialize the array list and clear it
		arraylist_teamnames.clear();					

		//Reading in TeamsIn.txt into the array list of teams and sorting it
		try
		{
			//Declaring a file reader object and passing the file to it
			FileReader readerofteamnamesfile = new FileReader("TeamsIn.txt");
			//Declaring a scanner object... 
			///...and passing a file reader to it, not user typing  
			Scanner textofteamnames = new Scanner(readerofteamnamesfile); 
				
			do
			{
				arraylist_teamnames.add(textofteamnames.nextLine());	//adding to the array list			
																		//nextLine() moves the scanner to the next space,tab or break
			} 
			while  (textofteamnames.hasNextLine());		
				
			Collections.sort(arraylist_teamnames);						//sorting the the array list
							
			readerofteamnamesfile.close();	
			textofteamnames.close();
				
		}
		catch (FileNotFoundException x) 
		{
			JOptionPane.showMessageDialog(null, "File Could Not Be Found", "Program Execution Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		catch (IOException x) 
		{
			JOptionPane.showMessageDialog(null, "File Input Output Error", "Program Execution Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
			
	}	
	
	public void getnewMatchListArrayList()
	{
		//Re-initialize the array list and clear it
		arraylist_matchlist.clear();	
		
		//initialize the increment variable that will loop through the teams
		int i = 0;													
		//initialize another increment variable that will also loop through the teams
		int j = 0;													
		
		//declare strings that splice and analyze the ResultsIn.txt text
		String matchlinelookedat = "";
		Boolean foundthematch = false;
		
		String matchlinelookedat_team1;
		String matchlinelookedat_score1;
		String matchlinelookedat_team2;
		String matchlinelookedat_score2;
		String matchlinelookedat_correct = "";
		
		//looking at the teams in a pairwise manner
		for(i = 0; i < arraylist_teamnames.size(); i++)				
		{
			for(j = i; j < arraylist_teamnames.size(); j++)
			{
				if (j != i)		//can't compare the same team
				{

					try
					{
						//Declaring a file reader object and passing the file to it
						FileReader readerofmatchresults = new FileReader("ResultsIn.txt");
						//Declaring a scanner object and passing a file reader to it, not user typing 
						Scanner textofmatchresults = new Scanner(readerofmatchresults); 
						
						while (textofmatchresults.hasNextLine())				//looping though ResultsIn.txt
						{
							matchlinelookedat = textofmatchresults.nextLine();	//nextLine() moves the scanner to the next line
							//if the two teams, i and j are involved in the match currently being looked at in ResultsIn.txt...
							if (matchlinelookedat.contains(arraylist_teamnames.get(i)) && matchlinelookedat.contains(arraylist_teamnames.get(j)))
							{
								foundthematch = true;
								break;
							}
							else
							{
								foundthematch = false;
							}
						} 
						readerofmatchresults.close();
						textofmatchresults.close();
						if (foundthematch == true)
						{
							//splicing our match text
							matchlinelookedat_team1 = matchlinelookedat.substring(0,matchlinelookedat.indexOf(" "));
							matchlinelookedat_score1 = matchlinelookedat.substring(matchlinelookedat.indexOf(" ") + 1,matchlinelookedat.indexOf(" ") + 2);
							matchlinelookedat_team2 = matchlinelookedat.substring(matchlinelookedat.indexOf(" ") + 3,matchlinelookedat.length() - 2);
							matchlinelookedat_score2 = matchlinelookedat.substring(matchlinelookedat.length() - 1,matchlinelookedat.length());
							//First check if it is alphabetical: e.g. Alphas vs Charlies and not Charlies vs Alphas
							if (matchlinelookedat_team1.compareTo(matchlinelookedat_team2) > 0)
							{
								//Correct it if these teams are not in alphabetical order
								matchlinelookedat_correct = matchlinelookedat_team2 + " " + matchlinelookedat_score2 + " " + matchlinelookedat_team1 + " " + matchlinelookedat_score1;
							}
							else
							{
								matchlinelookedat_correct = matchlinelookedat;
							}
													
							arraylist_matchlist.add(matchlinelookedat_correct);

						}
						else
						{
							arraylist_matchlist.add(arraylist_teamnames.get(i) + " " + arraylist_teamnames.get(j) + " *** no result yet");					
						
						}
					}
					catch (FileNotFoundException x) 
					{
						JOptionPane.showMessageDialog(null, "File Could Not Be Found", "Program Execution Error", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
					catch (IOException x) 
					{
						JOptionPane.showMessageDialog(null, "File Input Output Problem", "Program Execution Error", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
				}
			}
		}		
	}
	
	public void setResultsTableArrayList()
	{
		//Re-initialize the array list and clear it
		arraylist_resultstable.clear();
		
		//initialize the increment variable that will loop through the teams
		int i = 0;	
		//initialize the increment variable that will look at the first and then second team mentioned per match list entry
		int j = 0;
				
		//Creating new Team objects:
		//Creating an array of Team Objects
		Team[] array_TeamsObjects = new Team[arraylist_teamnames.size()]; 	
		
		for (i = 0; i < arraylist_teamnames.size(); i++) 
		{
			array_TeamsObjects[i] = new Team(arraylist_teamnames.get(i));
		}
		/*
		 	We are creating new objects without explicitly naming them
		 	We don't want to say: Team Alphas = new Team(); 
							      Alphas.setTeamname("Alphas");     ...
			... because that means we are explicitly limiting ourselves to the amount of teams we can have. 
			... We want to create a new array, so we set the number of objects flexibly
			But we don't need to explicit name them: Java will name them [Team@174d20a, Team@66d2e7d9, Team@1efbd816, etc]
		*/
		
		//We will now create the our 2D array list
		try
		{
			if (arraylist_matchlist.size() > 0)	// But first check if our list is not empty
			{
				//We will now go through ResultsIn.txt to change the other variables of the Team objects
				String matchlinelookedat = "";
				String matchlinelookedat_team1;
				String matchlinelookedat_score1;
				String matchlinelookedat_team2;
				String matchlinelookedat_score2;
				try
				{
					//Declaring a file reader object and passing the file to it
					FileReader readerofmatchresults = new FileReader("ResultsIn.txt");
					//Declaring a scanner object... 
					///...and passing a file reader to it, not user typing  
					Scanner textofmatchresults = new Scanner(readerofmatchresults); 
					while (textofmatchresults.hasNextLine())
					{
						matchlinelookedat = textofmatchresults.nextLine();
						//splicing our match text
						matchlinelookedat_team1 = matchlinelookedat.substring(0,matchlinelookedat.indexOf(" "));
						matchlinelookedat_score1 = matchlinelookedat.substring(matchlinelookedat.indexOf(" ") + 1,matchlinelookedat.indexOf(" ") + 2);
						matchlinelookedat_team2 = matchlinelookedat.substring(matchlinelookedat.indexOf(" ") + 3,matchlinelookedat.length() - 2);
						matchlinelookedat_score2 = matchlinelookedat.substring(matchlinelookedat.length() - 1,matchlinelookedat.length());
						//storing the match details in an array for less coding (to create a for loop)
						/*
					 	   [team 1][score 1]
					 	   [team 2][score 2]	
						 */
						String [][] matchlookedat_table = new String [2][2];		
						matchlookedat_table[0][0] = matchlinelookedat_team1;		
						matchlookedat_table[0][1] = matchlinelookedat_score1;
						matchlookedat_table[1][0] = matchlinelookedat_team2;
						matchlookedat_table[1][1] = matchlinelookedat_score2;

						//Match by match we are looking at the outcomes and ...
						//... updating our Objects' properties
						for (i = 0; i < array_TeamsObjects.length; i++) {
														
							for(j = 0; j < 2; j++)			
							{
								if (matchlookedat_table[j][0].equals(String.valueOf(array_TeamsObjects[i].getTeamName())))
								{
									array_TeamsObjects[i].addGoals_for(Integer.parseInt(matchlookedat_table[j][1]));
									array_TeamsObjects[i].addGoals_against(Integer.parseInt(matchlookedat_table[1-j][1]));
									if (Integer.parseInt(matchlookedat_table[j][1]) > Integer.parseInt(matchlookedat_table[1-j][1])) {
										array_TeamsObjects[i].addMatches_won(1); }
									else if (Integer.parseInt(matchlookedat_table[j][1]) == Integer.parseInt(matchlookedat_table[1-j][1])) {
										array_TeamsObjects[i].addMatches_drawn(1); }
									else {
										array_TeamsObjects[i].addMatches_lost(1); }
								}	
							}	
							
						}
						
					} 
					readerofmatchresults.close();
					textofmatchresults.close();
				}
				catch (FileNotFoundException x) 
				{
					JOptionPane.showMessageDialog(null, "File Could Not Be Found", "Program Execution Error", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				catch (IOException x) 
				{
					JOptionPane.showMessageDialog(null, "File Input Output Problem", "Program Execution Error", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
			
			//Now we are populating our array list of results (which will populate the results table)

			for (i = 0; i < array_TeamsObjects.length; i++) 
			{
				Object[] array_tabledata = {
											array_TeamsObjects[i].getTeamName(),
											"",
											array_TeamsObjects[i].getMatches_won(),
											array_TeamsObjects[i].getMatches_drawn(),
											array_TeamsObjects[i].getMatches_lost(),
											array_TeamsObjects[i].getGoals_for(),
											array_TeamsObjects[i].getGoals_against(),
											array_TeamsObjects[i].getMatch_points(),
											array_TeamsObjects[i].getGoaldifference(),
											""
											};
				
				arraylist_resultstable.add(array_tabledata);
			}		
		}
		catch (NullPointerException x)
		{
			//Do nothing, just catch
		}
		
	}

	public int counttheTeams()
	{
		//initialize the increment variable that will count the teams		
		int i = 0;						

		try
		{
			//Declaring a file reader object and passing the file to it
			FileReader readerofteamnamesfile = new FileReader("TeamsIn.txt");
			//Declaring a scanner object... 
			///...and passing a file reader to it, not user typing  
			Scanner textofteamnames = new Scanner(readerofteamnamesfile); 
					
			do
			{				
				i++;
				textofteamnames.nextLine();				//nextLine() moves the scanner to the next space,tab or break
			} 
			while  (textofteamnames.hasNextLine());
								
			readerofteamnamesfile.close();	
			textofteamnames.close();
					
			}
			catch (FileNotFoundException x) 
			{
				JOptionPane.showMessageDialog(null, "File Could Not Be Found", "Program Execution Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			catch (IOException x) 
			{
				JOptionPane.showMessageDialog(null, "File Input Output Error", "Program Execution Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
				
			return i;
	}
	
	public int calculatepossiblematches()
	{
		int possiblematches = 0;
	
		int i = 0;													//standard incrementer
		int j = 0;													//nested incrementer

		
		//looking at the teams in a pairwise manner
		for(i = 0; i < arraylist_teamnames.size(); i++)				
		{
			for(j = i; j < arraylist_teamnames.size(); j++)
			{
				if (j != i)		//can't compare the same team
				{
					possiblematches++;
				}
			}
		}		
		
		return possiblematches;
	}		
	
	public int getNumberOfMatchesPlayed()
	{
		int numberofmatchesplayed = 0;
		
		try
		{
			//Declaring a file reader object and passing the file to it
			FileReader readerofmatchresults = new FileReader("ResultsIn.txt");
			//Declaring a scanner object and passing a file reader to it (not user typing)
			Scanner textofmatchresults = new Scanner(readerofmatchresults); 
			while (textofmatchresults.hasNextLine())
			{
				numberofmatchesplayed++;
				textofmatchresults.nextLine();
			} 
			readerofmatchresults.close();
			textofmatchresults.close();
			
		}
		catch (FileNotFoundException x) 
		{
			JOptionPane.showMessageDialog(null, "File Could Not Be Found", "Program Execution Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		catch (IOException x) 
		{
			JOptionPane.showMessageDialog(null, "File Input Output Problem", "Program Execution Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		return numberofmatchesplayed;
	}
	
	
	public void updateTeamsInSource(String teamtoremove_name)
	{
		//	Create a temporary array to store the new teams...
		String [] array_teamnames_temp = new String[numberofteams-1];
		
		//initialize the increment variable that will loop through the original teams		
		int i = 0;						
		//initialize the increment variable that will loop through the new teams
		int j = 0;		
		for (i = 0; i < arraylist_teamnames.size(); i++)
		{
			if (arraylist_teamnames.get(i).equals(teamtoremove_name))
			{
				//skip the team then
			}
			else
			{
				array_teamnames_temp[j] = arraylist_teamnames.get(i);
				j++;
			}			
		}

		//	... using the temporary array, reduce the number of teams in TeamsIn.txt
		try
		{
			PrintWriter writerofteamsin = new PrintWriter(new FileOutputStream(new File("TeamsIn.txt"),false));			//This is how you open the text file in an overwrite mode (last paramater is false)
			for(i = 0; i < array_teamnames_temp.length; i++)				
			{
				writerofteamsin.format("%s\n", array_teamnames_temp[i]);
			}			
			writerofteamsin.close();
			
		}
		catch (FileNotFoundException x) 
		{
			JOptionPane.showMessageDialog(null, "File Could Not Be Found", "Program Execution Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		catch (IOException x) 
		{
			JOptionPane.showMessageDialog(null, "File Input Output Problem", "Program Execution Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}		
	}		

	public void updateResultsInSource(String teamtoremove_name)
	{
		
		//initialize the increment variable that will loop through the original teams		
		int i = 0;							

		//	eliminating all mention of the teamtoremove from ResultsIn.txt
		try
		{
			PrintWriter writerofresultsin = new PrintWriter(new FileOutputStream(new File("ResultsIn.txt"),false));			//This is how you open the text file in an overwrite mode (last paramater is false)
			for(i = 0; i < arraylist_matchlist.size(); i++)				
			{
				if ((arraylist_matchlist.get(i)).contains(String.valueOf(teamtoremove_name)) || (arraylist_matchlist.get(i)).contains("*** no result yet"))
				{
					//skip it
				}
				else
				{
					writerofresultsin.format("%s\n", arraylist_matchlist.get(i));
				}
			}
			writerofresultsin.close();
			
		}
		catch (FileNotFoundException x) 
		{
			JOptionPane.showMessageDialog(null, "File Could Not Be Found", "Program Execution Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		catch (IOException x) 
		{
			JOptionPane.showMessageDialog(null, "File Input Output Problem", "Program Execution Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}		
	}	
	
}

//importing necessary libraries
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.*;
import java.util.List;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;

//		The controller process the user requests.
//		The controller registers listeners that are called when ...
//		... the View detects a user interaction.

//		The Controller needs the Model and View
public class Controller 
{
	//defining a private reference to a Model object
	private Model my_model_ref;
	//defining a private reference to a View object
	private View my_view_ref;
	
    //*----Constructor----*
    Controller(Model my_model, View my_view)
    {
    	//equating the inputs object of this method to our privately defined objects
    	my_model_ref = my_model;
    	my_view_ref = my_view;
    	
    	//add listeners to the view
    	my_view_ref.addImportButtonListener(new ImportListener());
    	my_view_ref.addAddResultButtonListener(new AddResultListener());
    	my_view_ref.addWithdrawButtonListener(new WithdrawListener());
    	my_view_ref.addCalculateResultsButtonListener(new CalculateResultsListener());
    	my_view_ref.addExitButtonListener(new ExitListener());
    	    	
    }
    
    //Now we will be defining the action event methods
    class ImportListener
    implements ActionListener
    {
    	public void actionPerformed(ActionEvent e) 
    	{
    		// check to see which widget the event came from
    		if (e.getSource() == my_view_ref.btn_importmatches)
    		{    			

    			//update the match list JList
    			my_view_ref.updateTheMatchListWidget();
    			//Note: All we are doing is importing to the match list JList from the match list array list
    			
    			//change other widgets accordingly
    			my_view_ref.lbl_possiblematches.setText("Total Possible Matches: " + my_model_ref.possiblematches);
    			my_view_ref.btn_addresult.setEnabled(true);
    			my_view_ref.combo_chooseteam1.setEnabled(true);
    			my_view_ref.text_score1.setEnabled(true);
    			my_view_ref.combo_chooseteam2.setEnabled(true);
    			my_view_ref.text_score2.setEnabled(true);
    			my_view_ref.btn_calcresults.setEnabled(true);
    			my_view_ref.combo_chooseteamwithdraw.setEnabled(true);
    			my_view_ref.btn_withdraw.setEnabled(true);
    			
    		}
    	}
    }
    
    class AddResultListener
    implements ActionListener
    {
    	String matchlinelookedat = "";
    	
    	public void actionPerformed(ActionEvent e) 
    	{
    		if (e.getSource() == my_view_ref.btn_addresult)
    		{	
    			//Error Check 1: first check if all the fields have been completed
    			if ((my_view_ref.combo_chooseteam1.getSelectedIndex() == - 1) || (my_view_ref.text_score1.getText().equals("")) || (my_view_ref.combo_chooseteam2.getSelectedIndex() == - 1) || (my_view_ref.text_score2.getText().equals("")))
    			{
    				JOptionPane.showMessageDialog(null, "You have not completed all the details of the match", "Incomplete Data Error", JOptionPane.ERROR_MESSAGE);
    			}
    			else
    			{
    				//Error Check 2: if the user chooses the same team twice
    				if (my_view_ref.combo_chooseteam1.getSelectedItem().toString() == my_view_ref.combo_chooseteam2.getSelectedItem().toString())
    				{
    					JOptionPane.showMessageDialog(null, "You need to choose two different teams for a match", "Incorrect Logic Error", JOptionPane.ERROR_MESSAGE);
    				}
    				else
    				{
    					//Error Check 3: if the user typed in a non-number in the goals textboxes
    					try
    					{
    						//Error Check 4: if the user typed in a number less than 0 or more than 9
    						if (Integer.parseInt(my_view_ref.text_score1.getText().trim()) < 0 || Integer.parseInt(my_view_ref.text_score1.getText().trim()) > 9 || 
    							Integer.parseInt(my_view_ref.text_score2.getText().trim()) < 0 || Integer.parseInt(my_view_ref.text_score2.getText().trim()) > 9)
    							{
    								JOptionPane.showMessageDialog(null, "The goals scores must anywhere between from 0 to 9", "Tournament Logic Error", JOptionPane.ERROR_MESSAGE);
    							}
    						else
    						{
    							
    							//now lets check if the game has not already been played
    			    			//initialize the increment variable that will go through the match list		
    			    			int i = 0;
    			    			//looking at the teams in a pairwise manner
    			    			for(i = 0; i < my_model_ref.arraylist_matchlist.size(); i++)				
    			    			{
    			    				matchlinelookedat = my_model_ref.arraylist_matchlist.get(i);
    								//if we find a match line mentioning the two teams' names already (and they actually played)
    								if (matchlinelookedat.contains(String.valueOf(my_view_ref.combo_chooseteam1.getSelectedItem())) == true && 
    									matchlinelookedat.contains(String.valueOf(my_view_ref.combo_chooseteam2.getSelectedItem())) == true &&
    									!(matchlinelookedat.contains("*** no result yet")))
    								{
    									JOptionPane.showMessageDialog(null, "These teams have already played", "Incorrect Logic Error", JOptionPane.ERROR_MESSAGE);
    									break;
    								}
    								else
    								{
    									//if we could not find the game and we are at the end of the for loop
    									if (i == (my_model_ref.arraylist_matchlist.size() - 1))
    									{
     										
    										try
    										{
    											
    											//1. Add this match to ResultsIn.txt
    											PrintWriter writerofmatchresults = new PrintWriter(new FileOutputStream(new File("ResultsIn.txt"),true));			//This is how you open the text file in an appendable mode (last paramater is true)
    											writerofmatchresults.println(String.valueOf(my_view_ref.combo_chooseteam1.getSelectedItem()) + " " + my_view_ref.text_score1.getText().trim() + " " +
    													String.valueOf(my_view_ref.combo_chooseteam2.getSelectedItem()) + " " + my_view_ref.text_score2.getText().trim());
    											writerofmatchresults.close();
    											//2. Update the match list array list
    											my_model_ref.getnewMatchListArrayList();
    											//3. Update the match list widget accordingly
    											my_view_ref.updateTheMatchListWidget();
    											
    											//re-align the header and then contents to centre - the model was reset within the function
    											DefaultTableCellRenderer renderer_resultstable = (DefaultTableCellRenderer) my_view_ref.tbl_rankings.getTableHeader().getDefaultRenderer();
    											renderer_resultstable.setHorizontalAlignment(0);
    											JTableUtilities.setCellsAlignment(my_view_ref.tbl_rankings, SwingConstants.CENTER);
    											break;
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
    					}
    					catch (NumberFormatException x)
    					{
    						JOptionPane.showMessageDialog(null, "The scores per team have to be integers", "Incorrect Logic Error", JOptionPane.ERROR_MESSAGE);
    					}
    				}
    			}   		
    		}  		
    	}
    }
    
    class WithdrawListener
    implements ActionListener
    {
    	public void actionPerformed(ActionEvent e) 
    	{
    		if (e.getSource() == my_view_ref.btn_withdraw)
    		{
    			//1. update the file TeamsIn.txt
    			my_model_ref.updateTeamsInSource(my_view_ref.combo_chooseteamwithdraw.getSelectedItem().toString());
    			//2. update the file ResultsIn.txt
    			my_model_ref.updateResultsInSource(my_view_ref.combo_chooseteamwithdraw.getSelectedItem().toString());	
    			//3. reset the teams, matches and results table array lists
    			my_model_ref.reset();
    			//4. update the match list JList
    			my_view_ref.updateTheMatchListWidget();
    			//5. for updating the results table JTable, let the use click on the calc results button

    			//updating the JComboBoxes
    			my_view_ref.updateTheComboBoxWidgets();   			
    			
    			//if there are less than three teams left, the tournament ends
    			if (my_model_ref.numberofteams < 3)
    			{
    				JOptionPane.showMessageDialog(null, "There are not enough teams left to have a tournament", "Tournament Logic Error", JOptionPane.ERROR_MESSAGE);
    				System.exit(0);    				
    			}
    		}
    	}
    }
    
    class CalculateResultsListener
    implements ActionListener
    {
    	public void actionPerformed(ActionEvent e) 
    	{
    		if (e.getSource() == my_view_ref.btn_calcresults)
    		{
    			//update the results table JTable
    			my_view_ref.updateTheResultsTableWidget();
    			//Note: All we are doing is importing to the results table JTable from the results table array list
    			
    			//re-align the header and then contents to centre - the model was reset within the function
    			DefaultTableCellRenderer renderer_resultstable = (DefaultTableCellRenderer) my_view_ref.tbl_rankings.getTableHeader().getDefaultRenderer();
    			renderer_resultstable.setHorizontalAlignment(0);
    			JTableUtilities.setCellsAlignment(my_view_ref.tbl_rankings, SwingConstants.CENTER);
    		
    			//updating the JComboBoxes
    			my_view_ref.updateTheComboBoxWidgets();
    		
    		}
    	}
    }
    
    class ExitListener
    implements ActionListener
    {
    	public void actionPerformed(ActionEvent e) 
    	{
    		if (e.getSource() == my_view_ref.btn_exit)
    		{
    			//before we exit the program lets write to ResultsOut.txt
    			try
    			{
    				PrintWriter writerofmatchresults = new PrintWriter(new FileOutputStream(new File("ResultsOut.txt"),false));			//This is how you open the text file in an overwrite mode (last paramater is false)
    				//if all the matches have been played, export the results table to TeamsOut.txt
    				if (my_model_ref.getNumberOfMatchesPlayed() == my_model_ref.possiblematches)
    				{
    					//lets print the headers first
    					writerofmatchresults.format("\t\t\t       Matches\t\t     Goals\tMatch\tGoal\n");
    					writerofmatchresults.format("Team\t\tRank\tWon\tDrawn\tLost\tFor\tAgainst\tPoints\tDiff\tMedal\n");												 	
    					for(int i = 0; i < my_model_ref.arraylist_teamnames.size(); i++)				
    					{
    						for(int j = 0; j < 10; j++)
    						{
    							if ((j == 0) && ((String.valueOf(my_view_ref.tbl_rankings.getValueAt(i,j))).length()<8)) 
    							{
    								writerofmatchresults.format("%s\t\t",String.valueOf(my_view_ref.tbl_rankings.getValueAt(i,j)));		//This is just to make more space - Some team names are too long
    							}		
    							else 
    							{
    								writerofmatchresults.format("%s\t",String.valueOf(my_view_ref.tbl_rankings.getValueAt(i,j)));
    								}		
    						}
    						writerofmatchresults.format("\n");
    					}	
    					 
    					writerofmatchresults.println("");
    					
    				}
    				//if all the matches have not yet been played, export the JList match list to TeamsOut.txt
    				else
    				{
    					for(int i=0; i < my_view_ref.model_matchresults.getSize(); i++)
    					{
    						writerofmatchresults.format("%s\n",String.valueOf(my_view_ref.model_matchresults.getElementAt(i))); 
    					}
    				}
    				writerofmatchresults.close();
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
    			//exit program
    			System.exit(0);
    		}    		
    		
    	}
    }
}

//importing necessary libraries
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;

//		This is to create the View. This View doesn't know about the Controller, ...
//		except that it provides methods for registering a Controller's listeners. 

//		The View needs the Model
public class View extends JFrame 
{

	//Declaring the objects (widgets) to be used
	JButton btn_importmatches, btn_addresult, btn_withdraw, btn_calcresults ,btn_exit;
	JList list_matchresults;
	JComboBox combo_chooseteam1, combo_chooseteam2, combo_chooseteamwithdraw;
	JTextField text_score1, text_score2;
	JTable tbl_rankings;
	JLabel lbl_matchresults, lbl_possiblematches, lbl_resultstable, lbl_addresult, lbl_team1, lbl_score1, lbl_team2, lbl_score2, lbl_withdraw;	
	//Declaring models to be used by our JList and JComboBoxes
	DefaultListModel model_matchresults = new DefaultListModel();			
	DefaultComboBoxModel model1_teamsin = new DefaultComboBoxModel();		
	DefaultComboBoxModel model2_teamsin = new DefaultComboBoxModel();		
	DefaultComboBoxModel model_teamswithdraw = new DefaultComboBoxModel();	
	//Declaring models to be used by our JTable, ...
	//...and overriding the match points and and goal difference column to be integers for ranking
	DefaultTableModel model_resultstable = new DefaultTableModel(){
        @Override
        public Class getColumnClass(int column) {
            switch (column) {
                case 7:	 return Integer.class;
                case 8:	 return Integer.class;
                default: return String.class;
            }
        }
    };																		//We just decided to override the method GetColumnClass only for model_resultstable	
	
	//*----Constructor----*
    //Empty constructor - we don't actually call it
	View()
	{
		reset();
	}
	public void reset()
	{
		//updating the JComboBoxes
		updateTheComboBoxWidgets();
		//updating the JLists
		updateTheMatchListWidget();								
		//updating the JTable	
		updateTheResultsTableWidget();						
	}		

    //defining a private reference to a Model object
    private Model my_model_ref;			
   
    //*----Constructor----*
    //We call it from class Main_Program
    View(Model my_model)
    {
    	//equating the input object of this method to our privately defined object
    	my_model_ref = my_model;
    	
		//set the properties of View JFrame
		setTitle("Welcome to the Hibernia JavaBall Tournament Scores Manager");
		setSize(1140, 570);
	    setLocation(50, 50);
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);		//Disabling the close (x) button on the frame

	    //define and initialize the widgets
	    btn_importmatches = new JButton("Import");
	    btn_addresult = new JButton("Add");
	    btn_withdraw = new JButton("Withdraw");
	    btn_calcresults = new JButton("Calculate Results");
	    btn_exit = new JButton("Exit");
	    list_matchresults = new JList(model_matchresults);				//This is where we feed our list our model - we will code with the model
	    combo_chooseteam1 = new JComboBox(model1_teamsin);				//This is where we feed our combobox our model - we will code with the model
	    combo_chooseteam2 = new JComboBox(model2_teamsin);				//This is where we feed our combobox our model - we will code with the model
	    combo_chooseteamwithdraw = new JComboBox(model_teamswithdraw);	//This is where we feed our combobox our model - we will code with the model
	    
	    tbl_rankings = new JTable(model_resultstable);					//This is where we feed out table our model - we will code with the model (mostly)
	    tbl_rankings.setAutoCreateRowSorter(true);
	    
	    text_score1 = new JTextField();
	    text_score2 = new JTextField();
	    lbl_matchresults = new JLabel("Match Results:");
	    lbl_possiblematches = new JLabel("Total Possible Matches: None yet");
	    lbl_resultstable = new JLabel("Results Table:");
	    lbl_addresult = new JLabel("Add Result:");
	    lbl_team1 = new JLabel("Choose team:");
	    lbl_score1 = new JLabel("Score");
	    lbl_team2 = new JLabel("Choose team:");
	    lbl_score2 = new JLabel("Score");
	    lbl_withdraw = new JLabel("Withdraw Team:");
	    
	    //set absolute layout (and sizes) - not working with relational positions
	    getContentPane().setLayout(null);
	    //place and position the widgets
	    add(lbl_matchresults);
	    lbl_matchresults.setBounds(5,5,100,30); //(x, y, width, height)
	    JScrollPane scrollable_list_matchresults = new JScrollPane(list_matchresults);
	    scrollable_list_matchresults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    add(scrollable_list_matchresults);
	    scrollable_list_matchresults.setBounds(5,40,380,250);
	    add(lbl_possiblematches);
	    lbl_possiblematches.setBounds(5,290,230,30);
	    add(btn_importmatches);
	    btn_importmatches.setBounds(315,290,80,30);
	    add(lbl_addresult);
	    lbl_addresult.setBounds(5,330,100,30);
	    add(lbl_team1);
	    lbl_team1.setBounds(5,355,100,30);	    
	    add(combo_chooseteam1);
	    combo_chooseteam1.setBounds(5-3,385,120,30);
	    add(lbl_score1);
	    lbl_score1.setBounds(125,355,50,30);
	    add(text_score1);
	    text_score1.setBounds(125-3,385,50,30);
	    add(lbl_team2);
	    lbl_team2.setBounds(175,355,100,30);	    
	    add(combo_chooseteam2);
	    combo_chooseteam2.setBounds(175-3,385,120,30);
	    add(lbl_score2);
	    lbl_score2.setBounds(295,355,50,30);
	    add(text_score2);
	    text_score2.setBounds(295-3,385,50,30);
	    add(btn_addresult);
	    btn_addresult.setBounds(345,385,50,30);	
	    add(lbl_withdraw);
	    lbl_withdraw.setBounds(5,430,100,30);
	    add(combo_chooseteamwithdraw);
	    combo_chooseteamwithdraw.setBounds(5-3,460,120,30);	    
	    add(btn_withdraw);
	    btn_withdraw.setBounds(125-3,460,100,30);
	    add(lbl_resultstable);
	    lbl_resultstable.setBounds(430,5,100,30);
	    
	    //put the table in scroll panel (to allow scrolling and to see table headers)
	    JScrollPane scrollable_resultstable = new JScrollPane(tbl_rankings);
	    add(scrollable_resultstable);
	    scrollable_resultstable.setBounds(430,40,700,200);
	    //set the height of the table headers
	    JTableHeader header_tablerankings = tbl_rankings.getTableHeader();
	    header_tablerankings.setPreferredSize(new Dimension(100, 30));
	    //align the header and then contents to centre
	    DefaultTableCellRenderer renderer_resultstable = (DefaultTableCellRenderer) tbl_rankings.getTableHeader().getDefaultRenderer();
	    renderer_resultstable.setHorizontalAlignment(0);
	    JTableUtilities.setCellsAlignment(tbl_rankings, SwingConstants.CENTER);
	    
	    add(btn_calcresults);
	    btn_calcresults.setBounds(430-5,240,150,30);
	    add(btn_exit);
	    btn_exit.setBounds(1060,510,80,30);	  
	    
	    //underline labels
	    Font font_matchresults = lbl_matchresults.getFont();
	    Map attributes_matchresults = font_matchresults.getAttributes();
	    attributes_matchresults.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	    lbl_matchresults.setFont(font_matchresults.deriveFont(attributes_matchresults));
	    Font font_addresult = lbl_addresult.getFont();
	    Map attributes_addresult = font_addresult.getAttributes();
	    attributes_addresult.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	    lbl_addresult.setFont(font_addresult.deriveFont(attributes_addresult));
	    Font font_withdraw = lbl_withdraw.getFont();
	    Map attributes_withdraw = font_withdraw.getAttributes();
	    attributes_withdraw.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	    lbl_withdraw.setFont(font_withdraw.deriveFont(attributes_withdraw));	    
	    Font font_resultstable = lbl_resultstable.getFont();
	    Map attributes_resultstable = font_resultstable.getAttributes();
	    attributes_resultstable.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	    lbl_resultstable.setFont(font_resultstable.deriveFont(attributes_resultstable));	

		//updating the JComboBoxes
		updateTheComboBoxWidgets();
	    
	    //set default properties for the widgets
	    btn_addresult.setEnabled(false);
	    combo_chooseteam1.setEnabled(false);
	    text_score1.setEnabled(false);
	    combo_chooseteam2.setEnabled(false);
	    text_score2.setEnabled(false);
	    btn_calcresults.setEnabled(false);
	    combo_chooseteamwithdraw.setEnabled(false);
	    btn_withdraw.setEnabled(false);
		
	    //make the frames visible and wait
	    setVisible(true);    	
    	
    }
    
    //creating action listener methods
    //
    //ActionListener is an interface (not a class) that contains a single method:
    //public void actionPerformed(ActionEvent evt) ;
    //
    //as soon as you code widgetx.addActionListener(this) the program starts listening...
    //for actions to be performed on that widget
    //
    //clicking a button is an action event, updating a combobox is an action event
    void addImportButtonListener(ActionListener al_import)
    {
    	btn_importmatches.addActionListener(al_import);
    }
    void addAddResultButtonListener(ActionListener al_addresult)
    {
    	btn_addresult.addActionListener(al_addresult);
    }
    void addWithdrawButtonListener(ActionListener al_withdraw)
    {
    	btn_withdraw.addActionListener(al_withdraw);
    }
    void addCalculateResultsButtonListener(ActionListener al_calcresults)
    {
    	btn_calcresults.addActionListener(al_calcresults);
    }
    void addExitButtonListener(ActionListener al_exit)
    {
    	btn_exit.addActionListener(al_exit);
    }
	
    /*
 		Note: Normally if the View and Controller where on the same class:
 		1. You would under the Class declaration have:
 		implements ActionListener		//... to listen for action events from the user
 		2. And then in the constructor at the end have:
 		btn_importmatches.addActionListener(this);
 		But we were creating a method here that can be accessed by the Controller
	*/
    
    
    //Miscellaneous methods used to reset (clear and re-populate) widgets
    void updateTheComboBoxWidgets()
    {
		//Resetting the JComboxes
		model1_teamsin.removeAllElements(); 			//This initializes (clears) the team 1 JComboBox
		model2_teamsin.removeAllElements(); 			//This initializes (clears) the team 2 JComboBox		
		model_teamswithdraw.removeAllElements();		//This initializes (clears) the team withdraw JComboBox
		for(String s:my_model_ref.arraylist_teamnames){		//This updates the team 1 JComboBox
			combo_chooseteam1.addItem(s);}
		for(String s:my_model_ref.arraylist_teamnames){		//This updates the team 2 JComboBox
			combo_chooseteam2.addItem(s);}
		for(String s:my_model_ref.arraylist_teamnames){		//This updates the team withdraw JComboBox
			combo_chooseteamwithdraw.addItem(s);}
		/*
		  	Note: In the code above...
		  		  Because the array list arraylist_teamnames was defined in the class Model...
		  		  and we create an object of the class Model called my_model_ref ...
		  		  ... arrayist_teamnames is an attribute of my_model_ref
		  		  ... my_model_ref inherits this 'property' from the class Model 
		 */		
		
    }
    
    void updateTheMatchListWidget()
    {
    	model_matchresults.removeAllElements();			//This initializes (clears) the match list JList
    	
    	//update the JList match list from the match list array list
		for(int i = 0; i < my_model_ref.arraylist_matchlist.size(); i++)				
		{
			model_matchresults.addElement(my_model_ref.arraylist_matchlist.get(i));
		}
    }

    void updateTheResultsTableWidget()
    {
    	//this is a ranking incrementer that will increase as we look down the ranked table
    	int ranking = 1;
    	//creating an array of medals and non-medals
    	String [] array_medals = new String[my_model_ref.numberofteams];
    	array_medals[0] = "Gold";
    	array_medals[1] = "Silver";
    	array_medals[2] = "Bronze";
    	//this is a incrementer to manager assigning different medals
    	int m = 0;
    	
    	//First initialize the table
    	//1.Create the column headings
    	String[] columnNames = 	{"Team", "Rank", "<HTML>Matches<BR>Won</HTML>", "<HTML>Matches<BR>Drawn</HTML>", "<HTML>Matches<BR>Lost</HTML>", "<HTML>Goals<BR>For</HTML>", "<HTML>Goals<BR>Against</HTML>", "<HTML>Match<BR>Points</HTML>", "<HTML>Goal<BR>Diff</HTML>", "Medal"};
    	//2.Set the body...
    	//...for which we need an array and array (not an array and an array list)
    	//...thus lets quickly convert the results table array list to an array
    	Object [][] array_resultstable_temp = new Object[my_model_ref.numberofteams][10];
    	array_resultstable_temp = (my_model_ref.arraylist_resultstable).toArray(array_resultstable_temp);
    	model_resultstable.setDataVector(array_resultstable_temp,columnNames);		
			    				
    	//Now we need to sort this table based firstly on match points...
    	TableRowSorter<TableModel> sorter = new TableRowSorter<>(model_resultstable);
    	tbl_rankings.setRowSorter(sorter);
    	List<RowSorter.SortKey> sortKeys = new ArrayList<>();
    	sortKeys.add(new RowSorter.SortKey(7, SortOrder.DESCENDING));	//Match Points (Col 7) DESC
    	//... then sort the table based on goals difference
    	sortKeys.add(new RowSorter.SortKey(8, SortOrder.DESCENDING));	//Goal Diff (Col 8) DESC
    	//... then sort the table alphabetically by team name
    	sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));	//Team Name (Col 0) ASC
    	sorter.setSortKeys(sortKeys);
    	sorter.sort();
		    				
    	//But we have only sorted the view. We have not sorted the model.
    	//thus don't use table.getModel().getValue(). Just use table.getValue()
		    				
    	//Now we need to assign ranks...
    	String lookedatteam_matchpoints;
    	String lookedatteam_goaldifference;
    	String previousteam_matchpoints;
    	String previousteam_goaldifference;
    	//... and assign medals, but only once the tournament is finished
    	for(int i = 0; i < my_model_ref.arraylist_teamnames.size(); i++)		//going down results table, for as many teams as there are		
    	{
    		//if there is a team in the cell
    		if (!(tbl_rankings.getValueAt(i,0).equals("")))		//making double sure we are not pointing at an empty cell
    		{
    			//for the special case of looking at the top most row - the winner...
    			if (i == 0) 
    			{
    				tbl_rankings.setValueAt(ranking,i,1);					//rank is in column 1
    				if (my_model_ref.getNumberOfMatchesPlayed() == my_model_ref.possiblematches) {
    					tbl_rankings.setValueAt(array_medals[m],i,9); }
    				m++;
    				ranking++;
    				}
    				//... else for all other lower ranked teams
    			else
    			{
    				lookedatteam_matchpoints = String.valueOf(tbl_rankings.getValueAt(i,7));
    				lookedatteam_goaldifference = String.valueOf(tbl_rankings.getValueAt(i,8));
    				previousteam_matchpoints = String.valueOf(tbl_rankings.getValueAt(i-1,7));
    				previousteam_goaldifference = String.valueOf(tbl_rankings.getValueAt(i-1,8));
    				
    				//if the current team has the same match points and goal difference...
    				if (lookedatteam_matchpoints.equals(previousteam_matchpoints) &&  lookedatteam_goaldifference.equals(previousteam_goaldifference))							
    				{
    					//...take the same ranking as the predecessor
    					tbl_rankings.setValueAt(tbl_rankings.getValueAt(i-1,1),i,1);
    					if (my_model_ref.getNumberOfMatchesPlayed() == my_model_ref.possiblematches) {
    						tbl_rankings.setValueAt(tbl_rankings.getValueAt(i-1,9),i,9); }
    					ranking++;
    				}
    				else
    				{
    					tbl_rankings.setValueAt(ranking,i,1);
    					if (my_model_ref.getNumberOfMatchesPlayed() == my_model_ref.possiblematches) {
    						tbl_rankings.setValueAt(array_medals[m],i,9); }
    					m++;
    					ranking++;
    				}
    			}
    		}
    	}
    }
    
}

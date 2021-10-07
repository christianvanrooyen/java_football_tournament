
//		The main program initializes everything and ties everything together.

public class Main_Program 
{
    //	  Create Model, View, and Controller.  They are ...
    //    ... created once here and passed to the parts that ...
    //    ... need them so there is only one copy of each.
	
	public static void main(String[] args) 
	{	
		
		Model my_model = new Model();
		View my_view = new View(my_model);
		Controller my_controller = new Controller(my_model,my_view);
		
	}

}

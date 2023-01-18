package project4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * MountainClimb class is the program responsible for parsing and validating the command line arguments, 
 * reading and parsing the input file, producing any error messages, handling any exceptions thrown by 
 * other classes, and producing output.  
 *
 * @author Nicole Issagholian
 *
 */

public class MountainClimb {
	
	//data field
	File inputFile;
			
	/**
	* The main() method of this program. 
	* 
	* @param a String array args provided on the command line when the program begins; 
	* the first string should be the name of the input file containing the data for the tree species. 
	*/
	public static void main(String[] args) {
		
		//object of type BSTMountain
		BSTMountain mountain = new BSTMountain();
				
		//verify that the command line argument exists
		if (args.length == 0) {
			System.err.println("Usage Error: the program expects file name as an argument.\n");
			System.exit(1);
		}
					
		//verify that command line argument contains a name of an existing file
		File inputFile = new File(args[0]); 
					
		if (!inputFile.exists()){
			System.err.println("Error: the file " + inputFile.getAbsolutePath() + " does not exist.\n"); 
			System.exit(1);
		}
		if (!inputFile.canRead()){ 
			System.err.println("Error: the file " + inputFile.getAbsolutePath() + " cannot be opened for reading.\n");
		}
			
		try {
			Scanner data = new Scanner(inputFile);		
			
			//while loop that parses through data in input file
			while(data.hasNext()) {
                String eachLine = data.nextLine();
                String[] values = eachLine.split(" ");
                ArrayList<String> supplies = new ArrayList<>(); //arraylist that stores supplies
                ArrayList<String> obstacles = new ArrayList<>(); //arraylist that stores obstacles

                //if length of each line is greater than 1, loop through words in each line
                if (values.length > 1) {
                    for (int i = 1; i < values.length; i++) {
                        switch (values[i]) {
                            //if the word is a supply (food, raft, axe), the word is added to the supplies arraylist
                            case "food":
                            case "raft":
                            case "axe":
                                supplies.add(values[i]);
                                break;
                            //if the word is river, the word is added to the obstacles arraylist
                            case "river":
                                obstacles.add(values[i]);
                                break;
                            //if the word is fallen and the next word is tree, then "fallen tree" is added to the obstacles arraylist
                            case "fallen":
                                if (i != values.length - 1) {
                                    if (values[i + 1].equals("tree")) {
                                        obstacles.add("fallen tree");
                                    }
                                }
                                break;
                        }
                    }
                }
                //add the rest stops and each of their supplies and obstacles to the mountain
                mountain.add(new RestStop(values[0], supplies, obstacles));
			}
			data.close();
		}
			
		catch(FileNotFoundException e) {
			System.err.println("Error: the file file" + inputFile.getAbsolutePath() + "cannot be opened for reading");
			System.exit(1);
		}
		catch (IllegalArgumentException e) {
			System.err.println("Error: null or invalid entry given.");
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
		
		//traverse the mountain
		mountain.traverse();
	}
}

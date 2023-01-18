package project4;

import java.util.ArrayList;

/**
 * The RestStop class represents a single rest stop.
 * RestStop stores the label of the rest stop along with a list of the supplies that a hiker 
 * can collect at this rest stop and a list of obstacles that a hiker may encounter at this rest stop.
 * This class implements the Comparable interface. 
 * The class contains getter, compareTo, and toString methods.
 * 
 * @author Nicole Issagholian 
 *
 */

public class RestStop implements Comparable<RestStop> {
	
	//data fields
	private String label; //label of rest stop
	private ArrayList<String> theSupplies; //arraylist of supplies
	private ArrayList<String> theObstacles; //arraylist of obstacles

    /**
     * Constructor that creates a rest stop with the given label, supplies, and obstacles.
     *
     * @param label is a string of the rest stop's name
     * @param supplies is an ArrayList containing the supplies at the rest stop
     * @param obstacles is an ArrayList containing the obstacles to be overcome at the rest stop
     */
    public RestStop (String label, ArrayList<String> theSupplies, ArrayList<String> theObstacles) {
        this.label = label;
        this.theSupplies = theSupplies;
        this.theObstacles = theObstacles;
    }

	/**
	 * Getter for the label of the rest stop
	 * 
	 * @return a string representation of the rest stop's name
	 */
	public String getLabel() {
		return label;
	}

    /**
     * Getter for the obstacles
     *
     * @return an ArrayList of strings representing obstacles
     */
    public ArrayList<String> getObstacles() {
        return theObstacles;
    }

    /**
     * Getter for the supplies
     *
     * @return an ArrayList of strings representing supplies
     */
    public ArrayList<String> getSupplies() {
        return theSupplies;
    }

    /**
     * Returns a string containing the label, supplies, and obstacles of the rest stop
     *
     * @return a string representation of the RestStop
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.label);
        
        //for loop that appends supplies to string        
        for (int i=0; i<theSupplies.size(); i++) {
            String supply = theSupplies.get(i);
        	sb.append(" ").append(supply);
        }
        
        //for loop that appends obstacles to string
        for (int i=0; i<theObstacles.size(); i++) {
            String obstacle = theObstacles.get(i);
        	sb.append(" ").append(obstacle);
        }
        return sb.toString();     
    }

    /**
	 * Performs comparison between the labels of two rest stops
	 *
	 * @param other is the RestStop object to be compared to
	 * @return a positive integer when this is greater than o, a negative integer when this is less than o, 
	 * and a 0 when they are equal
	 */
	@Override
	public int compareTo(RestStop other) {
		return this.label.compareTo(other.getLabel());
	}
}

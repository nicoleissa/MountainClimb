package project4;

/**
 * The Hiker class represents a hiker traveling down the mountain.
 * An object of this class will keep track of all the supplies that the hiker
 * has in their possession. 
 * This information is updated as the hiker travels along a trail and consumes supplies.
 * 
 * @author Nicole Issagholian 
 *
 */

public class Hiker {

	//data fields
	private int countFood; //number of foods
	private int countAxes; //number of axes
	private int countRafts; //number of rafts

	/**
	 * Default constructor that initializes data fields.
	 */
	public Hiker() {
		this.countFood = 0;
	    this.countAxes = 0;
		this.countRafts = 0;
	}

    /**
     * Constructor, with parameter, that creates a copy of the given Hiker object
     *
     * @param hikerPerson is the Hiker to be copied
     */
    public Hiker(Hiker hikerPerson) {
        this.countFood = hikerPerson.countFood;
        this.countAxes = hikerPerson.countAxes;
        this.countRafts = hikerPerson.countRafts;
    }

    /**
     * Determines if the hiker can continue through the rest stop
     *
     * @param restStop is the current rest stop that the hiker is at
     * @return true if there is at least one food, one axe per fallen tree, and one raft per river; 
     * otherwise returns false
     */
    public boolean processRestStop(RestStop restStop) {
    	
    	//for loop that adds supplies to the hiker    	
    	for (int i=0; i<restStop.getSupplies().size(); i++) {
    		String supply = restStop.getSupplies().get(i);
    		
    		//if the supply is a food item, increment count of food
    		if (supply.equals("food")) {
    			this.countFood++;
    		}
    		
    		//if the supply is an axe, increment count of axes
	        else if (supply.equals("axe")) {
	            this.countAxes++;
	        }
    		
    		//if the supply is a raft, increment count of rafts
	        else if (supply.equals("raft")) {
	            this.countRafts++;
	        }
	    }
    	
	    //for loop that removes supplies to deal with obstacles
	    //if the hiker is missing supplies, return false    
	    for (int i=0; i<restStop.getObstacles().size(); i++) {
	    	String obstacle = restStop.getObstacles().get(i);
	    	
	    	//if the obstacle is a river, a raft will be used so
	    	//decrement count of rafts if greater than 0; else, return false
	    	if (obstacle.equals("river")) {
	    		if (countRafts > 0) {
	    			this.countRafts--;
	    		}
	            else {
	                return false;
	            }
	    	}

	    	//if the obstacle is a fallen tree, an axe will be used so 
	    	//decrement count of axes if greater than 0; else, return false 
	        else if (obstacle.equals("fallen tree")) {
	        	if (countAxes > 0) {
	        		this.countAxes--;
	        	}
	            else {
	                return false;
	            }
	        }
	    }
	    
	    //decrement count of food if greater than 0; else, return false
	    this.countFood--;
	    if (countFood < 0) {
	    	return false;
	    }

        return true;
    }
}

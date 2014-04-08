package Project1;

/**
 * @author Prabhdeep Singh
 * @project 1
 * @class CMSC 341 M-W 5:30-6:45pm
 * @section 3
 * @email CW28656@umbc.edu
 */
/**
 * The vehicle class is used to represent
 * simulated vehicles as they get added to
 * the intersection. Only the type of vehicle
 * and the time it entered are recorded. The
 * direction of travel is not, seeing as that
 * is handled by the queue implementations.
 */
public class Vehicle {

    private final char type;
    private final int timeEntered;

    /**
     * Constructs a new vehicle that entered the
     * intersection at a given time.
     *
     * @param type          The type of vehicle this
     *                      is. The only used types
     *                      are t for a truck
     *                      and c for a car.
     * @param timeEntered   The time the vehicle was
     *                      added to the intersection.
     */

    public Vehicle(final char type, final int timeEntered) {
        this.type = type; // type of the vehicle
        this.timeEntered = timeEntered; // time entered
    }

    /**
     * Gets the type
     * @return type
     */
    public char getType(){
        return type; // the type
    }

    /**
     * Gets the time entered
     * @return timeEntered
     */
    public int getTimeEntered(){
        return timeEntered; // the time enetered
    }

}

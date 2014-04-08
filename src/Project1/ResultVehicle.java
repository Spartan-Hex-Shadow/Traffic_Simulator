package Project1;

/**
 * @author Prabhdeep Singh
 * @project 1
 * @class CMSC 341 M-W 5:30-6:45pm
 * @section 3
 * @email CW28656@umbc.edu
 */

/**
 * The ResultVehicle class is
 * used to store needed results. It
 * only preserves important details,
 * such as the type of vehicle and the
 * time the vehicle waited.
 */
public class ResultVehicle {

    private final char type;
    private final int waitTime;

    /**
     * Builds a new ResultVehicle to
     * wrap around the given Vehicle.
     *
     * @param vehicle   The vehicle to
     *                  represent for this
     *                  result.
     * @param time      The time this result was
     *                  processed.
     */

    public ResultVehicle(final Vehicle vehicle, final int time){
        this.type = vehicle.getType(); // get vehicle type
        this.waitTime = time - vehicle.getTimeEntered(); // wait time is the time subtracted from the time the vehicle entered
    }

    /**
     * Gets the waiting time
     * @return waitTime
     */
    public int getWaitTime(){
        return waitTime; // return wait time
    }

    /**
     * Gets the type
     * @return type
     */

    public char getType(){
        return type; // return type
    }

}

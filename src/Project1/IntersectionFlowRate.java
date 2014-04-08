package Project1;

/**
 * @author Prabhdeep Singh
 * @project 1
 * @class CMSC 341 M-W 5:30-6:45pm
 * @section 3
 * @email CW28656@umbc.edu
 */

/**
 *
 * This class is used to record data for the flow rate
 * for the intersection. Due to the limited usage of
 * the driver class, this will be used in a static
 * context.
 * There are four recorded flow rates, each with equal
 * precedence.
 *     eastFlowRate     The flow rate of vehicles
 *                          travelling in an eastern
 *                          direction.
 *     westFlowRate     The flow rate of vehicles
 *                          travelling in an western
 *                          direction.
 *     northFlowRate    The flow rate of vehicles
 *                          travelling in an northern
 *                          direction.
 *     southFlowRate    The flow rate of vehicles
 *                          travelling in an southern
 *                          direction.
 *
 *
 */

public class IntersectionFlowRate {

    private static int eastFlowRateCars;
    private static int westFlowRateCars;
    private static int northFlowRateCars;
    private static int southFlowRateCars;

    private static int eastFlowRateTrucks;
    private static int westFlowRateTrucks;
    private static int northFlowRateTrucks;
    private static int southFlowRateTrucks;

    // Getters that get the flow rates in directions of the cars and trucks
    /**
     * Gets the flow rate of the cars
     * @return flow rate of the cars
     */
    public static int getEastFlowRateCars() {
        return eastFlowRateCars;
    }
    /**
     * Gets the flow rate of the cars
     * @return flow rate of the cars
     */

    public static int getWestFlowRateCars() {
        return westFlowRateCars;
    }
    /**
     * Gets the flow rate of the cars
     * @return flow rate of the cars
     */

    public static int getNorthFlowRateCars() {
        return northFlowRateCars;
    }
    /**
     * Gets the flow rate of the cars
     * @return flow rate of the cars
     */

    public static int getSouthFlowRateCars() {
        return southFlowRateCars;
    }
    /**
     * Gets the flow rate of the trucks
     * @return flow rate of the trucks
     */

    public static int getEastFlowRateTrucks() {
        return eastFlowRateTrucks;
    }
    /**
     * Gets the flow rate of the trucks
     * @return flow rate of the trucks
     */

    public static int getWestFlowRateTrucks() {
        return westFlowRateTrucks;
    }
    /**
     * Gets the flow rate of the trucks
     * @return flow rate of the trucks
     */

    public static int getNorthFlowRateTrucks() {
        return northFlowRateTrucks;
    }
    /**
     * Gets the flow rate of the trucks
     * @return flow rate of the trucks
     */

    public static int getSouthFlowRateTrucks() {
        return southFlowRateTrucks;
    }

    /**
     * Sets the flow rate given the vehicles per
     * minute. As per program requirements, the
     * rate should divide 60.
     *
     * @param rate      The vehicles per minute
     *                  for this direction.
     */

    public static void setEastFlowRateCars(int rate) {
        check(rate); // checks to see if the rate is valid
        eastFlowRateCars = 60 / rate;
    }

    /**
     * Sets the flow rate given the vehicles per
     * minute. As per program requirements, the
     * rate should divide 60.
     *
     * @param rate      The vehicles per minute
     *                  for this direction.
     */

    public static void setWestFlowRateCars(int rate) {
        check(rate);
        westFlowRateCars = 60 / rate;
    }

    /**
     * Sets the flow rate given the vehicles per
     * minute. As per program requirements, the
     * rate should divide 60.
     *
     * @param rate      The vehicles per minute
     *                  for this direction.
     */

    public static void setNorthFlowRateCars(int rate) {
        check(rate);
        northFlowRateCars = 60 / rate;
    }

    /**
     * Sets the flow rate given the vehicles per
     * minute. As per program requirements, the
     * rate should divide 60.
     *
     * @param rate      The vehicles per minute
     *                  for this direction.
     */

    public static void setSouthFlowRateCars(int rate) {
        check(rate);
        southFlowRateCars = 60 / rate;
    }

    /**
     * Sets the flow rate given the vehicles per
     * minute. As per program requirements, the
     * rate should divide 60.
     *
     * @param rate      The vehicles per minute
     *                  for this direction.
     */

    public static void setEastFlowRateTrucks(int rate) {
        check(rate);
        eastFlowRateTrucks = 60 / rate;
    }

    /**
     * Sets the flow rate given the vehicles per
     * minute. As per program requirements, the
     * rate should divide 60.
     *
     * @param rate      The vehicles per minute
     *                  for this direction.
     */

    public static void setWestFlowRateTrucks(int rate) {
        check(rate);
        westFlowRateTrucks = 60 / rate;
    }

    /**
     * Sets the flow rate given the vehicles per
     * minute. As per program requirements, the
     * rate should divide 60.
     *
     * @param rate      The vehicles per minute
     *                  for this direction.
     */

    public static void setNorthFlowRateTrucks(int rate) {
        check(rate);
        northFlowRateTrucks = 60 / rate;
    }

    /**
     * Sets the flow rate given the vehicles per
     * minute. As per program requirements, the
     * rate should divide 60.
     *
     * @param rate      The vehicles per minute
     *                  for this direction.
     */

    public static void setSouthFlowRateTrucks(int rate) {
        check(rate);
        southFlowRateTrucks = 60 / rate;
    }

    /**
     * used internally to ensure that all flow
     * rates are positive or equal to 0.
     * @param rate      The rate to check, if
     *                  not valid, an
     *                  IllegalArgumentException
     *                  is thrown.
     */

    public static void check(final int rate){
        if(rate < 0){ // checks to see if the rate is less than 0
            throw new IllegalArgumentException("Rate cannot be negative."); // then throws an illegal argument exception because the rate cannot possibly be negative
        }
    }
}

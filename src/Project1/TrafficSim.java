package Project1;
/**
 * @author Prabhdeep Singh
 * @project 1
 * @class CMSC 341 M-W 5:30-6:45pm
 * @section 3
 * @email CW28656@umbc.edu
 */

import java.io.*;
import java.nio.CharBuffer;
import java.text.DecimalFormat;
import java.util.*;
import java.math.RoundingMode;

/**
 * This simulates traffic flow in a
 * simulated intersection. There are many
 * defining factors here, including
 * the lights, queue sizes and speeds.
 * Most noteworthy would be the traffic
 * lights
 * This will ensure that maximum rate of
 * flow is always achieved, at least to
 * a level of acceptance.
 */
public class TrafficSim {

    private final static String DIVIDEND = "----------------------------------------------------";

    /**
     * The queues for each direction entering
     * the intersection. These are arranged
     * such that the northernQueue
     * would contain vehicles queue in the
     * northern part of the intersection,
     * travelling downwards.
     */

    private final Queue<Vehicle> northBoundQueue = new java.util.LinkedList(); // direction queue with LinkedList implementation
    private final Queue<Vehicle> westBoundQueue = new java.util.LinkedList();  // direction queue with LinkedList implementation
    private final Queue<Vehicle> southBoundQueue = new java.util.LinkedList(); // direction queue with LinkedList implementation
    private final Queue<Vehicle> eastBoundQueue = new java.util.LinkedList();  // direction queue with LinkedList implementation
    // Creates a linkedList of results based upon our own implementation of a linked list
    private final LinkedList<ResultVehicle> results = new LinkedList<>();

    private boolean priorityFlow = true;
    private int lastSwitch = 0;

    private boolean truckNorth;
    private boolean truckSouth;
    private boolean truckWest;
    private boolean truckEast;

    /**
     * Constructs a new traffic simulator
     * based off of the given input file. The
     * input file will contain data regarding
     * traffic flow rates, as per each direction.
     * It is important that the input file be
     * formatted correctly, to an extent, as per
     * the program requirements shown in the
     * instructions.
     *
     * @param fileName The file to read from.
     */

    public TrafficSim(final String fileName) {
        if (!readFromFile(fileName)) { // If cannot read from file
            throw new AssertionError("Failed to read from file"); // then this error is thrown
        }
        // add vehicle
        addVehicle('n', new Vehicle('c', 0));
        addVehicle('n', new Vehicle('c', 0));
        addVehicle('e', new Vehicle('c', 0));
        addVehicle('e', new Vehicle('c', 0));
        addVehicle('s', new Vehicle('c', 0));
        addVehicle('s', new Vehicle('c', 0));
        addVehicle('w', new Vehicle('c', 0));
        addVehicle('w', new Vehicle('c', 0));
        printBoard(0);
        for (int i = 1; i <= 120; i++) {
            handleCars(i); // handle cards
            handleNewVehicles(i); // handle new vehicles
            handleLights(i); // handle lights
            printBoard(i); // prints the board
        }

        int carCount = 0; // initialize car count to 0
        int truckCount = 0; // initialize truck count to 0
        int totalWaitTimes = 0; // initialize total wait time to 0
        final DecimalFormat format = new DecimalFormat("#.000000");
        format.setRoundingMode(RoundingMode.HALF_UP);
        for (final ResultVehicle vehicle : results.toArray(new ResultVehicle[0])) { // checks to see the type of vehicle
            if (vehicle.getType() == 'c') { // if its a car, the carcount is incremented
                carCount++;
            } else {
                truckCount++; // else increment the truckcount
            }
            totalWaitTimes += vehicle.getWaitTime();
        }
        System.out.println("The final results are:");
        System.out.println("The number of vehicles that passed through the intersection is: " + (carCount + truckCount));
        System.out.println("The number of cars that passed through the intersection is: " + carCount);
        System.out.println("The number of trucks that passed through the intersection is: " + truckCount);
        System.out.println("The average wait time for this intersection is: " + format.format(totalWaitTimes / ((double) carCount + truckCount)));
    }

    /**
     * This will read from a denoted input
     * file.
     * @param fileName The file to read from.
     *                 It will be checked first
     *                 if the program can read
     *                 from the file.
     * @return Whether or not the file
     *         was parsed correctly.
     */

    public boolean readFromFile(String fileName) {
        final File file = new File(fileName);
        if (file.exists() && file.canRead()) { // checks to see if the file exists and is readable
            try {
                final InputStream stream = new FileInputStream(file);
                final Scanner in = new Scanner(stream);
                in.useDelimiter("([\\s:]|(//.*\\s))+");
                while (in.hasNext()) { // As long as there are elements to be read from the file
                    final char direction = Character.toLowerCase(in.next().charAt(0)); // make lowercase
                    // Direction and flow rate of the cars and trucks in that particular direction
                    switch (direction) {
                        case 'n':
                            IntersectionFlowRate.setNorthFlowRateCars(in.nextInt());
                            IntersectionFlowRate.setNorthFlowRateTrucks(in.nextInt());
                            break;
                        case 's':
                            IntersectionFlowRate.setSouthFlowRateCars(in.nextInt());
                            IntersectionFlowRate.setSouthFlowRateTrucks(in.nextInt());
                            break;
                        case 'e':
                            IntersectionFlowRate.setEastFlowRateCars(in.nextInt());
                            IntersectionFlowRate.setEastFlowRateTrucks(in.nextInt());
                            break;
                        case 'w':
                            IntersectionFlowRate.setWestFlowRateCars(in.nextInt());
                            IntersectionFlowRate.setWestFlowRateTrucks(in.nextInt());
                            break;
                        default:
                            stream.close();
                            return false;
                    }
                }
                stream.close();
                return true;
            } catch (final IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Cannot read from file: " + fileName);
        }
        return false;
    }

    /**
     * Adds a vehicle to the appropriate queue.
     * @param direction The queue to add to. This
     *                  must be one of:
     *
     *                  n - Northern Queue
     *                  w - Southern Queue
     *                  s - Western Queue
     *                  e - Eastern Queue
     * @param vehicle   The Vehicle to add
     */

    public void addVehicle(char direction, Vehicle vehicle) {
        // Adds vehicle to the queue
        queueByDirection(direction).add(vehicle);
    }

    /**
     * Adds corresponding vehicles to queues
     * dependent on the IntersectionFlowRate
     * and the current clock.
     * Assuming a car and truck are to be added at
     * the same time, to the same queue, the car
     * would be added first.
     *
     * @param time The current clock value
     */

    public void handleNewVehicles(final int time) {
        // Adds vehicles to the queue depending on the flow rate and clock, wih car being first incase a truck and car have to be added at the same time
        // if the remainder of time and intersectionflow rate of direction == 0, adds a new vehicle(car or truck) to the directionqueue
        if (time % IntersectionFlowRate.getNorthFlowRateCars() == 0) { // if the remainder of time and intersectionflow rate of direction == 0, adds a new vehicle(car or truck)
            addVehicle('n', new Vehicle('c', time));
        }
        if (time % IntersectionFlowRate.getNorthFlowRateTrucks() == 0) {
            addVehicle('n', new Vehicle('t', time));
        }
        if (time % IntersectionFlowRate.getEastFlowRateCars() == 0) {
            addVehicle('e', new Vehicle('c', time));
        }
        if (time % IntersectionFlowRate.getEastFlowRateTrucks() == 0) {
            addVehicle('e', new Vehicle('t', time));
        }
        if (time % IntersectionFlowRate.getSouthFlowRateCars() == 0) {
            addVehicle('s', new Vehicle('c', time));
        }
        if (time % IntersectionFlowRate.getSouthFlowRateTrucks() == 0) {
            addVehicle('s', new Vehicle('t', time));
        }
        if (time % IntersectionFlowRate.getWestFlowRateCars() == 0) {
            addVehicle('w', new Vehicle('c', time));
        }
        if (time % IntersectionFlowRate.getWestFlowRateTrucks() == 0) {
            addVehicle('w', new Vehicle('t', time));
        }
    }

    /**
     * This handles the lights of the intersection.
     * This will attempt to have the North-South
     * lane flowing as much as possible, with a
     * minimum light-length of 30 seconds. Once
     * there are no more available cars in both the
     * North and South queues, it will switch to the
     * East-West.
     * The East-West queue will be on for a minimum
     * light-length of 10 seconds and a maximum of
     * 30 seconds.
     *
     * @param time The current clock value.
     */

    public void handleLights(final int time) {
        if (priorityFlow) {
            if (eastBoundQueue.isEmpty() && westBoundQueue.isEmpty()) {
                return;
            }
            if (time - lastSwitch >= 30) {
                priorityFlow = false;
                lastSwitch = time;
            }
        } else {
            if (time - lastSwitch >= 30) {
                priorityFlow = true;
                lastSwitch = time;
            }
            if (time - lastSwitch >= 10 && eastBoundQueue.isEmpty() && westBoundQueue.isEmpty()) {
                priorityFlow = true;
                lastSwitch = time;
            }
        }
    }

    /**
     * This method will handle all vehicles
     * travelling through the intersection based
     * off of the light value.
     *
     * @param time The current clock value.
     */

    public void handleCars(final int time) {
        if (priorityFlow) { // checking to see if priority flow
            handleCar('n', time);
            handleCar('s', time);
        } else {
            handleCar('w', time);
            handleCar('e', time);
        }
    }

    /**
     * This method will handle each vehicle
     * individually.
     * @param direction The direction to handle
     *                  This must be one of:
     *                  n - Northern Queue
     *                  w - Southern Queue
     *                  s - Western Queue
     *                  e - Eastern Queue
     *
     * @param time      The current clock value.
     */

    public void handleCar(final char direction, final int time) {
        final Queue<Vehicle> queue = queueByDirection(direction); // Queue of Vehicles with direction
        if (!queue.isEmpty()) { // Checking to see if the queue is empty or not
            Vehicle next = null;
            if (queue.peek().getType() == 't') {
                final boolean truckSeen = truckSeen(direction);
                if (truckSeen) {
                    next = queue.poll();
                }
                setTruck(direction, !truckSeen);
            } else {
                next = queue.poll();
            }
            if (next != null) {
                results.add(new ResultVehicle(next, time));
            }
        }
    }

    /**
     * Returns the correct queue based off of
     * the given direction.
     * @param direction This must be one of:
     *
     *                  n - Northern Queue
     *                  w - Southern Queue
     *                  s - Western Queue
     *                  e - Eastern Queue
     *
     * @return The corresponding queue.
     */

    public Queue<Vehicle> queueByDirection(final char direction) {
        // Queue based on direction
        switch (direction) {
            case 'n':
                return northBoundQueue; // northboundqueue based on the north direction
            case 'w':
                return westBoundQueue; // westboundqueue based on the north direction
            case 's':
                return southBoundQueue; // southboundqueue based on the north direction
            case 'e':
                return eastBoundQueue; // eastboundqueue based on the north direction
        }
        return null;
    }

    /**
     * This method is used to check if a truck
     * is being processed.
     * @param direction This must be one of:
     *
     *                  n - Northern Queue
     *                  w - Southern Queue
     *                  s - Western Queue
     *                  e - Eastern Queue
     *
     * @return Whether or not a truck
     *         is being processed in
     *         this queue.
     */

    public boolean truckSeen(final char direction) {
        // Direction of truck processing
        switch (direction) {
            case 'n':
                return truckNorth; // north Truck
            case 'w':
                return truckWest; // west truck
            case 's':
                return truckSouth; // south truck
            case 'e':
                return truckEast; // east truck
        }
        return false;
    }

    /**
     * Set whether or not a truck is to be
     * processed in the given queue.
     * @param direction This must be one of:
     *
     *                  n - Northern Queue
     *                  w - Southern Queue
     *                  s - Western Queue
     *                  e - Eastern Queue
     *
     * @param truckSeen Whether or not a truck
     *                  has been seen here.
     */

    public void setTruck(final char direction, final boolean truckSeen) {
        // Sets the truck seen in that particular queue to be processed.
        switch (direction) {
            case 'n':
                truckNorth = truckSeen; // Truck is seen in the north
                return;
            case 'w':
                truckWest = truckSeen; // Truck is seen in the west
                return;
            case 's':
                truckSouth = truckSeen; // Truck is seen in the south
                return;
            case 'e':
                truckEast = truckSeen; // Truck is seen in the east
        }
    }

    /**
     * This will print a "board" layout of the
     * intersection.
     * @param time The current clock value.
     */

    public void printBoard(final int time) {
        System.out.printf("   SB %s %2s   \n", (southBoundQueue.size() >= 5 ? 'x' : ' '), southBoundQueue.size()); // size of the southbound queue
        System.out.printf("      %s       \n", (southBoundQueue.size() >= 5 ? 'x' : ' '));
        System.out.printf("      %s       \n", (southBoundQueue.size() >= 4 ? 'x' : ' '));
        System.out.printf("      %s       \n", (southBoundQueue.size() >= 3 ? 'x' : ' '));
        System.out.printf("EB    %s       \n", (southBoundQueue.size() >= 2 ? 'x' : ' '));
        System.out.printf("%2s    %s      \n", eastBoundQueue.size(), southBoundQueue.isEmpty() ? ' ' : southBoundQueue.peek().getType());
        System.out.printf("%5s%s %s%s     \n", CharBuffer.allocate(Math.min(Math.max(eastBoundQueue.size() - 1, 0), 5)).toString().replace('\u0000', 'x'),
                eastBoundQueue.isEmpty() ? ' ' : eastBoundQueue.peek().getType(), westBoundQueue.isEmpty() ? ' ' : westBoundQueue.peek().getType(),
                CharBuffer.allocate(Math.min(Math.max(westBoundQueue.size() - 1, 0), 5)).toString().replace('\u0000', 'x')
        );// Formats the arguments into the given String format.
        System.out.printf("      %s       \n", (northBoundQueue.isEmpty() ? ' ' : northBoundQueue.peek().getType())); // gets the type
        System.out.printf("      %s     WB\n", (northBoundQueue.size() >= 2 ? 'x' : ' '));
        System.out.printf("      %s    %2s\n", (northBoundQueue.size() >= 3 ? 'x' : ' '), westBoundQueue.size()); // size of the westbound queue
        System.out.printf("      %s       \n", (northBoundQueue.size() >= 4 ? 'x' : ' '));
        System.out.printf("      %s       \n", (northBoundQueue.size() >= 5 ? 'x' : ' '));
        System.out.printf("   NB %s %2s   \n", (northBoundQueue.size() >= 6 ? 'x' : ' '), northBoundQueue.size()); // size of the northbound queue
        System.out.printf("at clock: %3s  \n", time);
        System.out.println(DIVIDEND);
    }
}

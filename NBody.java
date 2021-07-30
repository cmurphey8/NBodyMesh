/*******************************************************************************
 * 
 *  GOAL:
 *  Dependencies: StdDraw.java, StdAudio.java, Body.java
 * 
 *  N-body simulation.
 *    *  Reads in number of bodies N, radius of universe R, initial positions,
 *       velocities, masses, and name of image files from text file;
 *    *  Simulate the system from time t = 0 until t >= T and plots the
 *       the results to standard drawing;
 *
 ******************************************************************************/

import java.util.Scanner;
import java.io.File; 
import java.io.FileNotFoundException;

public class NBody {

    // animation pause (in miliseconds)
    public static final int DELAY = 20;

    // music (2001 theme)
    public static final String MUSIC = "2001theme.wav";

    // background image
    public static final String BACKGROUND = "starfield.jpg";

    // parameters input file
    public static String PLANETS_FILE;

    // size of mesh grid
    public static final int MESH_SIZE = 3;
    public static final int MESH_SQUARE = MESH_SIZE * MESH_SIZE;

    // numerical constants
    public static final double G = 6.67e-11;    // gravitational constant (N m^2 / kg^2)
    public static final double T= 157788000.0;  // simulate from time 0 to T (s);             
    public static final double dt = 25000.0;    // time quantum (s);
    public static final double SCALE = 0.042;   // scalar for star size

    // parameters from first two lines 
    public static int N;                // number of bodies
    public static double R;             // radius of universe

    // global arrays of Body properties
    public static Body[] objects;

    // read the planet file, new the parallel arrays, 
    // and load their values from the file.
    public static void loadPlanets() {
        
        // open a parameters File to read from
        Scanner scan = null;
        try { File f = new File(getFile()); scan = new Scanner( f ); } 
        catch(FileNotFoundException e) { System.out.println("File not found exception"); } 

        // read from standard input
        N = scan.nextInt();         // number of bodies
        R = scan.nextDouble();      // radius of universe (m)

        // declare parallel arrays
        objects = new Body[N];

        // read in initial position, velocity, mass, and image name from .txt file
        for (int i = 0; i < N; i++) {
            objects[i] = new Body(scan, R);
        }
    }

    public static String getFile() {
        String inputFile = "chaos.txt";
        System.out.print("parameters input file: " + inputFile);
        return inputFile;
    }

    public static void runSimulation() {

        // run numerical simulation from 0 to T
        for (double t = 0.0; t < T; t += dt) {

            // draw background
            StdDraw.picture(0.0, 0.0, BACKGROUND);

            // resize mesh for this round
            Mesh[] mesh = new Mesh[MESH_SQUARE];
            
            // reinit mesh for this round
            for (int i = 0; i < MESH_SQUARE; i++) {
                mesh[i] = new Mesh(N);
            }

            // assign each object to a mesh location
            for (int i = 0; i < N; i++) {
                int index = findIndex(i);
                mesh[index].addObj(i);
            }

            // calculate Center of Mass of each mesh
            for (int i = 0; i < MESH_SQUARE; i++) {
                mesh[i].updateCOM(objects);
            }

            // calculate forces on each object
            for (int i = 0; i < N; i++) {
                // init the fx and fy components to 0 for next sum
                objects[i].zeroF();

                // find which mesh grid object i resides in
                int grid_index = findIndex(i);

                // update force components acting on object i from 
                // each GRID that object i does NOT reside in
                for (int j = 0; j < MESH_SQUARE; j++) {
                    if (j != grid_index) {
                        mesh[j].updateF(objects[i], G);          
                    }
                }
                // update force components acting on object i from  
                // each OBJECT in the SAME mesh grid as object i
                for (int j = 0; j < mesh[grid_index].mesh_index; j++) {

                    // find the index of the next object in this mesh grid
                    int obj_index = mesh[grid_index].grid[j];

                    // update additive fx && fy components
                    if (i != obj_index) { objects[i].updateF(objects[obj_index], G); }    
                }
                // update velocities and positions
                objects[i].step(dt);

                // draw this object
                objects[i].draw();
            }
            // display the Canvas for this round
            StdDraw.show();
            StdDraw.pause(DELAY);
        }
    }

    public static int findIndex(int i) {
        // shift range from [-R, R] to [0, 2R]
        double tmpX = objects[i].getRx() + R;
        double tmpY = objects[i].getRy() + R;

        // make MESH_SIZE number of divides across grid space
        double breaks = 2 * R / MESH_SIZE;

        // integer roundoff to find appropriate grid row, col && catch bounds
        int col = Math.max(Math.min((int) (tmpX / breaks), 0), MESH_SIZE - 1);
        int row = Math.max(Math.min((int) (tmpY / breaks), 0), MESH_SIZE - 1);

        // return linearized grid index
        return MESH_SIZE * row + col;
    }
    
    public static void main(String[] args) {
        // read in objects from .txt file
        loadPlanets();

        // rescale coordinates so that we can use natural x- && y-coordinates
        StdDraw.setXscale(-R, +R);
        StdDraw.setYscale(-R, +R);

        StdAudio.play( MUSIC );

        StdDraw.enableDoubleBuffering();

        // Run simulation
        runSimulation();
    }
}

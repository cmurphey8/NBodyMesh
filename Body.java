/**********************************************************************************
 *
 *  GOAL: complete all methods below!
 *              
 **********************************************************************************/
import java.util.Scanner;

public class Body {
    public final double SCALE = 0.042;  // scalar for star size

    private double rx;      // x position
    private double ry;      // y position
    private double vx;      // x velocity
    private double vy;      // y velocity

    private double mass;    // mass
    private String image;   // png image
    private double size;    // size
    
    private double fx;      // x force
    private double fy;      // y force

/**********************************************************************************
 *  Constructors        
 **********************************************************************************/

    // TODO: construct a new object with var inits from input Scanner
    public Body(Scanner scan, double R) {
        rx = 0;             // TODO
        ry = 0;             // TODO
        vx = 0;             // TODO
        vy = 0;             // TODO
        mass = 0;           // TODO
        image = "star.gif"; // TODO

        if (image.equals("blackhole.gif")) { size = SCALE * R; }
        else { size  = Math.random() * SCALE * R; }
    }

/**********************************************************************************
 *  Modifiers      
 **********************************************************************************/

    // set fx && fy to 0 - COMPLETE
    public void zeroF() {
        fx = 0;
        fy = 0;
    }

    // update fx && fy with additive gravitational force - COMPLETE
    // NOTE: this version of updateF is used by Mesh.java
    public void updateF(double dx, double dy, double other_mass, double G) {
        double rad = Math.sqrt(dx * dx + dy * dy);
        if (rad > 0) { 
            double Force = G * mass * other_mass / (rad * rad); 
            fx += Force * dx / rad;
            fy += Force * dy / rad;    
        }
    }      
    
    // TODO: update fx && fy with additive gravitational force from input Body obj
    // NOTE: this version of updateF is used by NBody.java
    public void updateF(Body obj, double G) {
        // calculate dx, dy, rad
        double dx = 0;  // TODO
        double dy = 0;  // TODO
        double rad = 0; // TODO

        // calculate Force and update additive contibution to fx, fy, components
        if (rad > 0) { 
            double Force = 0;   // TODO
            fx += 0;            // TODO
            fy += 0;            // TODO
        }
    }   

    // TODO: update this object's position and velocity
    public void step(double dt) {
        vx += 0;    // TODO
        vy += 0;    // TODO
        rx += 0;    // TODO
        ry += 0;    // TODO
    }

/**********************************************************************************
 *  Accessors - COMPLETE      
 **********************************************************************************/

     // return the mass of this object
     public double getMass() {
        return mass;
    }

    // return the rx of this object
    public double getRx() {
        return rx;
    }

    // return the ry of this object
    public double getRy() {
        return ry;
    }

    // draw this object using it's rx, ry, and image data
    public void draw() {
        StdDraw.picture(rx, ry, image, size, size);
    }

}

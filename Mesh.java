/**********************************************************************************
 *
 *  GOAL: complete all methods below!
 *              
 **********************************************************************************/

public class Mesh {
    public int[] grid;          // indexes of all objects in this cell
    public int mesh_index;      // next open index in grid for this cell                   
    public double mesh_mass;    // total mass of all objects in this cell
    public double comX;         // x-copmonent center of mass of this cell
    public double comY;         // y-component cente rof mass of this cell 

/**********************************************************************************
 *  Constructors        
 **********************************************************************************/

    // create and init a new object with input parameters scanned from a .txt file
    public Mesh(int N) {
        // initialize all elements
        grid = new int[N];
        mesh_index = 0;
        mesh_mass = 0;
        comX = 0;
        comY = 0;
    }

/**********************************************************************************
 *  Modifiers      
 **********************************************************************************/

    // update COM with all objects in this cell
    public void updateCOM(Body[] objects) {
        double numX = 0;
        double numY = 0;
        for (int j = 0; j < mesh_index; j++) {

            // find index of next body in ith mesh grid
            int body_index = grid[j];

            // update running COM component sums
            mesh_mass += objects[body_index].getMass();
            numX += objects[body_index].getMass() * objects[body_index].getRx();
            numY += objects[body_index].getMass() * objects[body_index].getRy();
        }

        // compute the x and y COM components
        if (mesh_mass > 0) {
            comX = numX / mesh_mass;
            comY = numY / mesh_mass;
        }
        else {
            // default COM to 0 if this mesh grid is empty 
            comX = 0;
            comY = 0;
        }
    }

    // add a Body object to this mesh cell
    public void addObj(int index) {
        grid[mesh_index] = index;
        mesh_index++;
    }    

/**********************************************************************************
 *  Accessors   
 **********************************************************************************/

    // update additive fx, fy for obj with COM of this cell 
    public void updateF(Body obj, double G) {
        double dx = comX - obj.getRx();
        double dy = comY - obj.getRy();
        obj.updateF(dx, dy, mesh_mass, G);
    }
}
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;


/**
 * The seaship class.
 * @author Ruslan Volchetskyy
 */
public class SeaShip {
    /**
     * The Node where all objects are contained in.
     */
    private final Node rootNode;
    /**
     * The node with ship itself.
     */
    private final Node shipNode = new Node("ship");
    /**
     * The asset manager where the ship model is loaded from.
     */
    private final AssetManager assetManager;
    /**
     * The seaship's speed.
     */
    private int speed;
    /**
     * The path of ship's moving.
     */
    private MotionPath path;
    /**
     * The motion control object for moving ship.
     */
    private MotionEvent motionControl;
    
    /**
     * Creates Seaship class with specified root node and asset manager.
     * @param rootNode A node which contains all objects on the map.
     * @param assetManager Asset manager for models loading etc.
     * @param speed Speed of seaship.
     */
    public SeaShip(Node rootNode, AssetManager assetManager, int speed)
    {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        this.speed = speed;
        createShip();
    }
    
    public SeaShip(int speed)
    {
        this.speed = speed;
        rootNode = new Node();
        this.assetManager = null;
    }
    
    /**
     * Creates the ship object and attachs it to the shipNode.
     */
    private void createShip()
    {
        Spatial s = assetManager.loadModel("Models/Boat/boat.j3o");
        s.scale(0.4f, 0.4f, 0.4f);
       
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);

        shipNode.attachChild(s);
        rootNode.attachChild(shipNode);
        shipNode.setLocalTranslation(-2f, 0.5f, 0);
    } 
    
    /**
     * Sets the motion for ship to the mouse cursor location.
     * @param mousePoint A point where mouse cursor is located.
     * @param box A floor which seaship can't leave.
     */
    public void setMotion(Vector3f mousePoint, Box box){
        Vector3f destination = new Vector3f(mousePoint.getX(), mousePoint.getY(), mousePoint.getZ());
        Vector3f location = getPosition();
        
        if(mousePoint.getX() < (-1)*box.getXExtent()){
            destination.setX((-1)*box.getXExtent());
        }
        else if (mousePoint.getX() > box.getXExtent()){
            destination.setX(box.getXExtent());
        }
        
        if(mousePoint.getZ() < (-1)*box.getZExtent()){
            destination.setZ((-1)*box.getZExtent());
        }
        else if (mousePoint.getZ() > box.getZExtent()){
            destination.setZ(box.getZExtent());
        } 
        
        path = new MotionPath();
        path.addWayPoint(location);
        path.addWayPoint(destination);
        
        float duration = location.distance(destination) / speed;
        
        motionControl = new MotionEvent(shipNode,path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setInitialDuration(duration);
        
        shipNode.lookAt(mousePoint, new Vector3f(0, 1, 0));
    }
     
    /**
     * Plays the motion path.
     */
    public void playMotion() {
        motionControl.play();
    }
    
    public void changeSpeed(int newSpeed){
        this.speed = newSpeed;
    }
    
    /**
     * Gets current position of seaship.
     * @return A Vector3f with current position.
     */
    public Vector3f getPosition() {
        return shipNode.getLocalTranslation();
    }
    
    public int getSpeed(){
        return speed;
    }
}



 



 

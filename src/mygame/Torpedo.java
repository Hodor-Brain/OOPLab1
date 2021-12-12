package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Cylinder;
import com.jme3.texture.Texture;

import java.util.Random;


/**
 * The torpedo class.
 * @author Ruslan Volchetskyy
 */
public class Torpedo {
    /**
     * The Node where all objects are contained in.
     */
    private final Node rootNode;
    /**
     * The node with torpedo itself.
     */
    private final Node torpedoNode = new Node("Torpedo");
    /**
     * The material object to set meterials.
     */
    private Material material;
    /**
     * Object to set standart form of torpedo.
     */
    private final Cylinder Cylinder;
    /**
     * The torpedo's speed.
     */
    private int speed;
    /**
     * The path of torpedo's moving.
     */
    private MotionPath path;
    /**
     * The motion control object for moving torpedo.
     */
    private MotionEvent motionControl;

    /**
     * Creates torpedo class with specified root Node and asset manager.
     * @param assetManager Asset manager for models loading etc.
     * @param rootNode A node which contains all objects on the map.
     */
    Torpedo(AssetManager assetManager, Node rootNode, int speed) {
        this.rootNode = rootNode;
        Cylinder = new Cylinder(2, 32, 0.15f, 0.25f, 2f, true, false);
        this.speed = speed;
        initMaterials(assetManager);
    }
    
    public Torpedo(int speed)
    {
        this.speed = speed;
        this.rootNode = new Node();
        Cylinder = null;
    }

    /**
     * Creates material for torpedo.
     * @param assetManager Asset manager for models loading.
     */
    private void initMaterials(AssetManager assetManager) {
        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key = new TextureKey("Textures/torpedo.jpg");
        key.setGenerateMips(true);
        Texture texture = assetManager.loadTexture(key);
        material.setTexture("ColorMap", texture);
    }
    
    /**
     * Creates torpedo object and adds it to the map.
     */
    protected void createTorpedo() {
        Random rand = new Random();
        
        Geometry ballGeometry = new Geometry();
        ballGeometry.setMesh(Cylinder);
        ballGeometry.setMaterial(material);
        Spatial s = ballGeometry;
        
        int x_part = 0, z_part = 0;
        
        if (rand.nextBoolean())
            x_part = 1;
        else
            x_part = -1;
        
        if (rand.nextBoolean())
            z_part = 1;
        else
            z_part = -1;
        
        //s.setLocalTranslation(new Vector3f(x_part * 15f, 0.5f, z_part * 15f));//x_part * 15f
        
        torpedoNode.attachChild(s);
        rootNode.attachChild(torpedoNode);
        torpedoNode.setLocalTranslation(new Vector3f(x_part * 15f, 0.5f, z_part * 15f));
    }

    /**
     * Sets a motion for torpedo to the seaships location.
     * @param shipPoint A position of seaship.
     */
    public void setMotion(Vector3f shipPoint){                       //int status
        Vector3f destination = new Vector3f(shipPoint.getX(), shipPoint.getY(), shipPoint.getZ());
        
        
        path = new MotionPath();
        
        Vector3f location = torpedoNode.getLocalTranslation();
        
        path.addWayPoint(location);
        path.addWayPoint(destination);
        
        float duration = location.distance(destination) / speed;
        
        motionControl = new MotionEvent(torpedoNode, path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setInitialDuration(duration);
        
        torpedoNode.lookAt(shipPoint, new Vector3f(0, 1, 0));
    }
     
     /**
     * Plays the motion path.
     */
    public void playMotion() {
        motionControl.play();
    }
    
    public int getSpeed(){
        return speed; 
    }
}



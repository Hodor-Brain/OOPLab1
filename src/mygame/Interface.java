package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;

/**
 * The main application.
 * @author Ruslan Volchetskyy
 */
public class Interface extends SimpleApplication {
    private BulletAppState bulletAppState;
    private SeaShip shipCreator;
    private Torpedo torpedoCreator;
    private boolean torpedoCreated = false;
    private Floor floor;
    private final static String FONT = "Interface/Fonts/Default.fnt";

    /**
     * Calculates intersect point of ray and plane.
     * @param rayVector Vector of ray.
     * @param rayPoint Point of ray's start.
     * @param planeNormal Vector-norm to plane.
     * @param planePoint Any point on plane.
     * @return Vector3f with point of intersection.
     */
    private static Vector3f intersectPoint(Vector3f rayVector, Vector3f rayPoint, Vector3f planeNormal, Vector3f planePoint) {
        Vector3f diff = rayPoint.add(planePoint.mult(-1f));
        float prod1 = diff.dot(planeNormal);
        float prod2 = rayVector.dot(planeNormal);
        float prod3 = prod1 / prod2;
        return rayPoint.add(rayVector.mult(prod3*(-1)));
    }
    
    /**
     * Calculates the point where seaship have to go to.
     * @return Vector3f with x,y,z that represents point of destination.
     */
    public Vector3f calcPosition() {
        Vector3f direction = cam.getDirection();
        Vector3f position = cam.getLocation();
        
        Vector3f norm = new Vector3f(0, 1, 0);

        return intersectPoint(direction, position, norm, new Vector3f(0, 0.5f, 0));
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        cam.setLocation(new Vector3f(0, 30f, 20f));
        cam.lookAt(new Vector3f(0, 0.5f, 0), Vector3f.UNIT_Y);

        floor = new Floor(assetManager, rootNode, bulletAppState);
        shipCreator = new SeaShip(rootNode, assetManager, 10);
        torpedoCreator = new Torpedo(assetManager, rootNode, 8);
        
        flyCam.setEnabled(true);
        flyCam.setMoveSpeed(30);
        flyCam.setRotationSpeed(2);

        inputManager.addMapping("create", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, "create");

        initCrossHairs();
    }

    /**
     * Creates action listener.
     */
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("create") && !keyPressed && !torpedoCreated) {
                torpedoCreator.createTorpedo();
                torpedoCreated = true;
            }
        }
    };
    
    @Override
    public void simpleUpdate(float tpf) {
        Vector3f point = calcPosition();
        
        if (torpedoCreated){
            torpedoCreator.setMotion(shipCreator.getPosition());
            torpedoCreator.playMotion();
        }
        
        shipCreator.setMotion(point, floor.getFLOOR());
        shipCreator.playMotion();
    }

    /**
     * Creates the cross in center of screen.
     */
    private void initCrossHairs() {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont(FONT);
        BitmapText ch = new BitmapText(guiFont);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");
        ch.setLocalTranslation((settings.getWidth() - ch.getLineWidth()) / 2, (settings.getHeight() + ch.getLineHeight()) / 2, 0);
        guiNode.attachChild(ch);
    }

}

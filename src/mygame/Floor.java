package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 * The floor class.
 * @author Ruslan Volchetskyy
 */
public final class Floor {
    /**
     * Floor's material.
     */
    private Material material;
    /**
     * The floor itself.
     */
    private static Box FLOOR;

    /**
     * Gets Box object of floor.
     * @return Box object representing floor.
     */
    public Box getFLOOR() {
        return FLOOR;
    }

    /**
     * Creates Floor class with specified parameters.
     * @param assetManager
     * @param rootNode
     * @param bulletAppState
     */
    Floor(AssetManager assetManager, Node rootNode, BulletAppState bulletAppState) {
        FLOOR = new Box(50f, 0.1f, 50f);
        FLOOR.scaleTextureCoordinates(new Vector2f(3, 6));
        initMaterials(assetManager);
        initPhysics(rootNode, bulletAppState);
    }

    /**
     * Initialising materials.
     * @param assetManager 
     */
    private void initMaterials(AssetManager assetManager) {
        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key = new TextureKey("Textures/water.jpg");
        key.setGenerateMips(true);
        Texture texture = assetManager.loadTexture(key);
        texture.setWrap(Texture.WrapMode.Repeat);
        material.setTexture("ColorMap", texture);
    }

    /**
     * Initialising physics.
     * @param rootNode
     * @param bulletAppState 
     */
    private void initPhysics(Node rootNode, BulletAppState bulletAppState) {
        Geometry floorGeometry = new Geometry("Floor", FLOOR);
        floorGeometry.setMaterial(material);
        floorGeometry.setLocalTranslation(0, 0, 0);
        rootNode.attachChild(floorGeometry);

        RigidBodyControl floorRigidBodyControl = new RigidBodyControl(0.0f);
        floorGeometry.addControl(floorRigidBodyControl);
        bulletAppState.getPhysicsSpace().add(floorRigidBodyControl);
    }

}

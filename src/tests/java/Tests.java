/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.java;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import mygame.SeaShip;
import mygame.Torpedo;

/**
 *
 * @author rucla
 */
public class Tests {
    @Test
    public void TestForShip(){
        SeaShip s = new SeaShip(10);
        assertEquals(s.getSpeed(), 10);
        
        s.changeSpeed(20);
        assertEquals(s.getSpeed(), 20);
    }
    
    @Test
    public void TestForTorpedo(){
        Torpedo t = new Torpedo(5);
        assertEquals(t.getSpeed(), 5);
    }
}

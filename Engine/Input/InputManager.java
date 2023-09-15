//
//	InputManager.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Engine.Window.PresentBuffer;
import java.util.ArrayList;
import java.util.List;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener {
    public static List<Key> keys = new ArrayList<Key>();
    private static int mouseX;
    private static int mouseY;
    private static int mouseB = -1;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Consntructs the Input Manager, setting it as the key listener
    */ //----------------------------------------------------------------------
    public InputManager(PresentBuffer buf) {
        buf.addKeyListener(this);
        buf.addMouseListener(this);
    }

    // ------------------------------------------------------------------------
    /*! Get Mouse X
    *
    *   Gets the mouse x screen coordinate
    */ //----------------------------------------------------------------------
    public int GetMouseX() {
        return mouseX;
    }

    // ------------------------------------------------------------------------
    /*! Get Mouse Y
    *
    *   Returns the mouse y screen coordinate
    */ //----------------------------------------------------------------------
    public int GetMouseY() {
        return mouseY;
    }

    // ------------------------------------------------------------------------
    /*! Get Button
    *
    *   Returns if the mouse is clicked
    */ //----------------------------------------------------------------------
    public int GetButton() {
        return mouseB;
    }

    public class Key {
        public int presses, absorbs;
        public boolean down, clicked;

        // ------------------------------------------------------------------------
        /*! Constructor
        *
        *   Adds itself to the Input Manager
        */ //----------------------------------------------------------------------        
        public Key() {
            keys.add(this);
        }

        // ------------------------------------------------------------------------
        /*! Toogle
        *
        *   Registers the number of times it's pressed and the times
        */ //----------------------------------------------------------------------
        public void toogle(boolean pressed) {
            //If it's not down, account for every pressed eventuality
            if(pressed != down)
                down = pressed;

            //if pressed, update
            if(pressed)
                presses++;
        }

        // ------------------------------------------------------------------------
        /*! Constructor
        *
        *   Adds some basic states
        */ //----------------------------------------------------------------------
        public void tick() {
            //clicking?
            if(absorbs < presses) {
                absorbs++;
                clicked = true;
            } else {
                clicked = false;
            }
        }
    }

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key escape = new Key();

    // ------------------------------------------------------------------------
    /*! Release All
    *
    *   Releases all the keys available
    */ //----------------------------------------------------------------------
    public void releaseAll() {
        //for every key available
        for(int i = 0; i < keys.size(); i++)
            keys.get(i).down = false;
    }

    // ------------------------------------------------------------------------
    /*! Tick
    *
    *   Updates every registered key
    */ //----------------------------------------------------------------------
    public void Tick() {
        //tick every key
        for(int i = 0; i < keys.size(); i++)
            keys.get(i).tick();
    }

    // ------------------------------------------------------------------------
    /*! toogle
    *
    *   sets the correct key to pressed or not pressed
    */ //----------------------------------------------------------------------
    public void toogle(KeyEvent e, boolean pressed) {
        if(e.getKeyCode() == KeyEvent.VK_UP) up.toogle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_LEFT) left.toogle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) right.toogle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_DOWN) down.toogle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) escape.toogle(pressed);
    }

    // ------------------------------------------------------------------------
    /*! Key Typed
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void keyTyped(KeyEvent e) {}

    // ------------------------------------------------------------------------
    /*! Key Pressed
    *
    *   toogles wether key was pressed
    */ //----------------------------------------------------------------------
    @Override
    public void keyPressed(KeyEvent e) {
        toogle(e, true);
    }

    // ------------------------------------------------------------------------
    /*! Key released
    *
    *   releases wether key was released
    */ //----------------------------------------------------------------------
    @Override
    public void keyReleased(KeyEvent e) {
        toogle(e, false);
    }

    // ------------------------------------------------------------------------
    /*! Mouse Clicked
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void mouseClicked(MouseEvent e) {}

    // ------------------------------------------------------------------------
    /*! Mouse Pressed
    *
    *   Refreshes the mouse button state
    */ //----------------------------------------------------------------------
    @Override
    public void mousePressed(MouseEvent e) {
        mouseB = e.getButton();
    }

    // ------------------------------------------------------------------------
    /*! Mouse Released
    *
    *   Mouse not pressed ATM
    */ //----------------------------------------------------------------------
    @Override
    public void mouseReleased(MouseEvent e) {
        mouseB = -1;
    }

    // ------------------------------------------------------------------------
    /*! Mouse Clicked
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void mouseEntered(MouseEvent e) {}

    // ------------------------------------------------------------------------
    /*! Mouse Clicked
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void mouseExited(MouseEvent e) {}

    // ------------------------------------------------------------------------
    /*! Mouse Clicked
    *
    *   Sets the right location for the mouse X and Y Positions
    */ //----------------------------------------------------------------------
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    // ------------------------------------------------------------------------
    /*! Mouse Moved
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void mouseMoved(MouseEvent e) {}
}

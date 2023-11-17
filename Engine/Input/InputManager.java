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
import Engine.Math.Vector2D;
import Engine.Window.PresentBuffer;
import java.util.ArrayList;
import java.util.HashMap;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener {
    public static HashMap<Integer, ArrayList<InputFunction>> mKeyPressedFuncs = new HashMap<>();
    public static HashMap<Integer, ArrayList<InputFunction>> mKeyReleasedFuncs = new HashMap<>();
    private static Vector2D<Integer> mMouseCoords;
    private static int mMouseButton = -1;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Consntructs the Input Manager, setting it as the key listener
    */ //----------------------------------------------------------------------
    public InputManager(PresentBuffer buf) {
        buf.addKeyListener(this);
        buf.addMouseListener(this);
    }

    public static void Clear() {
        mKeyPressedFuncs.clear();
        mKeyReleasedFuncs.clear();
    }

    // ------------------------------------------------------------------------
    /*! SubscribePressed
    *
    *   Subscribes to the Pressed Input Function
    */ //----------------------------------------------------------------------
    public static void SubscribePressed(int vk, InputFunction f) {
        //If we have a group for this key event
        if(!mKeyPressedFuncs.containsKey(vk))
            mKeyPressedFuncs.put(vk, new ArrayList<>());
        mKeyPressedFuncs.get(vk).add(f);
    }

    // ------------------------------------------------------------------------
    /*! Subscribes Released
    *
    *   Subscribes to the realeased Input functions
    */ //----------------------------------------------------------------------
    public static void SubscribeReleased(int vk, InputFunction f) {
        //If we have a group for this key event
        if(!mKeyReleasedFuncs.containsKey(vk))
            mKeyReleasedFuncs.put(vk, new ArrayList<>());
        mKeyReleasedFuncs.get(vk).add(f);
    }

    // ------------------------------------------------------------------------
    /*! Get Mouse X
    *
    *   Gets the mouse x screen coordinate
    */ //----------------------------------------------------------------------
    public int GetMouseX() {
        return mMouseCoords.x;
    }

    // ------------------------------------------------------------------------
    /*! Get Mouse Y
    *
    *   Returns the mouse y screen coordinate
    */ //----------------------------------------------------------------------
    public int GetMouseY() {
        return mMouseCoords.y;
    }

    // ------------------------------------------------------------------------
    /*! Get Button
    *
    *   Returns if the mouse is clicked
    */ //----------------------------------------------------------------------
    public int GetButton() {
        return mMouseButton;
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
        //If we have a group for this key event
        if(mKeyPressedFuncs.containsKey(e.getKeyCode()))
            //Execute every function
            for(int i = 0; i < mKeyPressedFuncs.get(e.getKeyCode()).size(); i++)
                mKeyPressedFuncs.get(e.getKeyCode()).get(i).Execute();
        else
            mKeyPressedFuncs.put(e.getKeyCode(), new ArrayList<>());
    }

    // ------------------------------------------------------------------------
    /*! Key released
    *
    *   releases wether key was released
    */ //----------------------------------------------------------------------
    @Override
    public void keyReleased(KeyEvent e) {
        //If we have a group for this key event
        if(mKeyReleasedFuncs.containsKey(e.getKeyCode()))
            //Execute every function
            for(int i = 0; i < mKeyReleasedFuncs.get(e.getKeyCode()).size(); i++)
                mKeyReleasedFuncs.get(e.getKeyCode()).get(i).Execute();
        else
            mKeyReleasedFuncs.put(e.getKeyCode(), new ArrayList<>());
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
        mMouseButton = e.getButton();
    }

    // ------------------------------------------------------------------------
    /*! Mouse Released
    *
    *   Mouse not pressed ATM
    */ //----------------------------------------------------------------------
    @Override
    public void mouseReleased(MouseEvent e) {
        mMouseButton = -1;
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
        mMouseCoords.x = e.getX();
        mMouseCoords.y = e.getY();
    }

    // ------------------------------------------------------------------------
    /*! Mouse Moved
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void mouseMoved(MouseEvent e) {}
}

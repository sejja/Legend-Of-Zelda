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

final public class InputManager implements KeyListener, MouseListener, MouseMotionListener {
    private HashMap<Integer, ArrayList<InputFunction>> mKeyPressedFuncs;
    private HashMap<Integer, ArrayList<InputFunction>> mKeyReleasedFuncs;
    private Vector2D<Integer> mMouseCoords;
    private int mMouseButton = -1;
    private static InputManager sInstance = new InputManager();

    // ------------------------------------------------------------------------
    /*! Instance
    *
    *   Returns the Instance of the Input Manager
    */ //----------------------------------------------------------------------
    public static InputManager Instance() {
        return sInstance;
    }

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Consntructs the Input Manager
    */ //----------------------------------------------------------------------
    private InputManager() {
        mKeyPressedFuncs = new HashMap<>();
        mKeyReleasedFuncs = new HashMap<>();
    }

    // ------------------------------------------------------------------------
    /*! Set Speaking Buffer
    *
    *   Sets the buffer of which we are capturing input from
    */ //----------------------------------------------------------------------
    public void SetSpeakingBuffer(final PresentBuffer buf) {
        buf.addKeyListener(this);
        buf.addMouseListener(this);
    }

    // ------------------------------------------------------------------------
    /*! Clear
    *
    *   Clears every key pressed
    */ //----------------------------------------------------------------------
    public void Clear() {
        mKeyPressedFuncs.clear();
        mKeyReleasedFuncs.clear();
    }

    // ------------------------------------------------------------------------
    /*! SubscribePressed
    *
    *   Subscribes to the Pressed Input Function
    */ //----------------------------------------------------------------------
    public void SubscribePressed(final int vk, final InputFunction f) {
        mKeyPressedFuncs.computeIfAbsent(vk, k -> new ArrayList<>()).add(f);
    }

    // ------------------------------------------------------------------------
    /*! Subscribes Released
    *
    *   Subscribes to the realeased Input functions
    */ //----------------------------------------------------------------------
    public void SubscribeReleased(final int vk, final InputFunction f) {
        mKeyReleasedFuncs.computeIfAbsent(vk, k -> new ArrayList<>()).add(f);
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
    public void keyTyped(final KeyEvent e) {}

    // ------------------------------------------------------------------------
    /*! Key Pressed
    *
    *   toogles wether key was pressed
    */ //----------------------------------------------------------------------
    @Override
    public void keyPressed(final KeyEvent e) {
        mKeyPressedFuncs.getOrDefault(e.getKeyCode(), new ArrayList<InputFunction>()).forEach(x -> x.Execute());
    }

    // ------------------------------------------------------------------------
    /*! Key released
    *
    *   releases wether key was released
    */ //----------------------------------------------------------------------
    @Override
    public void keyReleased(final KeyEvent e) {
        mKeyReleasedFuncs.getOrDefault(e.getKeyCode(), new ArrayList<InputFunction>()).forEach(x -> x.Execute());
    }

    // ------------------------------------------------------------------------
    /*! Mouse Clicked
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void mouseClicked(final MouseEvent e) {}

    // ------------------------------------------------------------------------
    /*! Mouse Pressed
    *
    *   Refreshes the mouse button state
    */ //----------------------------------------------------------------------
    @Override
    public void mousePressed(final MouseEvent e) {
        mMouseButton = e.getButton();
    }

    // ------------------------------------------------------------------------
    /*! Mouse Released
    *
    *   Mouse not pressed ATM
    */ //----------------------------------------------------------------------
    @Override
    public void mouseReleased(final MouseEvent e) {
        mMouseButton = -1;
    }

    // ------------------------------------------------------------------------
    /*! Mouse Clicked
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void mouseEntered(final MouseEvent e) {}

    // ------------------------------------------------------------------------
    /*! Mouse Clicked
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void mouseExited(final MouseEvent e) {}

    // ------------------------------------------------------------------------
    /*! Mouse Clicked
    *
    *   Sets the right location for the mouse X and Y Positions
    */ //----------------------------------------------------------------------
    @Override
    public void mouseDragged(final MouseEvent e) {
        mMouseCoords.Set(e.getX(), e.getY());
    }

    // ------------------------------------------------------------------------
    /*! Mouse Moved
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void mouseMoved(final MouseEvent e) {}
}

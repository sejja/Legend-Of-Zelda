//
//	PresentBuffer.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 14/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import Engine.Graphics.GraphicsPipeline;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.StateMachine.StateMachine;

public class PresentBuffer extends JPanel implements Runnable {
    public static int mWidth;
    public static int mHeight;
    private Thread mLogicThread;
    private BufferedImage mFrameBuffer;
    private Graphics2D mGFX;
    private boolean mRunning;
    private InputManager mInputManager;
    private StateMachine mStateManager;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   As a JPanel, which behaves as a FrameBuffer, request the focus and sets the size
    */ //----------------------------------------------------------------------
    public PresentBuffer(int width, int height) {
        mWidth = width;
        mHeight = height;
        mStateManager = new StateMachine();
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
    }

    // ------------------------------------------------------------------------
    /*! Add Notify
    *
    *   Starts the Game Thread, in case there is none. This happens on the start
    */ //----------------------------------------------------------------------
    public void addNotify() {
        super.addNotify();

        //If we had no runing thread
        if(mLogicThread == null) {
            mLogicThread = new Thread(this, "LogicThread");   
            mLogicThread.start();
        }
    }

    // ------------------------------------------------------------------------
    /*! Init
    *
    *   Creates the FrameBuffer (a BufferedImage)
    */ //----------------------------------------------------------------------
    public void Init() {
        mRunning = true;
        mFrameBuffer = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_ARGB);
        mGFX = (Graphics2D)mFrameBuffer.getGraphics();
        mInputManager = new InputManager(this);
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Updates the game
    */ //----------------------------------------------------------------------
    public void Update() {
        mStateManager.Update();
        Vector2D<Integer> temp = new Vector2D<>();
        temp.x = getBounds().width;
        temp.y = getBounds().height;
        GraphicsPipeline.GetGraphicsPipeline().SetDimensions(temp);
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders the game
    */ //----------------------------------------------------------------------
    public void Render() {
        //If there is a valid graphics
        if(mGFX != null) {
            mGFX.setColor(Color.black);
            mGFX.fillRect(0, 0, mWidth, mHeight);
            mStateManager.Render(mGFX);
        }
    }

    // ------------------------------------------------------------------------
    /*! Present
    *
    *   Presents the game
    */ //----------------------------------------------------------------------
    public void Present() {
        Graphics d2g = (Graphics) getGraphics();
        d2g.drawImage(mFrameBuffer, 0, 0, mWidth, mHeight, null);
        d2g.dispose();
    }

    // ------------------------------------------------------------------------
    /*! Input
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    public void Input(Engine.Input.InputManager inputmgr) {
    }

    // ------------------------------------------------------------------------
    /*! Run
    *
    *   The game loop.
    */ //----------------------------------------------------------------------
    public void run() {
        Init();

        final double FPS = 60.f;
        final double TBU = 1000000000 / FPS;

        double lastRenderTime;

        //While the window is present
        while(mRunning) {
            long framestart_time = System.nanoTime();
            Input(mInputManager);
            Update();
            Render();
            Present();
            lastRenderTime = System.nanoTime();

            //If we have time to sleep until the next frame, sleep
            while(framestart_time - lastRenderTime < TBU) {
                Thread.yield();

                //Sleeping the tread is not safe, add a try/catch
                try {
                    Thread.sleep(10);
                } catch(Exception e) {
                    e.printStackTrace();
                }

                framestart_time = System.nanoTime();
            }
        }
    }
}
    
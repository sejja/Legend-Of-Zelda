package Engine.Window;

import Engine.ECSystem.Level;
import Engine.Input.InputManager;
import Engine.StateMachine.StateMachine;

public class GameLoop extends Thread {
    private boolean mRunning;
    private StateMachine mStateManager;
    private PresentBuffer mTargetBuffer;
    static private boolean mPause;

    public GameLoop(PresentBuffer target) {
        mTargetBuffer = target;
        mPause = false;
    }

    // ------------------------------------------------------------------------
    /*! Init
    *
    *   Creates the FrameBuffer (a BufferedImage)
    */ //----------------------------------------------------------------------
    public void Init() {
        mRunning = true;
        mStateManager = new StateMachine();
        new InputManager(mTargetBuffer);
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Updates the game
    */ //----------------------------------------------------------------------
    public void Update() {
        mStateManager.Update();
    }

    // ------------------------------------------------------------------------
    /*! Is Paused
    *
    *   Return wether we are paused or not
    */ //----------------------------------------------------------------------
    static public boolean IsPaused() {
        return mPause;
    }

    // ------------------------------------------------------------------------
    /*! Set Paused
    *
    *   Returns wether we pause the game or not
    */ //----------------------------------------------------------------------
    static public void SetPaused(boolean b) {
        mPause = b;
    }

    // ------------------------------------------------------------------------
    /*! Run
    *
    *   The game loop.
    */ //----------------------------------------------------------------------
    @Override
    public void run() {
        Init();

        final double FPS = 60.f;
        final double TBU = 1000000000 / FPS;

        //While the window is present
        for(long frametime = System.nanoTime(); mRunning; frametime = System.nanoTime()) {
            Level.mCurrentLevel.Update();
            if(!mPause) Update();
            mTargetBuffer.Render();
            mTargetBuffer.Present();
            final long lastRenderTime = System.nanoTime();
            
            //If we have time to sleep until the next frame, sleep
            for(Thread.yield(); frametime - lastRenderTime < TBU; frametime = System.nanoTime()) {
                //Sleeping the tread is not safe, add a try/catch
                try {
                    Thread.sleep(((long)TBU - (frametime - lastRenderTime)) / 1000000000);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

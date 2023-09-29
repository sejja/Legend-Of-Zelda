package Engine.Window;

import Engine.Input.InputManager;
import Engine.StateMachine.StateMachine;

public class GameLoop extends Thread {
    private boolean mRunning;
    private StateMachine mStateManager;
    private PresentBuffer mTargetBuffer;

    public GameLoop(PresentBuffer target) {
        mTargetBuffer = target;
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
            Update();
            mTargetBuffer.Render();
            mTargetBuffer.Present();
            final long lastRenderTime = System.nanoTime();

            //If we have time to sleep until the next frame, sleep
            for(Thread.yield(); frametime - lastRenderTime < TBU; frametime = System.nanoTime()) {
                //Sleeping the tread is not safe, add a try/catch
                try {
                    Thread.sleep(((long)TBU - (frametime - lastRenderTime)) / 1000000);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

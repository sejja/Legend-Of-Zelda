package Engine.Window;

import Engine.ECSystem.Level;
import Engine.Graphics.GraphicsPipeline;
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

        final double GAME_HERTZ = 60.0;
        final double TBU = 1000000000 / GAME_HERTZ;

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 60;
        final double TTBR = 1000000000 / TARGET_FPS;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);

        System.out.println(TBU);

        System.out.println(lastSecondTime);

        while (mRunning) {

            double now = System.nanoTime();

            Level.mCurrentLevel.Update();
            if(!mPause) Update();
            lastUpdateTime += TBU;

            if ((now - lastUpdateTime) > TBU) {
                lastUpdateTime = now - TBU;
            }

            mTargetBuffer.Render();
            mTargetBuffer.Present();
            lastRenderTime = now;

            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if (thisSecond > lastSecondTime) {
                lastSecondTime = thisSecond;
            }

            Thread.yield();

            try {
                Thread.sleep((int)(16 - ((System.nanoTime() - now) / 1000000)));
            } catch (Exception e) {
                System.out.println("ERROR: yielding thread");
            }

            System.out.println("FPS: " + 1000000000.0 / (System.nanoTime() - now));
            now = System.nanoTime();

        }
    }
}

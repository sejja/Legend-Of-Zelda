//
//	Window.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 14/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Window;

import javax.swing.JFrame;

public class Window extends JFrame {
    private GameLoop mGameLoop;
    
    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Creates a Window, with a Presenter Buffer on it
    */ //----------------------------------------------------------------------
    public Window() {
        final PresentBuffer buffer = new PresentBuffer(1280, 720);
        mGameLoop = new GameLoop(buffer);
        
        setTitle("The Legend Of Zelda");
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIgnoreRepaint(true);
        setContentPane(buffer);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setAutoRequestFocus(true);
        mGameLoop.start();
    }
}

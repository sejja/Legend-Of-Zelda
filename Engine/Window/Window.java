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
    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Creates a Window, with a Presenter Buffer on it
    */ //----------------------------------------------------------------------
    public Window() {
        setTitle("The Legend Of Zelda");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIgnoreRepaint(true);
        setContentPane(new PresentBuffer(1280, 720));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

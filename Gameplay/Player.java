package Gameplay;

import java.awt.Graphics2D;

import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import Engine.ECSystem.Entity;
import Engine.Graphics.Sprite;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;

public class Player extends Entity {
    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs a Player with a sprite, a position, and gives it a size
    */ //----------------------------------------------------------------------
    public Player(Sprite sprite, Vector2D position, int size) {
        super(sprite, position, size);
        //TODO Auto-generated constructor stub
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Adds Behavior to the Player
    */ //----------------------------------------------------------------------
    public void Update() {
        super.Update();
        Move();
    }

    // ------------------------------------------------------------------------
    /*! Move
    *
    *   Moves the sprite on a certain direction
    */ //----------------------------------------------------------------------
    public void Move() {
        if(up) {
            mPosition.y += 1;
        }

        if(down) {
            mPosition.y -= 1;
        }

        if(left) {
            mPosition.x -= 1;
        }

        if(right) {
            mPosition.x += 1;
        }
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders the player
    */ //----------------------------------------------------------------------
    @Override
    public void Render(Graphics2D g) {
        g.drawImage(mAnimation.GetCurrentFrame(), (int)mPosition.x, (int)mPosition.y, mSize, mSize, null);
    }

    // ------------------------------------------------------------------------
    /*! Input
    *
    *   Checks the input, and sets directions
    */ //----------------------------------------------------------------------
    public void Input(InputManager input) {
        if(input.up.down) {
            up = true;
            down = false;
            left = false;
            right = false;
        } else if(input.down.down) {
            up = false;
            down = true;
            left = false;
            right = false;
        } else if(input.left.down) {
            up = false;
            down = false;
            left = true;
            right = false;
        } else if(input.right.down) {
            up = false;
            down = false;
            left = false;
            right = true;
        } else {
             up = false;
            down = false;
            left = false;
            right = false;
        }

        if(input.GetButton() == 1) {
            System.out.println("Ahoy");
        }
    }
    
}

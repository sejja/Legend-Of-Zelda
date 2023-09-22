package Gameplay;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

import Engine.ECSystem.Actor;
import Engine.Graphics.Animation;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;


public class Npc extends Actor {
    private String name;
    private String dialogue;

        /*! Conversion Constructor
    *
    *   Constructs a NPC with a name, a sprite, a position, a dialog and gives it a size
    */ //----------------------------------------------------------------------
    public Npc(String name, Spritesheet sprite, Vector2D<Float> position, String dialogue) {
    
        super(position);
        this.name = name;
        this.dialogue = dialogue;

        sprite.setmSpriteArray(transposeMatrix(sprite.GetSpriteArray2D()));
        
    }

        /*! Transpose
    *       @Param  -> BufferedImage 2D Matrix
    *       ret     -> Transposed BufferedImage 2D Matrix
    */ //----------------------------------------------------------------------
    private BufferedImage[][] transposeMatrix(BufferedImage [][] m){
        BufferedImage[][] temp = new BufferedImage[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }
    // ------------------------------------------------------------------------

    public String getDialogue() {
        return dialogue;
    }
    public void setDialogue(String dialogue){
        this.dialogue = dialogue;
    }
}
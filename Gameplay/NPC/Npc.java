package Gameplay.NPC;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JLabel;

import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Animation;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Sprite;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.SpriteComponent;
import Engine.Graphics.Objects.FontObject;
import Engine.Graphics.Tile.TileManager;
import Engine.Graphics.Tile.Tilemap;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;


public class Npc extends Engine.ECSystem.Types.Actor {
    private String name;
    private static String dialogue;
    protected Graphics2D window;
    private SpriteComponent mAnimation;


        /*! Conversion Constructor
    *
    *   Constructs a NPC with a name, a sprite, a position, a dialog and gives it a size
    */ //----------------------------------------------------------------------

    public Npc(String nameNPC, Sprite sprite, Vector2D<Float> position, String dialogue, Vector2D<Float> size) {
        super(position);
        this.name = nameNPC;
        this.dialogue = dialogue;
        mAnimation = AddComponent(new SpriteComponent(this, sprite));
        SetScale(size);
    }

        /*! Transpose
    *       @Param  -> BufferedImage 2D Matrix
    *       ret     -> Transposed BufferedImage 2D Matrix
    */ //----------------------------------------------------------------------
    private BufferedImage[][] transposeMatrix(BufferedImage [][] m){
        BufferedImage[][] temp = new BufferedImage[m[0].length][m.length];
        for (int i = 0; i < m.length; i++){
            for (int j = 0; j < m[0].length; j++){
                temp[j][i] = m[i][j];
            }
        }
        return temp;
    }
    // ------------------------------------------------------------------------

    boolean isContact = true;

    public void Update(Vector2D<Float> playerPosition) {
        super.Update();
        if(isContact) {
            AddComponent(new DialogueWindow(this));    
            isContact = false;
        }
    }

    public static String getDialogue() {
        return dialogue;
    }
    public void setDialogue(String dialogue){
        this.dialogue = dialogue;
    }
}
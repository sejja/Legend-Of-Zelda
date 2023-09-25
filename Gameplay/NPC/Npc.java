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
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Objects.FontObject;
import Engine.Graphics.Tile.TileManager;
import Engine.Graphics.Tile.Tilemap;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;


public class Npc extends Engine.ECSystem.Types.Actor {
    private String name = "";
    private String dialogue;
    protected Graphics2D window;
    private AnimationMachine mAnimation;


        /*! Conversion Constructor
    *
    *   Constructs a NPC with a name, a sprite, a position, a dialog and gives it a size
    */ //----------------------------------------------------------------------

    public Npc(String nameNPC, Spritesheet sprite, Vector2D<Float> position, String dialogue, Vector2D<Float> size) {
        super(position);
        this.name = nameNPC;
        this.dialogue = dialogue;
        mAnimation = AddComponent(new AnimationMachine(this, sprite));

    }

        /*! Transpose
    *       @Param  -> BufferedImage 2D Matrix
    *       ret     -> Transposed BufferedImage 2D Matrix
    */ //----------------------------------------------------------------------
    private BufferedImage[][] transposeMatrix(BufferedImage [][] m){
        BufferedImage[][] temp = new BufferedImage[m[0].length][m.length]; //Modificar este +8 para casos generales 
        for (int i = 0; i < m.length; i++){
            for (int j = 0; j < m[0].length; j++){
                temp[j][i] = m[i][j];
            }
        }
        return temp;
    }
    // ------------------------------------------------------------------------

    public void drawDialogueScreen() {

        //Window
        int x = 100;
        int y = 100;
        int width = 500;
        int height = 400;

        drawSubWindow(x,y,width,height);

        window.setFont(window.getFont().deriveFont(Font.PLAIN, 40));
        x += 100;
        y += 100;
        window.drawString(dialogue, x, y);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        
        Color c = new Color(0,0,0, 200);
        window.setColor(c);
        window.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        window.setColor(c);
        window.setStroke(new BasicStroke(5));
        window.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }

    public void Update() {
        super.Update();
//        mAnimation.GetAnimation().SetDelay(-1);
//        mAnimation.GetAnimation().SetFrame(0);
    }

    public String getDialogue() {
        return dialogue;
    }
    public void setDialogue(String dialogue){
        this.dialogue = dialogue;
    }
}
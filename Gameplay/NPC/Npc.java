package Gameplay.NPC;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
    static ArrayList<String> mdialogueArrayList;
    private ArrayList<Npc> npcArrayList = new ArrayList<Npc>();


        /*! Conversion Constructor
    *
    *   Constructs a NPC with a name, a sprite, a position, a dialog and gives it a size
    */ //----------------------------------------------------------------------

    public Npc(String nameNPC, Sprite sprite, Vector2D<Float> position, ArrayList<String> dialogueArrayList, Vector2D<Float> size) {
        super(position);
        this.name = nameNPC;
        //this.dialogue = dialogue;
        mAnimation = AddComponent(new SpriteComponent(this, sprite));
        SetScale(size);
        this.mdialogueArrayList = dialogueArrayList;
        npcArrayList.add(this);
    }

        /*! Transpose
    *       @Param  -> BufferedImage 2D Matrix
    *       ret     -> Transposed BufferedImage 2D Matrix
    */ //----------------------------------------------------------------------
    
    boolean isContact = true;

    public void Update(Vector2D<Float> playerPosition) {
        super.Update();
        if(isContact) {
            AddComponent(new DialogueWindow(this, npcArrayList.get(0)));    
            isContact = false;
        }
    }

    public static ArrayList<String> getDialoguesArrayList() {
        return mdialogueArrayList;
    }
    public void setdialogue(String dialogue){
        this.dialogue = dialogue;
    }
}
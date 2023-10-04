package Gameplay.NPC;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;
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
import Engine.Physics.Components.BoxCollider;


public class Npc extends Engine.ECSystem.Types.Actor {
    private String name;
    private static String dialogue; //Como que estatico?
    protected Graphics2D window;
    private SpriteComponent mAnimation;
    private ArrayList<String> mdialogueArrayList;
    private static ArrayList<Npc> npcArrayList = new ArrayList<Npc>(); //Para que?
    protected BoxCollider mCollider;


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
        mCollider = (BoxCollider)AddComponent(new BoxCollider(this, new Vector2D<>(75f,0.f)));
    }

        /*! Transpose
    *       @Param  -> BufferedImage 2D Matrix
    *       ret     -> Transposed BufferedImage 2D Matrix
    */ //----------------------------------------------------------------------
    
    static boolean interact = false;
    static boolean remove = false;

    public void Update(Vector2D<Float> playerPosition) {

        super.Update();
        if(interact) {
            AddComponent(new DialogueWindow(this, npcArrayList.get(0)));    
            interact = false;
        }else if(remove == true){
            RemoveComponent(DialogueWindow.getDialgueWindow());
            remove = false;
        }
    }

    public ArrayList<String> getDialoguesArrayList() {
        return mdialogueArrayList;
    }
    public void setdialogue(String dialogue){
        this.dialogue = dialogue;
    }
    public static void setInteract(boolean interact1){
        interact = interact1;
    }
    public static void setRemove(boolean remove1) {
        remove = remove1;
    }
    public String getName() {
        return name;
    }
    public static ArrayList<Npc> getNpcArrayList() {
        return npcArrayList;
    }
}
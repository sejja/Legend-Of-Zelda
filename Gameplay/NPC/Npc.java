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
import Gameplay.Link.*;
import Gameplay.Link.Player;
import Gameplay.States.PlayState;


public class Npc extends Actor {
    private String name;
    protected BoxCollider boxCollider;
    static boolean interact = false;
    static boolean remove = false;

    
    private static DialogueWindow dialogueWindow;
    private ArrayList<String> dialogueArrayList;

    private AnimationMachine animationMachine;
    private BufferedImage[][] allAnimations;

    /*! Conversion Constructor
    * Constructs a NPC with a name, a sprite, a position, a dialog and gives it a size
    */ //----------------------------------------------------------------------

    public Npc(String nameNPC, Spritesheet sprite, Vector2D<Float> position, ArrayList<String> dialogueArrayList, Vector2D<Float> size) {
        super(position);
        this.name = nameNPC;
        
        allAnimations = transposeMatrix(sprite.GetSpriteArray2D());
        setSetopAnimationSet(allAnimations, allAnimations[0].length);
        animationMachine = AddComponent(new AnimationMachine(this, sprite));
        animationMachine.SetFrames(allAnimations[6]); //La diferencia entre correr a una direction y parase en la misma es un + 4
        animationMachine.GetAnimation().SetDelay(15);

        SetScale(size);
        this.dialogueArrayList = dialogueArrayList;
        boxCollider = (BoxCollider)AddComponent(new BoxCollider(this, new Vector2D<>(75f,0.f)));
        dialogueWindow = new DialogueWindow(this);
        ObjectManager.GetObjectManager().AddEntity(this);
    }
        /*! Transpose
        *
        * @Param  -> BufferedImage 2D Matrix
        * ret     -> Transposed BufferedImage 2D Matrix
        */ //----------------------------------------------------------------------
    public void Update(Vector2D<Float> playerPosition) {

        super.Update();
        //interaction();

    }
    //______________________________________________________________________________________
    private BufferedImage[][] transposeMatrix(BufferedImage [][] m){
        BufferedImage[][] temp = new BufferedImage[m[0].length + 4][m.length];
        for (int i = 0; i < m.length; i++){
            for (int j = 0; j < m[0].length; j++){
                temp[j][i] = m[i][j];
            }
        }
        return temp;
    }
    private void setSetopAnimationSet(BufferedImage[][] m, int size){
        for (int i = 0; i < 4; i++){
            for (int j = 0;  j < m[0].length; j++){
                m[size+i][j] = m[i][0];
            }
        }
    }
    //______________________________________________________________________________________
    
    public ArrayList<String> getDialoguesArrayList() {return dialogueArrayList;}
    public static void setInteract(boolean interact1){interact = interact1;}
    public static void setRemove() {remove = true;}
    public String getName() {return name;}

    public void interaction(){
        dialogueWindow.setNpc(this);
        if (!getmComponents().contains(dialogueWindow)){
            AddComponent(dialogueWindow);
            Pause();
        }else{
            nextDialog();
        }
    }

    public void nextDialog(){
        if(dialogueWindow.getJ() + 1 <=  this.dialogueArrayList.size()-1){ // Si hay siguiente
            dialogueWindow.setSiguiente();
        }else{
            //System.out.println("udbfyhisd");
            RemoveComponent(dialogueWindow);
            dialogueWindow.setJ(0);
            Player link = (Player) ObjectManager.GetObjectManager().getMapAliveActors().get(Player.class).getFirst();
            link.removeInteraction();
            Pause();
        }
    }

    private void Pause(){
        if(PlayState.getGameState() == PlayState.getPlayState()){
            PlayState.setGameState(2);
        }else if(PlayState.getGameState() == PlayState.getPauseState()){
            PlayState.setGameState(1);
        }
    }
}
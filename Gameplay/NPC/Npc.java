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

    private final int speed = 1;
    private int direction;
    private float xInicial;
    private float yInicial;

    private final int DOWN = 0;
    private final int LEFT = 1;
    private final int RIGHT= 2;
    private final int UP = 3;

    /*! Conversion Constructor
    * Constructs a NPC with a name, a sprite, a position, a dialog and gives it a size
    */ //----------------------------------------------------------------------

    public Npc(String nameNPC, Spritesheet sprite, Vector2D<Float> position, ArrayList<String> dialogueArrayList, Vector2D<Float> size, int numberStartAnimation) {
        super(position);
        this.name = nameNPC;
        
        allAnimations = transposeMatrix(sprite.GetSpriteArray2D());
        setSetopAnimationSet(allAnimations, allAnimations[0].length);
        animationMachine = AddComponent(new AnimationMachine(this, sprite));
        animationMachine.SetFrames(allAnimations[numberStartAnimation]); //La diferencia entre correr a una direction y parase en la misma es un + 4
        animationMachine.GetAnimation().SetDelay(40);

        direction = numberStartAnimation;
        xInicial = position.x;
        yInicial = position.y;

        SetScale(size);
        this.dialogueArrayList = dialogueArrayList;
        boxCollider = (BoxCollider)AddComponent(new BoxCollider(this, new Vector2D<>(55f,0.f)));
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
        movement();

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
            dialogueWindow.setJ(0);
            Pause();
        }else{
            nextDialog();
        }
    }

    public void nextDialog(){
        if(dialogueWindow.getJ() + 1 <=  this.dialogueArrayList.size()-1){ // Si hay siguiente
            dialogueWindow.setSiguiente();
        }else{
            RemoveComponent(dialogueWindow);
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

    private void movement() {
        Vector2D<Float> pos = GetPosition();
        Vector2D<Float> distance = new Vector2D<Float>(100.f, 100.f);
        if(pos.y == yInicial + distance.y && pos.x == xInicial){
            animationMachine.SetFrames(allAnimations[LEFT]);
            direction = LEFT;
            yInicial = pos.y;
        }else if(pos.x == xInicial - distance.x && pos.y == yInicial){
            animationMachine.SetFrames(allAnimations[UP]);
            direction = UP;
            xInicial = pos.x;
        }else if(pos.y == yInicial - distance.x && pos.x == xInicial){
            animationMachine.SetFrames(allAnimations[RIGHT]);
            direction = RIGHT;
            yInicial = pos.y;
        }else if(pos.x == xInicial + distance.x && pos.y == yInicial){
            animationMachine.SetFrames(allAnimations[DOWN]);
            direction = DOWN;
            xInicial = pos.x;
        }
        if (direction == UP){
            pos.y -= speed;
        }else if(direction == DOWN) {
            pos.y += speed;
        }else if(direction == LEFT) {
            pos.x -= speed;
        }else if(direction == RIGHT) {
            pos.x += speed;
        }

        SetPosition(pos);
    }
}
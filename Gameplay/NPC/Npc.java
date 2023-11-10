package Gameplay.NPC;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import javax.swing.JLabel;

import Engine.Assets.AssetManager;
import Engine.Audio.Audio;
import Engine.Audio.Sound;
import Engine.Developer.Logger.Log;
import Engine.Developer.Logger.Logger;
import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Graphics.Components.SpriteComponent;
import Engine.Graphics.Tile.TileManager;
import Engine.Graphics.Tile.Tilemap;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.Physics.StaticPlayerCollision;
import Engine.Physics.Components.BoxCollider;
import Engine.Window.GameLoop;
import Gameplay.Interaction;
import Gameplay.Link.Player;


public class Npc extends Actor implements StaticPlayerCollision, Interaction{
    private String name;
    protected BoxCollider hitbox;
    static boolean interact = false;
    static boolean remove = false;
    
    private static DialogueWindow dialogueWindow;

    private AnimationMachine animationMachine;
    private BufferedImage[][] allAnimations;

    private final int speed = 2;
    private int direction;
    private int currentDirection;
    private float xInicial;
    private float yInicial;


    private final int DOWN = 0;
    private final int LEFT = 1;
    private final int RIGHT= 2;
    private final int UP = 3;

    private int typeOfMovement;

    //Type of movement
    private final int squareMovement = 4;
    private final int xLineMovement = 5;
    private final int yLineMovement = 6;
    private final int stop = 7;


    /*! Conversion Constructor
    * Constructs a NPC with a name, a sprite, a position, a dialog and gives it a size
    */ //----------------------------------------------------------------------

    public Npc(String nameNPC, Spritesheet sprite, Vector2D<Float> position, Vector2D<Float> size, int numberStartAnimation, int movement) {
        super(position);
        this.name = nameNPC;
        
        allAnimations = transposeMatrix(sprite.GetSpriteArray2D());
        setSetopAnimationSet(allAnimations, allAnimations[0].length);
        sprite.setmSpriteArray(allAnimations);
        animationMachine = AddComponent(new AnimationMachine(this, sprite));
        animationMachine.SetFrameTrack(numberStartAnimation); //La diferencia entre correr a una direction y parase en la misma es un + 4
        animationMachine.GetAnimation().SetDelay(15);

        typeOfMovement = movement;

        direction = numberStartAnimation;
        xInicial = position.x;
        yInicial = position.y;

        SetScale(size);

        this.setDefaultPseudoPosition();
        setPseudoPositionVisible();
        hitbox = (BoxCollider)AddComponent(new BoxCollider(this, new Vector2D<Float>(50f,50f), true));
        
        dialogueWindow = new DialogueWindow(this);
        
    }

    public void Update() {
        super.Update();
        movement();
        hitbox.Update();
        pseudoPositionUpdate();
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
    
    public static void setInteract(boolean interact1){interact = interact1;}
    public static void setRemove() {remove = true;}
    public String getName() {return name;}

    public void lookAtPLayer(Vector2D<Float> playerPosition){
        Player player = (Player) ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).get(0);
        Vector2D<Float> vector = player.getPseudoPosition().getVectorToAnotherActor(this.getPseudoPosition());
        if((player.getPseudoPosition().x > this.getPseudoPosition().x) && (Math.abs(vector.x) > Math.abs(vector.y))){
            animationMachine.SetFrameTrack(RIGHT);
        } else if(player.getPseudoPosition().x < this.getPseudoPosition().x && (Math.abs(vector.x) > Math.abs(vector.y))){
            animationMachine.SetFrameTrack(LEFT);
        } else if(player.getPseudoPosition().y < this.getPseudoPosition().y && (Math.abs(vector.x) < Math.abs(vector.y))){
            animationMachine.SetFrameTrack(UP);
        } else if(player.getPseudoPosition().y > this.getPseudoPosition().y && (Math.abs(vector.x) < Math.abs(vector.y))){
            animationMachine.SetFrameTrack(DOWN);
        }
    }

    public void nextDialog(){
        if(dialogueWindow.getJ() + 1 <=  dialogueWindow.getDialogue().size()-1){ // Si hay siguiente
            dialogueWindow.setSiguiente();
            Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/message.wav"));
            Audio.Instance().Play(sound);
        }else{
            RemoveComponent(dialogueWindow);
            Player link = (Player) ObjectManager.GetObjectManager().GetPawn();
            link.removeInteraction();
            animationMachine.SetFrameTrack(currentDirection);
            Pause();
            Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/finish.wav"));
            Audio.Instance().Play(sound);
        }
    }

    private void Pause(){
        GameLoop.SetPaused(!GameLoop.IsPaused());
    }

    private void movement() {
        Vector2D<Float> pos = GetPosition();
        Vector2D<Float> distance = new Vector2D<Float>(100.f, 100.f);
        if(typeOfMovement != stop){
            switch(typeOfMovement){
                case squareMovement:
                    squareMovement(pos, distance);
                case xLineMovement:
                    xLineMovement(pos, distance);
                case yLineMovement:
                    yLineMovement(pos, distance);
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
        }
        SetPosition(pos);
    }
    public void squareMovement(Vector2D<Float> pos, Vector2D<Float> distance) {
        if(pos.y == yInicial + distance.y && pos.x == xInicial){
            animationMachine.SetFrameTrack(LEFT);
            direction = LEFT;
            yInicial = pos.y;
        }else if(pos.x == xInicial - distance.x && pos.y == yInicial){
            animationMachine.SetFrameTrack(UP);
            direction = UP;
            xInicial = pos.x;
        }else if(pos.y == yInicial - distance.y && pos.x == xInicial){
            animationMachine.SetFrameTrack(RIGHT);
            direction = RIGHT;
            yInicial = pos.y;
        }else if(pos.x == xInicial + distance.x && pos.y == yInicial){
            animationMachine.SetFrameTrack(DOWN);
            direction = DOWN;
            xInicial = pos.x;
        }
    }
    
    public void xLineMovement(Vector2D<Float> pos, Vector2D<Float> distance){
        if(pos.x == xInicial - distance.x && pos.y == yInicial){
            animationMachine.SetFrameTrack(RIGHT);
            direction = RIGHT;
            yInicial = pos.y;
        }else if(pos.x == xInicial + distance.y && pos.y == yInicial){
            animationMachine.SetFrameTrack(LEFT);
            direction = LEFT;
            yInicial = pos.y;
        }
    }

    public void  yLineMovement(Vector2D<Float> pos, Vector2D<Float> distance) {
        if(pos.y == yInicial - distance.y && pos.x == xInicial){
            animationMachine.SetFrameTrack(DOWN);
            direction = DOWN;
            xInicial = pos.x;
        }else if(pos.y == yInicial + distance.y && pos.x == xInicial){
            animationMachine.SetFrameTrack(UP);
            direction = UP;
            xInicial = pos.x;
        }
    }
    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override 
    public Class GetSuperClass(){return Npc.class;}
    
    @Override
    public void interaction() {

        // TODO Auto-generated method stub
        lookAtPLayer(ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).get(0).GetPosition());
        dialogueWindow.setNpc(this);
        currentDirection = this.getDirection(); 
        if (!getmComponents().contains(dialogueWindow)){
            dialogueWindow.setJ(0);
            AddComponent(dialogueWindow);
            Pause();
            Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/message.wav"));
            Audio.Instance().Play(sound);
        }else{
            nextDialog();
        }
    }

}
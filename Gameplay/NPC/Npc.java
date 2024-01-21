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
import Engine.Math.EuclideanCoordinates;
import Engine.Math.Vector2D;
import Engine.Physics.StaticPlayerCollision;
import Engine.Physics.Components.BoxCollider;
import Engine.Window.GameLoop;
import Gameplay.Interaction;
import Gameplay.Link.Player;


/**
 * Npc class represents non-player characters (NPCs) in the game.
 * It extends Actor and implements StaticPlayerCollision and Interaction interfaces.
 */
public class Npc extends Actor implements StaticPlayerCollision, Interaction {
    
    // Fields for NPC properties and behavior
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

    // Constants representing movement directions
    private final int DOWN = 0;
    private final int LEFT = 1;
    private final int RIGHT= 2;
    private final int UP = 3;

    private int typeOfMovement;

    // Constants representing types of movement
    private final int squareMovement = 4;
    private final int xLineMovement = 5;
    private final int yLineMovement = 6;
    private final int stop = 7;

    /*! Conversion Constructor
    * Constructs an NPC with a name, a sprite, a position, a dialog and gives it a size
    */
    public Npc(String nameNPC, Spritesheet sprite, Vector2D<Float> position, Vector2D<Float> size, int numberStartAnimation, int movement) {
        // Constructor initializes NPC properties
        super(position);
        this.name = nameNPC;
        
        allAnimations = transposeMatrix(sprite.GetSpriteArray2D());
        setSetopAnimationSet(allAnimations, allAnimations[0].length);
        sprite.ChangeSpriteFrames(allAnimations);
        animationMachine = AddComponent(new AnimationMachine(this, sprite));
        animationMachine.SetFrameTrack(numberStartAnimation); // The difference between running in a direction and stopping in the same is +4
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

    /**
     * Updates the NPC's state.
     */
    public void Update() {
        // Updates NPC logic and components
        super.Update();
        movement();
        hitbox.Update();
        pseudoPositionUpdate();
    }

    //______________________________________________________________________________________
    /**
     * Transposes a 2D matrix of BufferedImages.
     * @param m The original matrix to be transposed.
     * @return The transposed matrix.
     */
    private BufferedImage[][] transposeMatrix(BufferedImage[][] m) {
        // Implementation of matrix transposition
        BufferedImage[][] temp = new BufferedImage[m[0].length + 4][m.length];
        for (int i = 0; i < m.length; i++){
            for (int j = 0; j < m[0].length; j++){
                temp[j][i] = m[i][j];
            }
        }
        return temp;
    }

    /**
     * Sets up the stop animation set by copying the first frame to the next four frames.
     * @param m The animation matrix.
     * @param size The size of the animation set.
     */
    private void setSetopAnimationSet(BufferedImage[][] m, int size) {
        // Set up the stop animation set
        for (int i = 0; i < 4; i++){
            for (int j = 0;  j < m[0].length; j++){
                m[size+i][j] = m[i][0];
            }
        }
    }
    //______________________________________________________________________________________
    
    /**
     * Sets the interact flag.
     * @param interact1 The new value for the interact flag.
     */
    public static void setInteract(boolean interact1) {
        interact = interact1;
    }

    /**
     * Sets the remove flag.
     */
    public static void setRemove() {
        remove = true;
    }

    /**
     * Gets the name of the NPC.
     * @return The name of the NPC.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the NPC's direction based on the player's position.
     * @param playerPosition The position of the player.
     */
    public void lookAtPLayer(Vector2D<Float> playerPosition) {
        // Sets NPC's direction based on player's position
        Player player = (Player) ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).get(0);
        Vector2D<Float> vector = new EuclideanCoordinates(player.getPseudoPosition()).getVectorToAnotherActor(this.getPseudoPosition());
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

    /**
     * Moves to the next dialogue in the dialogue window.
     */
    public void nextDialog() {
        // Moves to the next dialogue in the dialogue window
        if(dialogueWindow.getJ() + 1 <=  dialogueWindow.getDialogue().size()-1){ // If there is a next dialogue
            dialogueWindow.setSiguiente();
            Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/message.wav"));
            Audio.Instance().Play(sound);
        }else{
            removeDialogWindown();
        }
    }

    /**
     * Removes the dialogue window and resets the player's interaction state.
     */
    public void removeDialogWindown() {
        // Removes the dialogue window and resets player's interaction state
        RemoveComponent(dialogueWindow);
        Player link = (Player) ObjectManager.GetObjectManager().GetPawn();
        link.removeInteraction();
        animationMachine.SetFrameTrack(currentDirection);
        Pause();
        Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/finish.wav"));
        Audio.Instance().Play(sound);
    }

    /**
     * Pauses or resumes the game loop.
     */
    private void Pause() {
        // Pauses or resumes the game loop
        GameLoop.SetPaused(!GameLoop.IsPaused());
    }

    /**
     * Handles NPC movement based on the type of movement specified.
     */
    private void movement() {
        // Handles NPC movement
        Vector2D<Float> pos = GetPosition();
        Vector2D<Float> distance = new Vector2D<Float>(100.f, 100.f);
        if(typeOfMovement != stop){
            switch(typeOfMovement){
                case squareMovement:
                    squareMovement(pos, distance);
                    break;
                case xLineMovement:
                    xLineMovement(pos, distance);
                    break;
                case yLineMovement:
                    yLineMovement(pos, distance);
                    break;
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

    /**
     * Handles square movement pattern.
     * @param pos The current position of the NPC.
     * @param distance The distance vector for the square movement.
     */
    public void squareMovement(Vector2D<Float> pos, Vector2D<Float> distance) {
        // Handles square movement pattern
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
    
    /**
     * Handles X-line movement pattern.
     * @param pos The current position of the NPC.
     * @param distance The distance vector for the X-line movement.
     */
    public void xLineMovement(Vector2D<Float> pos, Vector2D<Float> distance){
        // Handles X-line movement pattern
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

    /**
     * Handles Y-line movement pattern.
     * @param pos The current position of the NPC.
     * @param distance The distance vector for the Y-line movement.
     */
    public void yLineMovement(Vector2D<Float> pos, Vector2D<Float> distance) {
        // Handles Y-line movement pattern
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

    /**
     * Gets the current direction of the NPC.
     * @return The current direction of the NPC.
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Sets the current direction of the NPC.
     * @param direction The new direction value.
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Returns the superclass of the NPC class.
     * @return The superclass of the NPC class.
     */
    @Override
    public Class GetSuperClass() {
        return Npc.class;
    }

    /**
     * Handles NPC interaction with the player.
     */
    @Override
    public void interaction() {
        // Handles NPC interaction with the player
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
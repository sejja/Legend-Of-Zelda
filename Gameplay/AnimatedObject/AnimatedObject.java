package Gameplay.AnimatedObject;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.ListIterator;

import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Animation;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.Physics.CollisionResult;
import Engine.Physics.Components.BoxCollider;
import Gameplay.States.PlayState;
import Gameplay.Enemies.Enemy;
import Gameplay.LifeBar.LifeBar;
import Gameplay.NPC.Npc;

public class AnimatedObject extends Actor{
    protected AnimationMachine animationMachine;
    protected BufferedImage[] animation;
    protected int delay = 5;
    protected boolean deleteAtTheEnd = false;

    public AnimatedObject (Vector2D<Float> position, Spritesheet spritesheet, int delay,int animationIndex ,boolean deleteAtTheEnd){
        super(position);
        spritesheet.flip();

        animationMachine = AddComponent(new AnimationMachine(this, spritesheet));
        animationMachine.setMust_Complete(deleteAtTheEnd);
        this.deleteAtTheEnd = deleteAtTheEnd;

        animation = spritesheet.GetSpriteArray2D()[animationIndex];
        ObjectManager.GetObjectManager().AddEntity(this);
    }
    public AnimatedObject(Vector2D<Float> position) {
        super(position);
    }
    
    @Override
    public void Update(){
        if(deleteAtTheEnd && animationMachine.finised_Animation){
            ObjectManager.GetObjectManager().RemoveEntity(this);
        }
    }
}